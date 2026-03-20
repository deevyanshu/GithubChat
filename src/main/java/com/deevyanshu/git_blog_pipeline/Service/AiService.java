package com.deevyanshu.git_blog_pipeline.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.pinecone.PineconeVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiService {

    private ChatClient chatClient;

    @Autowired
    private PineconeVectorStore vectorStore;

    public AiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String ques,String url){

        var advisor= QuestionAnswerAdvisor.builder(vectorStore).
                searchRequest(SearchRequest.builder().similarityThreshold(0.5d).
                        filterExpression("repo=='"+url+"'").topK(3).build()).build();

        return chatClient.prompt(ques)
                .advisors(advisor)
                .call()
                .content();
    }

    public Flux<String> streamResponse(String ques,String url){
        var advisor= QuestionAnswerAdvisor.builder(vectorStore).
                searchRequest(SearchRequest.builder().similarityThreshold(0.5d).
                        filterExpression("repo=='"+url+"'").topK(3).build()).build();

        return chatClient.prompt(ques)
                .advisors(advisor)
                .stream()
                .content();
    }
}
