# 🤖 WhatsApp Chatbot Backend Simulation

A **Spring Boot REST API** that simulates a WhatsApp Cloud API webhook, complete with intelligent reply generation, in-memory message logging, and a beautiful interactive demo UI.

---

## 📋 Features

| Feature | Details |
|---|---|
| **Webhook Endpoint** | `POST /webhook` accepts WhatsApp-style JSON payloads |
| **Verification** | `GET /webhook` simulates Meta's hub.verify flow |
| **Smart Replies** | Keyword matching for hi, hello, bye, help, thanks + more |
| **Message Logging** | All messages logged to console + stored in-memory |
| **Log Viewer** | `GET /logs` returns full conversation history |
| **Command Docs** | `GET /commands` lists all supported keywords |
| **Docker Ready** | Multi-stage Dockerfile for Render / Railway / Fly.io |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+

### Run Locally

```bash
git clone https://github.com/YOUR_USERNAME/whatsapp-bot.git
cd whatsapp-bot
mvn spring-boot:run
```

Server starts at: **http://localhost:8080**

### Run with Docker

```bash
docker build -t whatsapp-bot .
docker run -p 8080:8080 whatsapp-bot
```

---

## 📡 API Reference

### POST `/webhook` — Send a message

```bash
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{
    "from": "+91-9876543210",
    "to": "+1-555-BOT-0001",
    "message": "Hi",
    "timestamp": "2024-01-15T10:30:00",
    "messageId": "wamid.abc123"
  }'
```

**Response:**
```json
{
  "status": "success",
  "to": "+91-9876543210",
  "reply": "Hello! 👋 How can I help you today?",
  "originalMessage": "Hi",
  "botName": "WA-Bot",
  "responseTimestamp": "2024-01-15 10:30:01"
}
```

---

### GET `/webhook` — Verify webhook

```bash
curl "http://localhost:8080/webhook\
?hub.mode=subscribe\
&hub.verify_token=wa_bot_secret_token\
&hub.challenge=1234567890"
```

---

### GET `/logs` — View message log

```bash
curl http://localhost:8080/logs
```

---

### GET `/commands` — List supported commands

```bash
curl http://localhost:8080/commands
```

---

## 💬 Supported Bot Commands

| User Input | Bot Reply |
|---|---|
| `hi` | Hello! 👋 How can I help you today? |
| `hello` | Hey there! 😊 What can I do for you? |
| `hey` | Hey! 👋 Nice to hear from you! |
| `bye` | Goodbye! 👋 Take care! |
| `goodbye` | See you later! 😊 Have a wonderful day! |
| `help` | Lists all supported commands |
| `thanks` | You're welcome! 😊 |
| `thank you` | My pleasure! 😄 |
| `how are you` | I'm doing great! 🤖 |
| `what is your name` | I'm WA-Bot! |
| `who are you` | I'm WA-Bot — a Spring Boot chatbot! |
| *anything else* | Friendly fallback with suggestions |

---

## 🏗️ Project Structure

```
whatsapp-bot/
├── src/
│   ├── main/
│   │   ├── java/com/whatsapp/bot/
│   │   │   ├── WhatsAppBotApplication.java   # Entry point
│   │   │   ├── controller/
│   │   │   │   └── WebhookController.java    # REST endpoints
│   │   │   ├── service/
│   │   │   │   └── ChatBotService.java       # Reply logic + logging
│   │   │   └── model/
│   │   │       ├── IncomingMessage.java      # Request payload
│   │   │       ├── WebhookResponse.java      # Response payload
│   │   │       └── MessageLog.java           # Log entry
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/whatsapp/bot/
│           └── WhatsAppBotApplicationTests.java
├── Dockerfile
├── pom.xml
└── README.md
```

---

## ☁️ Deploy on Render (Free Tier)

1. Push repo to GitHub
2. Go to [render.com](https://render.com) → New → Web Service
3. Connect your GitHub repo
4. Set:
   - **Environment**: Docker
   - **Build Command**: *(auto-detected from Dockerfile)*
   - **Start Command**: *(auto-detected)*
5. Click **Deploy**

Your bot will be live at: `https://your-app.onrender.com`

---

## 🧪 Run Tests

```bash
mvn test
```

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2**
- **Maven**
- **Docker** (multi-stage build)
- **SLF4J / Logback** for logging

---

## 📄 License

MIT — free to use and modify.
