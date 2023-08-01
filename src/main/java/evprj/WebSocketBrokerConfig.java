package evprj;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketBrokerConfig implements WebSocketConfigurer {

    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	registry.enableSimpleBroker("/topic");
    	registry.setApplicationDestinationPrefixes("/app");
        
    }


   /* public void registerStompEndpoints(StompEndpointRegistry registry) {
    	System.out.println("fdfdfd");
       /* registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
        
        registry.addEndpoint("/websocket").withSockJS();
    }*/
    

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandler(), "/websocket").setAllowedOrigins("*");
    }
}
