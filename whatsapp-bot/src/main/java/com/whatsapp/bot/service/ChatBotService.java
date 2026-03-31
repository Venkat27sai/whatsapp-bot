package com.whatsapp.bot.service;

import com.whatsapp.bot.model.IncomingMessage;
import com.whatsapp.bot.model.MessageLog;
import com.whatsapp.bot.model.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Core chatbot service: handles message parsing, reply generation, and logging.
 */
@Service
public class ChatBotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBotService.class);

    private static final String BOT_NAME = "WA-Bot";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // In-memory message log
    private final List<MessageLog> messageLogs = new ArrayList<>();

    /**
     * Predefined reply map — case-insensitive keyword matching.
     * Add more entries here to expand the bot's vocabulary.
     */
    private static final Map<String, String> REPLY_MAP;

    static {
        REPLY_MAP = new LinkedHashMap<>();
        // Greetings
        REPLY_MAP.put("hi",       "Hello! 👋 How can I help you today?");
        REPLY_MAP.put("hello",    "Hey there! 😊 What can I do for you?");
        REPLY_MAP.put("hey",      "Hey! 👋 Nice to hear from you!");
        REPLY_MAP.put("good morning", "Good morning! ☀️ Hope you have a great day!");
        REPLY_MAP.put("good evening", "Good evening! 🌙 How's your day been?");
        // Farewells
        REPLY_MAP.put("bye",      "Goodbye! 👋 Take care!");
        REPLY_MAP.put("goodbye",  "See you later! 😊 Have a wonderful day!");
        REPLY_MAP.put("see you",  "See you soon! 👋");
        // Help & Info
        REPLY_MAP.put("help",     "Sure! I can respond to: hi, hello, hey, bye, goodbye, help, thanks, how are you. Try one!");
        REPLY_MAP.put("thanks",   "You're welcome! 😊 Anything else I can help with?");
        REPLY_MAP.put("thank you","My pleasure! 😄 Feel free to ask anytime.");
        REPLY_MAP.put("how are you", "I'm doing great, thanks for asking! 🤖 How about you?");
        REPLY_MAP.put("what is your name", "I'm WA-Bot, your WhatsApp assistant! 🤖");
        REPLY_MAP.put("who are you", "I'm WA-Bot — a Spring Boot-powered WhatsApp chatbot simulation! 💬");
    }

    /**
     * Main entry point: processes an incoming message and returns a bot response.
     */
    public WebhookResponse processMessage(IncomingMessage incoming) {
        String rawMessage = incoming.getMessage();
        String from       = incoming.getFrom() != null ? incoming.getFrom() : "unknown";
        String messageId  = incoming.getMessageId() != null
                ? incoming.getMessageId()
                : "msg-" + System.currentTimeMillis();
        String timestamp  = LocalDateTime.now().format(FORMATTER);

        // Log incoming message
        logger.info("📩 Incoming message | From: {} | MsgId: {} | Content: \"{}\"",
                from, messageId, rawMessage);

        // Generate reply
        String reply = generateReply(rawMessage);

        // Log the reply
        logger.info("🤖 Bot reply       | To:   {} | Reply: \"{}\"", from, reply);

        // Store in memory log
        MessageLog log = new MessageLog(messageId, from, rawMessage, reply, timestamp);
        messageLogs.add(log);

        // Build and return response
        return new WebhookResponse(
                "success",
                from,
                reply,
                rawMessage,
                BOT_NAME,
                timestamp
        );
    }

    /**
     * Generates a reply for a given input message using the REPLY_MAP.
     * Falls back to a default message if no match is found.
     */
    private String generateReply(String message) {
        if (message == null || message.isBlank()) {
            return "I received an empty message. Please send something! 🙂";
        }

        String normalized = message.trim().toLowerCase();

        // Exact-match first
        if (REPLY_MAP.containsKey(normalized)) {
            return REPLY_MAP.get(normalized);
        }

        // Partial / contains match
        for (Map.Entry<String, String> entry : REPLY_MAP.entrySet()) {
            if (normalized.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Default fallback
        return "I'm not sure how to respond to \"" + message + "\" yet. "
                + "Try: hi, hello, bye, help, thanks, how are you 🤔";
    }

    /**
     * Returns an unmodifiable view of all logged messages.
     */
    public List<MessageLog> getAllLogs() {
        return Collections.unmodifiableList(messageLogs);
    }

    /**
     * Returns the full reply map (for documentation / info endpoint).
     */
    public Map<String, String> getReplyMap() {
        return Collections.unmodifiableMap(REPLY_MAP);
    }
}
