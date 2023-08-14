package evprj.websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import evprj.entity.EVChargingStation;
import evprj.repo.EVChargingStationRepository;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private final EVChargingStationRepository evChargingStationRepository;

	public CustomWebSocketHandler(EVChargingStationRepository evChargingStationRepository) {
		this.evChargingStationRepository = evChargingStationRepository;
	}

	/*
	 * @PersistenceContext EntityManager entityManager;
	 */

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		System.out.println("WebSocket connection opened.");
		sessions.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		String payload = message.getPayload();
		JsonNode jsonNode = mapper.readTree(payload);
		String url_link = jsonNode.get("url_link").asText();
		int station_id = jsonNode.get("station_id").asInt();
		if ("/getByStationId".equals(url_link)) {

			EVChargingStation evChargingStation = new EVChargingStation();

			evChargingStation = evChargingStationRepository.findById(station_id).get();

			if (evChargingStation != null) {

				evChargingStation = evChargingStationRepository.save(evChargingStation);

				updateBatteryPercentage(evChargingStation, evChargingStation.getBattery_percentage(),
						evChargingStation.getCharging_power());

			}

		} else {

			String a = "something wrong";
			TextMessage responseMessage = new TextMessage(a);
			session.sendMessage(responseMessage);
		}

	}

	private void updateBatteryPercentage(EVChargingStation evChargingStation, int batteryPercentage, double power)
			throws Exception {

		double chargingPower = power / 10;
		batteryPercentage += chargingPower;
		final int newBatteryPercentage = batteryPercentage;

		String json = convertToJSON(evChargingStation, newBatteryPercentage);

		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				try {
					if (newBatteryPercentage >= 100) {
						// evChargingStation.setEstimated_end_time(dt);
						// evChargingStation =evChargingStationRepository.save(evChargingStation);
						sessions.remove(session);

					}
					TextMessage batteryTextMessage = new TextMessage(json);
					System.out.println(json);

					session.sendMessage(batteryTextMessage);

					scheduler.schedule(() -> {
						try {
							updateBatteryPercentage(evChargingStation, newBatteryPercentage, power);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}, 2, TimeUnit.SECONDS);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String convertToJSON(EVChargingStation evChargingStation, int batteryPercentage)
			throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = objectMapper.createObjectNode();
		jsonNode.put("stationId", evChargingStation.getCharging_station_id());
		jsonNode.put("name", evChargingStation.getCharging_station_name());
		jsonNode.put("power", evChargingStation.getCharging_power());
		jsonNode.put("batteryPercentage", batteryPercentage);

		return objectMapper.writeValueAsString(jsonNode);
	}
	/*
	 * private void sendUpdate(WebSocketSession session, int payload) { try { //
	 * Replace this with your logic to fetch real-time data or updates long
	 * timeMillis = System.currentTimeMillis();
	 * 
	 * long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
	 * System.out.print(payload); payload = payload+1; System.out.print(payload);
	 * String realTimeData = "Battery percent " + timeSeconds + payload;
	 * 
	 * session.sendMessage(new TextMessage(realTimeData)); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		sessions.remove(session);
		System.err.println("WebSocket error: " + exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		sessions.remove(session);
		System.out.println("WebSocket connection closed.");
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
