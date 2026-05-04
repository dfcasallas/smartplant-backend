package com.Smartplants.application.service;

import com.Smartplants.application.port.in.ChatUseCase;
import com.Smartplants.application.port.out.AiChatPort;
import com.Smartplants.domain.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService implements ChatUseCase {

    private static final String FALLBACK_RESPONSE = "No pude conectar con la IA en este momento. "
            + "Puedes revisar riego, luz, sustrato, plagas y ambiente de tu planta, y volver a intentar en unos minutos.";

    private final AiChatPort aiChatPort;

    @Override
    public String responder(String pregunta) {
        validarPregunta(pregunta);

        try {
            String respuesta = aiChatPort.generarRespuesta(pregunta.trim());
            return respuesta == null || respuesta.isBlank() ? FALLBACK_RESPONSE : respuesta.trim();
        } catch (RuntimeException exception) {
            return FALLBACK_RESPONSE;
        }
    }

    private void validarPregunta(String pregunta) {
        if (pregunta == null || pregunta.isBlank()) {
            throw new ValidationException("La pregunta es obligatoria");
        }
    }
}
