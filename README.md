# 🤖 WhatsApp Chatbot Backend Simulation

A production-style **REST API backend** built with **Java 17** and **Spring Boot 3.2**
that simulates a WhatsApp Cloud API webhook — receiving messages, generating intelligent
replies, and logging every conversation.

---

## 🚀 Live Demo

> Server runs locally at `http://localhost:8080`
> Deploy instantly on Render / Railway using the included Dockerfile.

---

## 💡 Why This Project?

WhatsApp is used by 2+ billion people worldwide. Businesses rely on chatbots to handle
customer queries 24/7 — but building one requires understanding how webhooks, REST APIs,
and message routing work under the hood.

This project simulates exactly that backend layer — without needing a real WhatsApp
Business account or any paid API access. Just Java, Spring Boot, and a POST request.

---

## ✅ What It Does

- Accepts JSON payloads at `POST /webhook` simulating real WhatsApp messages
- Matches user input against a keyword dictionary and returns smart replies
- **Hi → Hello**, **Bye → Goodbye**, and 9 more built-in responses
- Logs every incoming message and bot reply to the console using SLF4J
- Stores full conversation history in memory — viewable at `GET /logs`
- Verifies webhooks via Meta's `hub.verify` flow at `GET /webhook`
- Ships with 5 JUnit unit tests and a Docker-ready multi-stage build

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Build Tool | Apache Maven |
| Logging | SLF4J + Logback |
| Testing | JUnit 5 |
| Container | Docker |
