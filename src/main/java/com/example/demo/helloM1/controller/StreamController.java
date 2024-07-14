package com.example.demo.helloM1.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/m1")
public class StreamController {

    private final ChatClient chatClient;

    public StreamController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/stream")
    public Flux<String> stream() {
        return chatClient.prompt()
                .user("I am traveling to Kansas City next week that are 10 of the best BBQ joints in the city!")
                .stream()
                .content();
    }
}
