package com.example.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.Map;

@RestController
@RequestMapping("/youtube")
public class YoutubeController {

    private final ChatClient chatClient;
    @Value("classpath:/prompts/youtube.st")
    private Resource youtubePromptResource;

    public YoutubeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/popular")
    public String findPopularYoutubersByGenre(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
        String message = """
                List 10 of the most popular Youtubers in {genre} along with their current subscriber counts. If you don't know the answer, just say "I don't know."
                """;
        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/v2/popular")
    public String findPopularYoutubersByGenreV2(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
        PromptTemplate promptTemplate = new PromptTemplate(youtubePromptResource);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        return chatClient.prompt(prompt).call().content();
    }
}
