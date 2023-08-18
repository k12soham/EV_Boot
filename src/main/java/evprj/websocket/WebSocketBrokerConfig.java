package evprj.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import evprj.repo.EVChargingStationRepository;
import evprj.service.UserService;
import evprj.utility.CommonUtility;

@Configuration
//@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketBrokerConfig implements WebSocketConfigurer {
//WebSocketMessageBrokerConfigurer {
	@Autowired
	private UserService userService;
	
	@Autowired
	private EVChargingStationRepository evChargingStationRepository;

	@Autowired 
	evprj.utility.CommonUtility commonUtility;
	
	
	/* 
	 * @Autowired
	 * 
	 * public void configureMessageBroker(MessageBrokerRegistry registry) {
	 * System.out.println("ggggggg"); registry.enableSimpleBroker("/topic");
	 * registry.setApplicationDestinationPrefixes("/app");
	 * 
	 * }
	 * 
	 * 
	 * /* @Override public void registerStompEndpoints(StompEndpointRegistry
	 * registry) { System.out.println("fdfdfd"); /*
	 * registry.addEndpoint("/websocket") .setAllowedOrigins("*") .withSockJS();
	 */

	/*
	 * registry.addEndpoint("/websocket").withSockJS(); }
	 */

	/*
	 * @Override public void registerStompEndpoints(StompEndpointRegistry registry)
	 * { registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins(
	 * "http://localhost:3000"); }
	 */

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(new CustomWebSocketHandler(userService,evChargingStationRepository,commonUtility), "/websocket")
				.setAllowedOrigins("*");
				

	}

}