package it.maxmarchesini.chat_web_socket.controller;


import it.maxmarchesini.chat_web_socket.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @MessageMapping("/chat.send") // riceve da /app/chat.send
    @SendTo("/topic/messages")    // inoltra a /topic/messages
    public ChatMessage send(ChatMessage message) {
        return message;
    }
}