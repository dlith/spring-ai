package com.example.demo.helloM1.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/m1")
public class ChatControllerNew {

    private final ChatClient chatClient;

    public ChatControllerNew(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("You are a loud assistant that responds with capital letters")
                .build();
    }

    @RequestMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @RequestMapping("/jokes")
    public String jokes(@RequestParam String topic) {
        return chatClient.prompt()
                .user(u -> u.text("Tell me a dad joke about {topic}").param("topic", topic))
                .call()
                .content();
    }
}
