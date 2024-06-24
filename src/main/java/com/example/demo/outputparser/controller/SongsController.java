package com.example.demo.outputparser.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.ai.parser.OutputParser;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
public class SongsController {

    private final ChatClient chatClient;

    public SongsController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/v1/popular")
    public String getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Taylor Swift") String artist) {
        String message = """
                Please give me a list of top 10 songs for the artist {artist}. If you don't know the answer, just say "I don't know."
                """;
        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("artist", artist));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return response.getResult().getOutput().getContent();
    }

    @GetMapping("/v2/popular")
    public List<String> getSongsByArtistV2(@RequestParam(value = "artist", defaultValue = "Taylor Swift") String artist) {
        String message = """
                Please give me a list of top 10 songs for the artist {artist}. If you don't know the answer, just say "I don't know."
                {format}
                """;
        ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());
        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("artist", artist, "format", listOutputConverter.getFormat()));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        return listOutputConverter.convert(response.getResult().getOutput().getContent());
    }
}
