package it.maxmarchesini.chat_web_socket.model;

public class ChatMessage {
    private String sender;
    private String content;

    // Getter/Setter
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
