package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.FamiliaUseCase;
import com.Smartplants.domain.model.Familia;
import com.Smartplants.infrastructure.adapter.in.web.request.FamiliaRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CatalogoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/familias")
@RequiredArgsConstructor
public class FamiliaController {

    private final FamiliaUseCase useCase;

    @GetMapping
    public List<CatalogoResponse> listar() {
        return useCase.listar().stream()
                .map(familia -> toResponse(familia.getId(), familia.getNombre()))
                .toList();
    }

    @PostMapping
    public CatalogoResponse crear(@RequestBody FamiliaRequest request) {
        Familia familia = useCase.crear(CatalogCommand.builder().valor(request.getNombre()).build());
        return toResponse(familia.getId(), familia.getNombre());
    }

    @PutMapping("/{id}")
    public CatalogoResponse actualizar(@PathVariable Long id, @RequestBody FamiliaRequest request) {
        Familia familia = useCase.actualizar(id, CatalogCommand.builder().valor(request.getNombre()).build());
        return toResponse(familia.getId(), familia.getNombre());
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
