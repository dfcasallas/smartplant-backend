package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.port.in.ChatUseCase;
import com.Smartplants.infrastructure.adapter.in.web.request.ChatRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatUseCase useCase;

    @PostMapping
    public ChatResponse responder(@RequestBody ChatRequest request) {
        return ChatResponse.builder()
                .respuesta(useCase.responder(request != null ? request.getPregunta() : null))
                .build();
    }
}
