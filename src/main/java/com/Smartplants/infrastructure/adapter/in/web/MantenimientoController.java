package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.MantenimientoUseCase;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.infrastructure.adapter.in.web.request.MantenimientoRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CatalogoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimientos")
@RequiredArgsConstructor
public class MantenimientoController {

    private final MantenimientoUseCase useCase;

    @GetMapping
    public List<CatalogoResponse> listar() {
        return useCase.listar().stream()
                .map(mantenimiento -> toResponse(mantenimiento.getId(), mantenimiento.getNivel()))
                .toList();
    }

    @PostMapping
    public CatalogoResponse crear(@RequestBody MantenimientoRequest request) {
        Mantenimiento mantenimiento = useCase.crear(CatalogCommand.builder().valor(request.getNivel()).build());
        return toResponse(mantenimiento.getId(), mantenimiento.getNivel());
    }

    @PutMapping("/{id}")
    public CatalogoResponse actualizar(@PathVariable Long id, @RequestBody MantenimientoRequest request) {
        Mantenimiento mantenimiento = useCase.actualizar(id, CatalogCommand.builder().valor(request.getNivel()).build());
        return toResponse(mantenimiento.getId(), mantenimiento.getNivel());
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
