package it.maxmarchesini.chat_web_socket.message;

public record ChatMessage(String sender, String content, MessageType messageType) {}