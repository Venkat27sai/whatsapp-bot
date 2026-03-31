package com.whatsapp.bot.model;

/**
 * Represents a logged message entry (stored in memory).
 */
public class MessageLog {

    private String messageId;
    private String from;
    private String receivedMessage;
    private String botReply;
    private String timestamp;

    public MessageLog() {}

    public MessageLog(String messageId, String from, String receivedMessage,
                      String botReply, String timestamp) {
        this.messageId = messageId;
        this.from = from;
        this.receivedMessage = receivedMessage;
        this.botReply = botReply;
        this.timestamp = timestamp;
    }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getReceivedMessage() { return receivedMessage; }
    public void setReceivedMessage(String receivedMessage) { this.receivedMessage = receivedMessage; }

    public String getBotReply() { return botReply; }
    public void setBotReply(String botReply) { this.botReply = botReply; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
