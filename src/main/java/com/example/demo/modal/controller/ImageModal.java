package com.example.demo.modal.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageModal {

    private final ChatClient chatClient;

    public ImageModal(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/describe-image")
    public String describeImage() throws IOException {
        byte[] imageData = new ClassPathResource("/images/sincerely-media-2UlZpdNzn2w-unsplash.jpg").getContentAsByteArray();
        Media media = new Media(MimeTypeUtils.IMAGE_JPEG, new ByteArrayResource(imageData));
        UserMessage userMessage = new UserMessage("Can you please explain what you see in the following image?", media);
        ChatResponse chatResponse = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping("/describe-code")
    public String describeCode() throws IOException {
        byte[] imageData = new ClassPathResource("/images/java-open-ai.png").getContentAsByteArray();
        Media media = new Media(MimeTypeUtils.IMAGE_PNG, new ByteArrayResource(imageData));
        UserMessage userMessage = new UserMessage("The following is a screenshot of some code. Can you do your best provide a description of what the code is doing?", media);
        ChatResponse chatResponse = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        return chatResponse.getResult().getOutput().getContent();
    }

    @GetMapping("/describe-to-code")
    public String imageToCode() throws IOException {
        byte[] imageData = new ClassPathResource("/images/java-open-ai.png").getContentAsByteArray();
        Media media = new Media(MimeTypeUtils.IMAGE_PNG, new ByteArrayResource(imageData));
        UserMessage userMessage = new UserMessage("The following is a screenshot of some code. Can you translate this from image into actual code?", media);
        ChatResponse chatResponse = chatClient.prompt(new Prompt(userMessage)).call().chatResponse();
        return chatResponse.getResult().getOutput().getContent();
    }
}
