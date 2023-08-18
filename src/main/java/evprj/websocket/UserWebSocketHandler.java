package evprj.websocket;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import evprj.entity.EVChargingStation;
import evprj.entity.EVChargingStationUser;
import evprj.repo.EVChargingStationRepository;
import evprj.service.UserService;
import evprj.utility.CommonUtility;

@Component
public class UserWebSocketHandler extends TextWebSocketHandler {

	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final UserService userService;
	private final EVChargingStationRepository evChargingStationRepository;
	private final evprj.utility.CommonUtility commonUtility;

	public UserWebSocketHandler(UserService userService, EVChargingStationRepository evChargingStationRepository,
			CommonUtility commonUtility) {
		this.userService = userService;
		this.evChargingStationRepository = evChargingStationRepository;
		this.commonUtility = commonUtility;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("WebSocket connection opened.");
		sessions.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		int id;
		String payload = message.getPayload();
		JsonNode jsonNode = mapper.readTree(payload);
		String urlLink = jsonNode.get("urlLink").asText();

		
		if ("/postUser".equals(urlLink)) {
			String name = jsonNode.get("name").asText();
			String phone = jsonNode.get("phone").asText();
			String vehicleDesc = jsonNode.get("vehicleDesc").asText();
			
			EVChargingStationUser evChargingStationUser = new EVChargingStationUser();
			evChargingStationUser.setUserName(name);
			evChargingStationUser.setPhone(phone);
			evChargingStationUser.setVehicleDes(vehicleDesc);
			
			EVChargingStationUser evStationUser = userService.createNewUser(evChargingStationUser);
			if (evStationUser != null) {
				getUserDetails(evStationUser);
			}
		} else if ("/getUserById".equals(urlLink)) {
			id = jsonNode.get("id").asInt();
			EVChargingStationUser evChargingStationUser = new EVChargingStationUser();
			evChargingStationUser = userService.getUserById(id);
			if (evChargingStationUser != null) {
				getUserDetails(evChargingStationUser);
			}
		} else if ("/getByStationId".equals(urlLink)) {
			id = jsonNode.get("id").asInt();
			EVChargingStation evChargingStation = new EVChargingStation();
			evChargingStation = evChargingStationRepository.findById(id).get();
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

	private void getUserDetails(EVChargingStationUser evChargingStationUser) throws Exception {
		String json = commonUtility.convertToJSONForUser(evChargingStationUser);
		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				try {
					TextMessage textMessage = new TextMessage(json);
					System.out.println(json);
					session.sendMessage(textMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateBatteryPercentage(EVChargingStation evChargingStation, int batteryPercentage, double power)
			throws Exception {
		double chargingPower = power / 10;
		batteryPercentage += chargingPower;
		final int newBatteryPercentage = batteryPercentage;
		String json = commonUtility.convertToJSON(evChargingStation, newBatteryPercentage);
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