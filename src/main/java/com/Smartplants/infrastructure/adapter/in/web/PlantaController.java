package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.PlantaCommand;
import com.Smartplants.application.port.in.PlantaUseCase;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.infrastructure.adapter.in.web.request.PlantaRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CatalogoResponse;
import com.Smartplants.infrastructure.adapter.in.web.response.PlantaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantas")
@RequiredArgsConstructor
public class PlantaController {

    private final PlantaUseCase useCase;

    @GetMapping
    public List<PlantaResponse> listar() {
        return useCase.listar().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PlantaResponse obtenerPorId(@PathVariable Long id) {
        return toResponse(useCase.obtenerPorId(id));
    }

    @PostMapping
    public PlantaResponse crear(@RequestBody PlantaRequest request) {
        return toResponse(useCase.crear(toCommand(request)));
    }

    @PutMapping("/{id}")
    public PlantaResponse actualizar(@PathVariable Long id, @RequestBody PlantaRequest request) {
        return toResponse(useCase.actualizar(id, toCommand(request)));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        useCase.eliminar(id);
    }

    private PlantaCommand toCommand(PlantaRequest request) {
        return PlantaCommand.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .luz(request.getLuz())
                .riego(request.getRiego())
                .temperatura(request.getTemperatura())
                .tamano(request.getTamano())
                .ambiente(request.getAmbiente())
                .tipoId(request.getTipoId())
                .familiaId(request.getFamiliaId())
                .mantenimientoId(request.getMantenimientoId())
                .saludId(request.getSaludId())
                .build();
    }

    private PlantaResponse toResponse(Planta planta) {
        return PlantaResponse.builder()
                .id(planta.getId())
                .nombre(planta.getNombre())
                .descripcion(planta.getDescripcion())
                .tipo(toCatalogo(planta.getTipo().getId(), planta.getTipo().getNombre()))
                .familia(toCatalogo(planta.getFamilia().getId(), planta.getFamilia().getNombre()))
                .mantenimiento(toCatalogo(planta.getMantenimiento().getId(), planta.getMantenimiento().getNivel()))
                .salud(toCatalogo(planta.getSalud().getId(), planta.getSalud().getEstado()))
                .build();
    }

    private CatalogoResponse toCatalogo(Long id, String valor) {
        return CatalogoResponse.builder()
                .id(id)
                .valor(valor)
                .build();
    }
}
