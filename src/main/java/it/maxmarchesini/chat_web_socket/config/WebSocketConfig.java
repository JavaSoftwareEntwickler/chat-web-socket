package it.maxmarchesini.chat_web_socket.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // Dove ascolta il client
        config.setApplicationDestinationPrefixes("/app"); // Dove pubblica il client
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat") // endpoint di connessione WebSocket
                .setAllowedOriginPatterns("http://localhost:4200") // cors
                .withSockJS(); // fallback a SockJS
    }
}