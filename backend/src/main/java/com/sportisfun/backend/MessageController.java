package com.sportisfun.backend;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    @GetMapping("/message")
    public Message getMessage() {
        return new Message("Hello from Spring Boot!");
    }

    // Prosty DTO - normalnie w osobnej klasie
    static class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

