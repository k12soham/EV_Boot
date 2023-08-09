package evprj.websocket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	// private final Map<String, WebSocketApiHandler> apiHandlers = new HashMap<>();

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
		// sendBatteryPercentage(session);
		// handleMessage(session);
		// sendInitialData(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		// Session session1 = entityManager.unwrap(Session.class);
	
		String payload = message.getPayload();
		JsonNode jsonNode = mapper.readTree(payload);
		String endpoint = jsonNode.get("endpoint").asText();
		int param1 = jsonNode.get("station_id").asInt();
		if ("/getByStationId".equals(endpoint)) {

			EVChargingStation evChargingStation = new EVChargingStation();

			evChargingStation = evChargingStationRepository.findById(param1).get();

			if (evChargingStation != null) {

				 updateBatteryPercentage(evChargingStation,evChargingStation.getBattery_percentage(), 
						 evChargingStation.getCharging_power());
				
			}

		} else {
			
			String a="something wrong";
			TextMessage responseMessage = new TextMessage(a);
			session.sendMessage(responseMessage);
		}

		

		/*
		 * try { // int newBatteryPercentage = Integer.parseInt(payload);
		 * 
		 * JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();
		 * 
		 * // Get the individual values from the JSON object int batteryPercentage =
		 * jsonObject.get("batteryPercentage").getAsInt(); int power =
		 * jsonObject.get("power").getAsInt();
		 * 
		 * 
		 * updateBatteryPercentage(batteryPercentage,power); } catch
		 * (NumberFormatException e) { e.printStackTrace(); }
		 */
	}

	 private void updateBatteryPercentage(EVChargingStation evChargingStation, int batteryPercentage, double d) throws JsonProcessingException {

		double chargingPower = d / 10; // Normalize charging capacity to charging power

		batteryPercentage += chargingPower;
		final int newBatteryPercentage = batteryPercentage;

		//String batteryMessage = String.valueOf(batteryPercentage);
		//TextMessage batteryTextMessage = new TextMessage(batteryMessage);
		//System.out.println(batteryTextMessage);

		
		
		String json = convertToJSON(evChargingStation, newBatteryPercentage);
		
		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				try {
					if (newBatteryPercentage >= 100) {
						sessions.remove(session);
						
					}
					 TextMessage batteryTextMessage = new TextMessage(json);
		               
					session.sendMessage(batteryTextMessage);
					scheduler.schedule(() -> {
						try {
							updateBatteryPercentage(evChargingStation,newBatteryPercentage, d);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}, 2, TimeUnit.SECONDS);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	 private String convertToJSON(EVChargingStation evChargingStation, int batteryPercentage) throws JsonProcessingException {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   System.out.println(dtf.format(now)); 
		   String start  =dtf.format(now);
		 ObjectMapper objectMapper = new ObjectMapper();
		    ObjectNode jsonNode = objectMapper.createObjectNode();
		    jsonNode.put("stationId", evChargingStation.getCharging_station_id());
		    jsonNode.put("name", evChargingStation.getCharging_station_name());
		    jsonNode.put("power", evChargingStation.getCharging_power());
		    jsonNode.put("batteryPercentage", batteryPercentage);
		   // jsonNode.put("start-time",start );
		    // Add other properties as needed

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
		// sessions.close(session);
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
