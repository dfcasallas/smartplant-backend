package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.port.in.UsuarioUseCase;
import com.Smartplants.domain.model.Usuario;
import com.Smartplants.infrastructure.adapter.in.web.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase useCase;

    @GetMapping
    public List<UsuarioResponse> listar() {
        return useCase.listar().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse obtenerPorId(@PathVariable Long id) {
        return toResponse(useCase.obtenerPorId(id));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .ultimaConexion(usuario.getUltimaConexion())
                .build();
    }
}
