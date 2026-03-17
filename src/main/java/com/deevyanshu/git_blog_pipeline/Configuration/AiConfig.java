package com.deevyanshu.git_blog_pipeline.Configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiEmbeddingConnectionDetails;
import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingModel;
import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder)
    {
        return builder.build();
    }

    @Bean
    public GoogleGenAiTextEmbeddingModel embeddingModel(@Value("${spring.ai.google.genai.api-key}") String apiKey) {
        // Initialize the API client
        var api =  GoogleGenAiEmbeddingConnectionDetails.builder().apiKey(apiKey).build();
        // Configure options (model name, dimensions, etc.)
        var options = GoogleGenAiTextEmbeddingOptions.builder()
                .model("gemini-embedding-001")
                .dimensions(768)// Recommended for Gemini
                .build();

        return new GoogleGenAiTextEmbeddingModel(api, options);
    }
}
