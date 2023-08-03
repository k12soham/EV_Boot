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
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonParseException;

import evprj.entity.BatteryChargingData;
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler     {

	 private int batteryPercentage = 10;
	    private final Set<WebSocketSession> sessions = new HashSet<>();
	    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


	    
	    
	    
	    
	    
	    
	    
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("WebSocket connection opened.");
		sessions.add(session);
		// sendBatteryPercentage(session);
		//handleMessage(session);
		// sendInitialData(session);
	}


	
	

	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        try {
            int newBatteryPercentage = Integer.parseInt(payload);
            
            //scheduler.scheduleAtFixedRate(() -> updateBatteryPercentage(newBatteryPercentage), 0, 10, TimeUnit.SECONDS);
            updateBatteryPercentage(newBatteryPercentage);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

	
	/* private void sendInitialData(WebSocketSession session) throws IOException {
		 
	        int initialBatteryPercentage = 10;
	        String batteryMessage = String.valueOf(initialBatteryPercentage);
	
	        session.sendMessage(new TextMessage(batteryMessage));
	        
	    }*/
	 

	    public void updateBatteryPercentage(int newBatteryPercentage) {
	    	
	        String batteryMessage = String.valueOf(newBatteryPercentage);
	        TextMessage batteryTextMessage = new TextMessage(batteryMessage);
	        System.out.println(batteryTextMessage);
	        for (WebSocketSession session : sessions) {
	            if (session.isOpen()) {
	                try {
	                    session.sendMessage(batteryTextMessage);
	                 
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	
	
	
	
	
	
	
	

	
	/*private void sendUpdate(WebSocketSession session, int payload) {
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
	}*/

	
	
    

	
	
	
	
	
	
	
	
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
