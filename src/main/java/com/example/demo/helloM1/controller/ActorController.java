package com.example.demo.helloM1.controller;

import com.example.demo.helloM1.output.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/m1/actor")
public class ActorController {

    private final ChatClient chatClient;

    public ActorController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @RequestMapping("/films-string")
    public String getActorFilmsString() {
        return chatClient.prompt()
                .user("Generate a filmography for a Anthony Hopkins for the year 2010")
                .call()
                .content();
    }

    @RequestMapping("/films")
    public ActorFilms getActorFilms() {
        return chatClient.prompt()
                .user("Generate a filmography for a Anthony Hopkins for the year 2010")
                .call()
                .entity(ActorFilms.class);
    }

    @RequestMapping("/films-list")
    public List<ActorFilms> getListActorFilms() {
        return chatClient.prompt()
                .user("Generate a filmography for the actors Denzel Washington, Leonardo DiCaprio and Tom Hanks")
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
                });
    }
}
