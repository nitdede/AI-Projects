package com.example.AIdemo.controller;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final ConversationalRetrievalChain retrievalChain;

    public ChatController(ConversationalRetrievalChain retrievalChain) {
        this.retrievalChain = retrievalChain;
    }

    @PostMapping("/getPdfChat")
    public String getPdfChat(@RequestBody String message) {
        return retrievalChain.execute(message);
    }
}
