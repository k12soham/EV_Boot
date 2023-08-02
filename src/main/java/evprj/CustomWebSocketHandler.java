package evprj;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
@Component
public class CustomWebSocketHandler implements WebSocketHandler    {

	 private int batteryPercentage = 10;
	    private final Set<WebSocketSession> sessions = new HashSet<>();
	    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("WebSocket connection opened.");
		sessions.add(session);
		 sendBatteryPercentage(session);
	}


	
	@Override

	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		//int payload = Integer.parseInt((String) message.getPayload());

		// payload=payload+1;
		// =message.getPayload().toString();
		//session.sendMessage(new TextMessage("Battery percent, " + payload + "%"));

		//scheduler.scheduleAtFixedRate(() -> sendUpdate(session, payload), 0, 1, TimeUnit.SECONDS);

	}

	
	
	
	 @MessageMapping("/getBattery")
	    @SendTo("/topic/battery")
	    public String getBattery() {
	        return String.valueOf(batteryPercentage);
	    }

	    private void sendBatteryPercentage(WebSocketSession session) throws IOException {
	        String message = String.valueOf(batteryPercentage);
	        session.sendMessage(new TextMessage(message));
	    }

	    public void addBatteryPercentage() throws IOException {
	    	System.out.println("fff");
	        if (batteryPercentage < 100) {
	            batteryPercentage += 1;
	            broadcastBatteryPercentage();
	        }
	    }

	    private String broadcastBatteryPercentage() throws IOException {
	        String message = String.valueOf(batteryPercentage);
	        TextMessage textMessage = new TextMessage(message);
	        WebSocketSession session = null;
	        /*for (WebSocketSession session : sessions) {
	            if (session.isOpen()) {
	                try {
	                	System.out.println("yyyy");
	                	System.out.println(textMessage);
	                    session.sendMessage(textMessage);
	                } catch (IOException e) {
	                	System.out.println("uuuu");
	                    e.printStackTrace();
	                }
	            }
	        }*/
	        session.sendMessage(textMessage);
return  textMessage.getPayload();
	       
	    }

	    public void startBatteryUpdateScheduler() {
	    	System.out.println("sdsds");
	        scheduler.scheduleAtFixedRate(() -> {
				try {
					addBatteryPercentage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}, 0, 10, TimeUnit.SECONDS);
	    }


	
	
	
	
	

	
	private void sendUpdate(WebSocketSession session, int payload) {
		try {
			// Replace this with your logic to fetch real-time data or updates
			long timeMillis = System.currentTimeMillis();

			long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
			System.out.print(payload);
			payload = payload+1;
			System.out.print(payload);
			String realTimeData = "Battery percent " + timeSeconds + payload;

			session.sendMessage(new TextMessage(realTimeData));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		//sessions.close(session);
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
