package it.maxmarchesini.chat_web_socket.controller;


import it.maxmarchesini.chat_web_socket.message.ChatMessage;
import it.maxmarchesini.chat_web_socket.users.ActiveUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ActiveUserStore userStore;
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    public ChatController(ActiveUserStore userStore, SimpMessagingTemplate messagingTemplate) {
        this.userStore = userStore;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send") // riceve da /app/chat.send
    @SendTo("/topic/messages")    // inoltra a /topic/messages
    public ChatMessage send(@Payload ChatMessage message) {
        return message;
    }

    // inoltra a /topic/pubblic
    // inoltra a /topic/users
    @MessageMapping("/chat.join")  // riceve da /app/chat.join
    public void addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        // Aggiunge il nome dell'utente all'header della sessione
        String username = message.sender();

        String senderInSession = (String) headerAccessor.getSessionAttributes().get("sender");
        if(senderInSession != message.sender()) {
            headerAccessor.getSessionAttributes().put("sender", username);
        }
        // Aggiunge l'utente all'elenco se non presente
        if (!userStore.contains(username)) {
            userStore.addUser(username);
        }
        // Invia messaggio JOIN a tutti
        messagingTemplate.convertAndSend("/topic/messages", message);
        // Invia lista utenti aggiornata a tutti
        messagingTemplate.convertAndSend("/topic/users", userStore.getActiveUsers());
    }
}