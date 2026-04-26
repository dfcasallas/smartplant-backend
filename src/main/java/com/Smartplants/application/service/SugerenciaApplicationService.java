package com.Smartplants.application.service;

import com.Smartplants.application.port.in.SugerenciaUseCase;
import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.domain.model.PreferenciasUsuario;
import com.Smartplants.domain.model.ResultadoSugerencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SugerenciaApplicationService implements SugerenciaUseCase {

    private final PlantaRepositoryPort plantaRepository;

    @Override
    public ResultadoSugerencia sugerir(PreferenciasUsuario preferencias) {
        validarPreferencias(preferencias);

        List<Planta> plantas = plantaRepository.findAll();
        if (plantas.isEmpty()) {
            throw new ResourceNotFoundException("No hay plantas disponibles para sugerencias");
        }

        return plantas.stream()
                .map(planta -> ResultadoSugerencia.builder()
                        .planta(planta)
                        .puntaje(calcularPuntaje(planta, preferencias))
                        .build())
                .max(Comparator
                        .comparingInt(ResultadoSugerencia::getPuntaje)
                        .thenComparing(resultado -> coincideMantenimiento(resultado.getPlanta(), preferencias))
                        .thenComparing(resultado -> resultado.getPlanta().getId(), Comparator.nullsLast(Comparator.reverseOrder())))
                .orElseThrow(() -> new ResourceNotFoundException("No hay plantas disponibles para sugerencias"));
    }

    private int calcularPuntaje(Planta planta, PreferenciasUsuario preferencias) {
        int puntaje = 0;
        puntaje += coincide(planta.getLuz(), preferencias.getLuz());
        puntaje += coincide(planta.getRiego(), preferencias.getRiego());
        puntaje += coincide(planta.getTemperatura(), preferencias.getTemperatura());
        puntaje += coincide(planta.getTamano(), preferencias.getTamano());
        puntaje += coincide(planta.getAmbiente(), preferencias.getAmbiente());
        return puntaje;
    }

    private int coincide(Integer valorPlanta, Integer valorPreferencia) {
        return valorPlanta != null && valorPlanta.equals(valorPreferencia) ? 1 : 0;
    }

    private void validarPreferencias(PreferenciasUsuario preferencias) {
        if (preferencias == null) {
            throw new ValidationException("Las preferencias son obligatorias");
        }

        validarTexto(preferencias.getMantenimiento(), "El mantenimiento es obligatorio");
        validarPositivo(preferencias.getLuz(), "La luz es obligatoria");
        validarPositivo(preferencias.getRiego(), "El riego es obligatorio");
        validarPositivo(preferencias.getTemperatura(), "La temperatura es obligatoria");
        validarPositivo(preferencias.getTamano(), "El tamano es obligatorio");
        validarPositivo(preferencias.getAmbiente(), "El ambiente es obligatorio");
    }

    private void validarTexto(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensajeError);
        }
    }

    private void validarPositivo(Integer valor, String mensajeError) {
        if (valor == null || valor <= 0) {
            throw new ValidationException(mensajeError);
        }
    }

    private boolean coincideMantenimiento(Planta planta, PreferenciasUsuario preferencias) {
        if (planta == null || planta.getMantenimiento() == null || planta.getMantenimiento().getNivel() == null) {
            return false;
        }
        return planta.getMantenimiento().getNivel().trim().equalsIgnoreCase(preferencias.getMantenimiento().trim());
    }
}
