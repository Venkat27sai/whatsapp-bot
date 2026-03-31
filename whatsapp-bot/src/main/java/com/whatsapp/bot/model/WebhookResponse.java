package com.whatsapp.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the bot's reply sent back via the webhook.
 */
public class WebhookResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("to")
    private String to;

    @JsonProperty("reply")
    private String reply;

    @JsonProperty("originalMessage")
    private String originalMessage;

    @JsonProperty("botName")
    private String botName;

    @JsonProperty("responseTimestamp")
    private String responseTimestamp;

    // Constructors
    public WebhookResponse() {}

    public WebhookResponse(String status, String to, String reply,
                           String originalMessage, String botName, String responseTimestamp) {
        this.status = status;
        this.to = to;
        this.reply = reply;
        this.originalMessage = originalMessage;
        this.botName = botName;
        this.responseTimestamp = responseTimestamp;
    }

    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public String getOriginalMessage() { return originalMessage; }
    public void setOriginalMessage(String originalMessage) { this.originalMessage = originalMessage; }

    public String getBotName() { return botName; }
    public void setBotName(String botName) { this.botName = botName; }

    public String getResponseTimestamp() { return responseTimestamp; }
    public void setResponseTimestamp(String responseTimestamp) { this.responseTimestamp = responseTimestamp; }
}
