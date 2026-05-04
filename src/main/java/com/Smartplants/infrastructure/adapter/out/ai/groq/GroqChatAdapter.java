package com.Smartplants.infrastructure.adapter.out.ai.groq;

import com.Smartplants.application.port.out.AiChatPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class GroqChatAdapter implements AiChatPort {

    private static final String SYSTEM_PROMPT = "Responder como asistente experto en cuidado de plantas. "
            + "Dar respuestas practicas, claras y seguras. "
            + "No inventar diagnosticos medicos definitivos de plantas. "
            + "Sugerir revisar riego, luz, sustrato, plagas y ambiente. "
            + "Si la pregunta es ajena a plantas, redirigir amablemente al tema de SmartPlant.";

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final String apiKey;
    private final String apiUrl;
    private final String model;

    public GroqChatAdapter(
            ObjectMapper objectMapper,
            @Value("${GROQ_API_KEY:}") String apiKey,
            @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}") String apiUrl,
            @Value("${groq.model:llama-3.3-70b-versatile}") String model
    ) {
        this.objectMapper = objectMapper;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.model = model;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public String generarRespuesta(String pregunta) {
        validarConfiguracion();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .timeout(Duration.ofSeconds(30))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(buildRequestBody(pregunta)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            validarRespuestaHttp(response);
            return extraerContenido(response.body());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("La comunicacion con Groq fue interrumpida", exception);
        } catch (IOException | RuntimeException exception) {
            throw new IllegalStateException("No fue posible obtener respuesta de Groq", exception);
        }
    }

    private String buildRequestBody(String pregunta) throws JacksonException {
        Map<String, Object> body = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", pregunta)
                ),
                "temperature", 0.4,
                "max_completion_tokens", 600
        );

        return objectMapper.writeValueAsString(body);
    }

    private void validarConfiguracion() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GROQ_API_KEY no esta configurada");
        }
        if (apiUrl == null || apiUrl.isBlank()) {
            throw new IllegalStateException("groq.api.url no esta configurada");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalStateException("groq.model no esta configurado");
        }
    }

    private void validarRespuestaHttp(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("Groq respondio con estado " + response.statusCode());
        }
    }

    private String extraerContenido(String responseBody) throws JacksonException {
        JsonNode root = objectMapper.readTree(responseBody);
        String content = root.path("choices")
                .path(0)
                .path("message")
                .path("content")
                .asText();

        if (content == null || content.isBlank()) {
            throw new IllegalStateException("Groq respondio sin contenido");
        }

        return content.trim();
    }
}
