package evprj.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import evprj.repo.EVChargingStationRepository;

@Configuration
@EnableWebSocketMessageBroker
//@EnableWebSocket
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
//WebSocketConfigurer   {
	@Autowired
	private EVChargingStationRepository evChargingStationRepository;
 public void configureMessageBroker(MessageBrokerRegistry registry) {
		System.out.println("ggggggg");
    	registry.enableSimpleBroker("/topic");
    	registry.setApplicationDestinationPrefixes("/app");
        
    }
    
    
 /*   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
    	System.out.println("fdfdfd");
      /*  registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();*/
        
        /*registry.addEndpoint("/websocket").withSockJS();
    }*/
    
	
	

	  @Override
	  public void registerStompEndpoints(StompEndpointRegistry registry) {
	    registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("http://localhost:3000");
	  }

   /* @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandler(evChargingStationRepository), "/websocket").setAllowedOrigins("http://localhost:3000"); ;
       
    	
    }*/
    

}
