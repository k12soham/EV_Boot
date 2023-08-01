package evprj;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class CustomWebSocketHandler implements WebSocketHandler{
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
   
	 @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection opened.");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        int payload =  Integer.parseInt((String) message.getPayload());
        
        		//=message.getPayload().toString();
        session.sendMessage(new TextMessage("Hello, " + payload + "!"));
        scheduler.scheduleAtFixedRate(() -> sendUpdate(session,payload), 0, 1, TimeUnit.SECONDS);
    }
    
    
    public void batteryStatus(String payload)
    {
    	
    }
 
    private void sendUpdate(WebSocketSession session, int payload) {
        try {
            // Replace this with your logic to fetch real-time data or updates
        	long timeMillis = System.currentTimeMillis();
        	long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis);
        	System.out.print(payload);
        	payload=payload+1;
            String realTimeData = "Some real-time data at " +timeSeconds+payload;
            
            session.sendMessage(new TextMessage(realTimeData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket connection closed.");
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
