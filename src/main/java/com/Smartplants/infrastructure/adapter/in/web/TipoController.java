package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.TipoUseCase;
import com.Smartplants.domain.model.Tipo;
import com.Smartplants.infrastructure.adapter.in.web.request.TipoRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CatalogoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@RequiredArgsConstructor
public class TipoController {

    private final TipoUseCase useCase;

    @GetMapping
    public List<CatalogoResponse> listar() {
        return useCase.listar().stream()
                .map(tipo -> toResponse(tipo.getId(), tipo.getNombre()))
                .toList();
    }

    @PostMapping
    public CatalogoResponse crear(@RequestBody TipoRequest request) {
        Tipo tipo = useCase.crear(CatalogCommand.builder().valor(request.getNombre()).build());
        return toResponse(tipo.getId(), tipo.getNombre());
    }

    @PutMapping("/{id}")
    public CatalogoResponse actualizar(@PathVariable Long id, @RequestBody TipoRequest request) {
        Tipo tipo = useCase.actualizar(id, CatalogCommand.builder().valor(request.getNombre()).build());
        return toResponse(tipo.getId(), tipo.getNombre());
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
