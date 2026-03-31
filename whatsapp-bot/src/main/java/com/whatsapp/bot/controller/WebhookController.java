package com.whatsapp.bot.controller;

import com.whatsapp.bot.model.IncomingMessage;
import com.whatsapp.bot.model.MessageLog;
import com.whatsapp.bot.model.WebhookResponse;
import com.whatsapp.bot.service.ChatBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller exposing the WhatsApp webhook and supporting endpoints.
 *
 * Endpoints:
 *   POST /webhook          — Receive and process a WhatsApp-style message
 *   GET  /webhook          — Verify webhook (simulates Meta's hub.verify flow)
 *   GET  /logs             — View all received/replied messages
 *   GET  /commands         — List all supported bot commands
 *   GET  /health           — Simple health check
 */
@RestController
@CrossOrigin(origins = "*")  // Allow the demo UI to call the API
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final ChatBotService chatBotService;

    public WebhookController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    // ─────────────────────────────────────────────
    // POST /webhook  — Core message handler
    // ─────────────────────────────────────────────

    /**
     * Receives a JSON payload simulating a WhatsApp Cloud API webhook event.
     *
     * Example request body:
     * {
     *   "from": "+91-9876543210",
     *   "to":   "+1-555-BOT-0001",
     *   "message": "Hi",
     *   "timestamp": "2024-01-15T10:30:00",
     *   "messageId": "wamid.abc123"
     * }
     */
    @PostMapping("/webhook")
    public ResponseEntity<WebhookResponse> receiveMessage(
            @RequestBody IncomingMessage incomingMessage) {

        logger.info("🔔 POST /webhook called from: {}", incomingMessage.getFrom());

        if (incomingMessage.getMessage() == null) {
            WebhookResponse error = new WebhookResponse(
                    "error", null, "Missing 'message' field in request body.",
                    null, "WA-Bot", null);
            return ResponseEntity.badRequest().body(error);
        }

        WebhookResponse response = chatBotService.processMessage(incomingMessage);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────
    // GET /webhook  — Webhook verification (Meta style)
    // ─────────────────────────────────────────────

    /**
     * Simulates WhatsApp's hub verification challenge.
     * Real Meta webhooks send: hub.mode, hub.verify_token, hub.challenge
     */
    @GetMapping("/webhook")
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(name = "hub.mode",         required = false) String mode,
            @RequestParam(name = "hub.verify_token", required = false) String token,
            @RequestParam(name = "hub.challenge",    required = false) String challenge) {

        String verifyToken = "wa_bot_secret_token";

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            logger.info("✅ Webhook verified successfully");
            return ResponseEntity.ok(challenge);
        }

        logger.warn("⚠️  Webhook verification failed — invalid token or mode");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verification failed");
    }

    // ─────────────────────────────────────────────
    // GET /logs — Message history
    // ─────────────────────────────────────────────

    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getLogs() {
        List<MessageLog> logs = chatBotService.getAllLogs();

        Map<String, Object> response = new HashMap<>();
        response.put("totalMessages", logs.size());
        response.put("messages", logs);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────
    // GET /commands — Supported bot commands
    // ─────────────────────────────────────────────

    @GetMapping("/commands")
    public ResponseEntity<Map<String, Object>> getCommands() {
        Map<String, Object> response = new HashMap<>();
        response.put("botName", "WA-Bot");
        response.put("description", "WhatsApp Chatbot Simulation powered by Spring Boot");
        response.put("supportedCommands", chatBotService.getReplyMap());

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────────
    // GET /health — Health check
    // ─────────────────────────────────────────────

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "WhatsApp Bot Simulation");
        status.put("version", "1.0.0");

        return ResponseEntity.ok(status);
    }

    // ─────────────────────────────────────────────
    // GET / — Welcome / API docs page
    // ─────────────────────────────────────────────

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> index() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "WhatsApp Bot Simulation API");
        info.put("version", "1.0.0");
        info.put("framework", "Spring Boot 3.2");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("POST /webhook",   "Send a message to the bot");
        endpoints.put("GET  /webhook",   "Verify webhook (hub.mode + hub.verify_token + hub.challenge)");
        endpoints.put("GET  /logs",      "View all message logs");
        endpoints.put("GET  /commands",  "List supported bot commands");
        endpoints.put("GET  /health",    "Health status");
        info.put("endpoints", endpoints);

        return ResponseEntity.ok(info);
    }
}
