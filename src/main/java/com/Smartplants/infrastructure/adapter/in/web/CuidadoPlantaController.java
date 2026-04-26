package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.RegistrarCuidadoCommand;
import com.Smartplants.application.port.in.CuidadoPlantaUseCase;
import com.Smartplants.domain.model.CuidadoPlanta;
import com.Smartplants.infrastructure.adapter.in.web.request.RegistrarCuidadoRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CuidadoPlantaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CuidadoPlantaController {

    private final CuidadoPlantaUseCase useCase;

    @PostMapping("/api/inventario/{inventarioId}/cuidados")
    public CuidadoPlantaResponse registrar(
            @PathVariable Long inventarioId,
            @RequestBody RegistrarCuidadoRequest request
    ) {
        return toResponse(useCase.registrar(toCommand(inventarioId, request)));
    }

    @GetMapping("/api/inventario/{inventarioId}/cuidados")
    public List<CuidadoPlantaResponse> listar(@PathVariable Long inventarioId) {
        return useCase.listarPorInventario(inventarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    private RegistrarCuidadoCommand toCommand(Long inventarioId, RegistrarCuidadoRequest request) {
        return RegistrarCuidadoCommand.builder()
                .inventarioId(inventarioId)
                .tipoCuidado(request.getTipoCuidado())
                .observacion(request.getObservacion())
                .build();
    }

    private CuidadoPlantaResponse toResponse(CuidadoPlanta cuidado) {
        return CuidadoPlantaResponse.builder()
                .id(cuidado.getId())
                .inventarioId(cuidado.getInventarioId())
                .tipoCuidado(cuidado.getTipoCuidado().name())
                .fecha(cuidado.getFecha())
                .observacion(cuidado.getObservacion())
                .proximaFechaSugerida(cuidado.getProximaFechaSugerida())
                .build();
    }
}
