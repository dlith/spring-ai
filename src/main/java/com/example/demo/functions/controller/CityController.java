package com.example.demo.functions.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private final ChatClient chatClient;

    public CityController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("")
    public String cities(@RequestParam(value = "message") String message) {
        SystemMessage systemMessage = new SystemMessage("You are helpful AI assistant answering questions about cities around the world");
        UserMessage userMessage = new UserMessage(message);

        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withFunction("currentWeatherFunction").build();
        ChatResponse response = chatClient.prompt(new Prompt(List.of(systemMessage, userMessage), chatOptions)).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }

}
