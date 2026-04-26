package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.AgregarPlantaInventarioCommand;
import com.Smartplants.application.port.in.InventarioUseCase;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.infrastructure.adapter.in.web.request.AgregarInventarioRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.InventarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioUseCase useCase;

    @PostMapping("/api/usuarios/{usuarioId}/inventario")
    public InventarioResponse agregar(
            @PathVariable Long usuarioId,
            @RequestBody AgregarInventarioRequest request
    ) {
        return toResponse(useCase.agregar(toCommand(usuarioId, request)));
    }

    @GetMapping("/api/usuarios/{usuarioId}/inventario")
    public List<InventarioResponse> listarPorUsuario(@PathVariable Long usuarioId) {
        return useCase.listarPorUsuario(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/api/inventario/{inventarioId}")
    public InventarioResponse consultarPorId(@PathVariable Long inventarioId) {
        return toResponse(useCase.consultarPorId(inventarioId));
    }

    @DeleteMapping("/api/inventario/{inventarioId}")
    public void eliminar(@PathVariable Long inventarioId) {
        useCase.eliminar(inventarioId);
    }

    private AgregarPlantaInventarioCommand toCommand(Long usuarioId, AgregarInventarioRequest request) {
        return AgregarPlantaInventarioCommand.builder()
                .usuarioId(usuarioId)
                .plantaId(request.getPlantaId())
                .nombrePersonalizado(request.getNombrePersonalizado())
                .build();
    }

    private InventarioResponse toResponse(InventarioPlanta inventarioPlanta) {
        return InventarioResponse.builder()
                .id(inventarioPlanta.getId())
                .usuarioId(inventarioPlanta.getUsuarioId())
                .plantaId(inventarioPlanta.getPlanta().getId())
                .nombrePlanta(inventarioPlanta.getPlanta().getNombre())
                .nombrePersonalizado(inventarioPlanta.getNombrePersonalizado())
                .fechaAgregado(inventarioPlanta.getFechaAgregado())
                .build();
    }
}
