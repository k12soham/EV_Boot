package evprj;

import java.io.IOException;
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

	private int batteryPercentage = 10;
	private final Set<WebSocketSession> sessions = new HashSet<>();
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("WebSocket connection opened.");
		sessions.add(session);
		// sendBatteryPercentage(session);
		// handleMessage(session);
		// sendInitialData(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		System.out.println(message);
		String payload = message.getPayload();

		try {
			// int newBatteryPercentage = Integer.parseInt(payload);

			JsonObject jsonObject = new JsonParser().parse(payload).getAsJsonObject();

			// Get the individual values from the JSON object
			int batteryPercentage = jsonObject.get("batteryPercentage").getAsInt();
			int power = jsonObject.get("power").getAsInt();

			

		
			updateBatteryPercentage(batteryPercentage,power);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	

	/*
	 * private void sendInitialData(WebSocketSession session) throws IOException {
	 * 
	 * int initialBatteryPercentage = 10; String batteryMessage =
	 * String.valueOf(initialBatteryPercentage);
	 * 
	 * session.sendMessage(new TextMessage(batteryMessage));
	 * 
	 * }
	 */

	public void updateBatteryPercentage(int batteryPercentage,int power) {

		//String batteryMessage = String.valueOf(batteryPercentage);
		//TextMessage batteryTextMessage = new TextMessage(batteryMessage);
		//System.out.println(batteryTextMessage);
		
		int chargingPower = power / 10; // Normalize charging capacity to charging power

		batteryPercentage += chargingPower;
		final int newBatteryPercentage=batteryPercentage;
		
		
		String batteryMessage = String.valueOf(batteryPercentage);
		TextMessage batteryTextMessage = new TextMessage(batteryMessage);
		System.out.println(batteryTextMessage);
		
		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				try {
					if(newBatteryPercentage>=100)
					{
						sessions.remove(session);
					}
					
					session.sendMessage(batteryTextMessage);
					 scheduler.schedule(() ->
					 updateBatteryPercentage(newBatteryPercentage,power),  2, TimeUnit.SECONDS);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
