# Chat Pubblica - Backend Spring Boot

## Descrizione

Backend di una semplice applicazione di chat pubblica real-time sviluppata in **Spring Boot**.  
Espone un endpoint WebSocket compatibile con **STOMP over SockJS**, che riceve e inoltra messaggi in broadcast a tutti i client connessi.

---

## Caratteristiche principali

- WebSocket abilitato con Spring Messaging (`@EnableWebSocketMessageBroker`)
- Configurazione tramite STOMP con fallback SockJS
- Routing semplice dei messaggi `/app/chat.send` → `/topic/messages`
- Scambio di messaggi tramite oggetti JSON `ChatMessage`
- Configurazione CORS aperta per sviluppo (`setAllowedOriginPatterns("*")`)

---

## Requisiti

- Java 17+  
- Maven 3.8+  
- Spring Boot 3.x  
- IDE come IntelliJ IDEA, Eclipse o VS Code  

---

## Avvio del progetto

1. Clona il repository backend:

```bash
git clone https://github.com/JavaSoftwareEntwickler/chat-web-socket.git
cd chat-web-socket
````

2. Compila ed esegui con Maven:

```bash
./mvnw spring-boot:run
```

L'applicazione sarà disponibile su `http://localhost:8080`.

---

## Architettura WebSocket

| Tipo        | Path              | Descrizione                                 |
| ----------- | ----------------- | ------------------------------------------- |
| Endpoint WS | `/ws-chat`        | Punto di connessione SockJS/WebSocket       |
| Publish     | `/app/chat.send`  | Dove i client inviano i messaggi            |
| Subscribe   | `/topic/messages` | Dove i client ricevono i messaggi broadcast |

---

## Struttura del progetto

```
src/
└── main/
    └── java/it/maxmarchesini/chat_web_socket/
        ├── controller/ChatController.java
        ├── config/WebSocketConfig.java
        └── model/ChatMessage.java
```

---

## Esempio JSON messaggio

```json
{
  "sender": "Mario",
  "content": "Ciao a tutti!"
}
```

---

## Possibili evoluzioni

* Integrazione con autenticazione OAuth2 / JWT
* Persistenza dei messaggi (es. PostgreSQL, MongoDB)
* Logging e tracciamento eventi WebSocket
* Integrazione con Redis Pub/Sub per scalabilità orizzontale
* Filtri contenuti, moderazione e gestione utenti

---

## Frontend associato

Il progetto è pensato per lavorare insieme al [Frontend Angular Chat](https://github.com/JavaSoftwareEntwickler/FE-chat-web-socket), che si connette al WebSocket `/ws-chat`.

---

## Contatti

Max Marchesini – [Sito internet](https://maxmarchesini.it)  
[LinkedIn](https://www.linkedin.com/in/mmjava/) | [GitHub](https://github.com/JavaSoftwareEntwickler)

---