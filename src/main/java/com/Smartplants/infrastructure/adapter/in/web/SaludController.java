package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.SaludUseCase;
import com.Smartplants.domain.model.Salud;
import com.Smartplants.infrastructure.adapter.in.web.request.SaludRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CatalogoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saludes")
@RequiredArgsConstructor
public class SaludController {

    private final SaludUseCase useCase;

    @GetMapping
    public List<CatalogoResponse> listar() {
        return useCase.listar().stream()
                .map(salud -> toResponse(salud.getId(), salud.getEstado()))
                .toList();
    }

    @PostMapping
    public CatalogoResponse crear(@RequestBody SaludRequest request) {
        Salud salud = useCase.crear(CatalogCommand.builder().valor(request.getEstado()).build());
        return toResponse(salud.getId(), salud.getEstado());
    }

    @PutMapping("/{id}")
    public CatalogoResponse actualizar(@PathVariable Long id, @RequestBody SaludRequest request) {
        Salud salud = useCase.actualizar(id, CatalogCommand.builder().valor(request.getEstado()).build());
        return toResponse(salud.getId(), salud.getEstado());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        useCase.eliminar(id);
    }

    private CatalogoResponse toResponse(Long id, String valor) {
        return CatalogoResponse.builder()
                .id(id)
                .valor(valor)
                .build();
    }
}
