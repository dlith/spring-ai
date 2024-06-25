package com.example.demo.outputparser.controller;

import com.example.demo.outputparser.controller.entity.Author;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final ChatClient chatClient;

    public BookController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/author/{author}")
    public Map<String, Object> getAuthorsSocialLinks(@PathVariable String author) {
        String message = """
                Generate a list of links for the author {author}. Include the authors name as the key and any social network links as the object.
                {format}
                """;
        MapOutputConverter mapOutputConverter = new MapOutputConverter();
        String format = mapOutputConverter.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("author", author, "format", format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.prompt(prompt).call().chatResponse().getResult();
        return mapOutputConverter.convert(generation.getOutput().getContent());
    }

    @GetMapping("/byAuthor")
    public Author getBookByAuthor(@RequestParam(value = "author", defaultValue = "Ken Kousen") String author) {
        String message = """
                Generate a list of book written by the author {author}. If you aren't positive that a book belong to this author please don't include it.
                {format}
                """;
        BeanOutputConverter<Author> outputConverter = new BeanOutputConverter<>(Author.class);
        String format = outputConverter.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("author", author, "format", format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.prompt(prompt).call().chatResponse().getResult();
        return outputConverter.convert(generation.getOutput().getContent());
    }
}
