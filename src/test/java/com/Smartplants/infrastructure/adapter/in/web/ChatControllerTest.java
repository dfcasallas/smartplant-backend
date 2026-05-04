package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.infrastructure.adapter.in.web.request.ChatRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.ChatResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatControllerTest {

    @Test
    void respondeUsandoCasoDeUso() {
        ChatController controller = new ChatController(pregunta -> "Respuesta IA");
        ChatRequest request = new ChatRequest();
        request.setPregunta("Como cuido una suculenta?");

        ChatResponse response = controller.responder(request);

        assertEquals("Respuesta IA", response.getRespuesta());
    }
}
