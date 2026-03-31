package com.whatsapp.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an incoming WhatsApp-style message payload.
 */
public class IncomingMessage {

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("messageId")
    private String messageId;

    // Constructors
    public IncomingMessage() {}

    public IncomingMessage(String from, String to, String message, String timestamp, String messageId) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    // Getters and Setters
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    @Override
    public String toString() {
        return "IncomingMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
