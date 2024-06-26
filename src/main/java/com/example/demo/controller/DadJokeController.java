package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dad-jokes")
public class DadJokeController {

    private final ChatClient chatClient;

    public DadJokeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/joke")
    public String jokes() {
        SystemMessage system = new SystemMessage("Your primary function is to tell Dad jokes. If someone asks for any other type of joke please tell them you only know Dad jokes");
        UserMessage user = new UserMessage("Tell me a serious joke about the universe");
        Prompt prompt = new Prompt(List.of(system, user));
        return chatClient.prompt(prompt).call().content();
    }
}
