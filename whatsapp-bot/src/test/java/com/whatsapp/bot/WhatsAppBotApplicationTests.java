package com.whatsapp.bot;

import com.whatsapp.bot.model.IncomingMessage;
import com.whatsapp.bot.model.WebhookResponse;
import com.whatsapp.bot.service.ChatBotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WhatsAppBotApplicationTests {

    @Autowired
    private ChatBotService chatBotService;

    @Test
    void contextLoads() {
        assertNotNull(chatBotService);
    }

    @Test
    void testHiReply() {
        IncomingMessage msg = new IncomingMessage(
            "+91-9999999999", "+1-555-BOT", "Hi", null, "test-001");
        WebhookResponse response = chatBotService.processMessage(msg);
        assertEquals("success", response.getStatus());
        assertTrue(response.getReply().toLowerCase().contains("hello"));
    }

    @Test
    void testByeReply() {
        IncomingMessage msg = new IncomingMessage(
            "+91-9999999999", "+1-555-BOT", "Bye", null, "test-002");
        WebhookResponse response = chatBotService.processMessage(msg);
        assertEquals("success", response.getStatus());
        assertTrue(response.getReply().toLowerCase().contains("goodbye") 
                || response.getReply().toLowerCase().contains("take care"));
    }

    @Test
    void testHelpReply() {
        IncomingMessage msg = new IncomingMessage(
            "+91-9999999999", "+1-555-BOT", "help", null, "test-003");
        WebhookResponse response = chatBotService.processMessage(msg);
        assertEquals("success", response.getStatus());
        assertNotNull(response.getReply());
    }

    @Test
    void testUnknownMessage() {
        IncomingMessage msg = new IncomingMessage(
            "+91-9999999999", "+1-555-BOT", "xyzabc123", null, "test-004");
        WebhookResponse response = chatBotService.processMessage(msg);
        assertEquals("success", response.getStatus());
        assertTrue(response.getReply().contains("not sure"));
    }

    @Test
    void testMessageLogging() {
        int before = chatBotService.getAllLogs().size();
        IncomingMessage msg = new IncomingMessage(
            "+91-1111111111", "+1-555-BOT", "Hello", null, "test-005");
        chatBotService.processMessage(msg);
        assertEquals(before + 1, chatBotService.getAllLogs().size());
    }
}
