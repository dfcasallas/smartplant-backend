package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.port.in.SugerenciaUseCase;
import com.Smartplants.domain.model.PreferenciasUsuario;
import com.Smartplants.domain.model.ResultadoSugerencia;
import com.Smartplants.infrastructure.adapter.in.web.request.SugerenciaRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.SugerenciaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sugerencias")
@RequiredArgsConstructor
public class SugerenciaController {

    private final SugerenciaUseCase useCase;

    @PostMapping
    public SugerenciaResponse sugerir(@RequestBody SugerenciaRequest request) {
        return toResponse(useCase.sugerir(toDomain(request)));
    }

    private PreferenciasUsuario toDomain(SugerenciaRequest request) {
        return PreferenciasUsuario.builder()
                .mantenimiento(request.getMantenimiento())
                .luz(request.getLuz())
                .riego(request.getRiego())
                .temperatura(request.getTemperatura())
                .tamano(request.getTamano())
                .ambiente(request.getAmbiente())
                .build();
    }

    private SugerenciaResponse toResponse(ResultadoSugerencia resultado) {
        return SugerenciaResponse.builder()
                .plantaId(resultado.getPlanta().getId())
                .nombre(resultado.getPlanta().getNombre())
                .descripcion(resultado.getPlanta().getDescripcion())
                .mantenimiento(resultado.getPlanta().getMantenimiento() != null ? resultado.getPlanta().getMantenimiento().getNivel() : null)
                .puntaje(resultado.getPuntaje())
                .build();
    }
}
