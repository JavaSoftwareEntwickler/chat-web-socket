package it.maxmarchesini.chat_web_socket.config;

import it.maxmarchesini.chat_web_socket.message.MessageType;
import it.maxmarchesini.chat_web_socket.message.ChatMessage;
import it.maxmarchesini.chat_web_socket.users.ActiveUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.logging.Logger;

@Component
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;
    Logger log = Logger.getLogger(WebSocketEventListener.class.getName());
    private final ActiveUserStore userStore;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, ActiveUserStore userStore) {
        this.messagingTemplate = messagingTemplate;
        this.userStore = userStore;
    }
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("sessionId: " + headerAccessor.getSessionId());
        String sender = (String) headerAccessor.getSessionAttributes().get("sender");
        if (sender != null) {
            userStore.removeUser(sender);
            messagingTemplate.convertAndSend("/topic/users", userStore.getActiveUsers());
            log.info("user disconnected: " + sender);
            var chatMessage = new ChatMessage(sender, "left the chat", MessageType.LEAVE);
            messagingTemplate.convertAndSend("/topic/messages", chatMessage);
        }
    }
}
