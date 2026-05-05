package com.Smartplants.application.service;

import com.Smartplants.application.port.out.AiChatPort;
import com.Smartplants.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChatServiceTest {

    @Test
    void respondePreguntaValida() {
        ChatService service = new ChatService(pregunta -> "Revisa la luz y el riego.");

        String respuesta = service.responder("  Mi planta tiene hojas amarillas  ");

        assertEquals("Revisa la luz y el riego.", respuesta);
    }

    @Test
    void rechazaPreguntaVacia() {
        ChatService service = new ChatService(pregunta -> "No deberia llamarse");

        ValidationException exception = assertThrows(ValidationException.class, () -> service.responder(" "));

        assertEquals("La pregunta es obligatoria", exception.getMessage());
    }

    @Test
    void devuelveFallbackSiGroqFalla() {
        ChatService service = new ChatService(new FailingAiChatPort());

        String respuesta = service.responder("Como cuido una monstera?");

        assertEquals(
                "No pude conectar con la IA en este momento. "
                        + "Puedes revisar riego, luz, sustrato, plagas y ambiente de tu planta, y volver a intentar en unos minutos.",
                respuesta
        );
    }

    private static final class FailingAiChatPort implements AiChatPort {
        @Override
        public String generarRespuesta(String pregunta) {
            throw new IllegalStateException("Groq no disponible");
        }
    }
}
