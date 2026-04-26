package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.LoginCommand;
import com.Smartplants.application.command.RegistrarUsuarioCommand;
import com.Smartplants.application.port.in.AuthUseCase;
import com.Smartplants.domain.model.Usuario;
import com.Smartplants.infrastructure.adapter.in.web.request.LoginRequest;
import com.Smartplants.infrastructure.adapter.in.web.request.RegisterRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase useCase;

    @PostMapping("/register")
    public UsuarioResponse registrar(@RequestBody RegisterRequest request) {
        return toResponse(useCase.registrar(toCommand(request)));
    }

    @PostMapping("/login")
    public UsuarioResponse login(@RequestBody LoginRequest request) {
        return toResponse(useCase.login(toCommand(request)));
    }

    private RegistrarUsuarioCommand toCommand(RegisterRequest request) {
        return RegistrarUsuarioCommand.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(request.getPassword())
                .rol(request.getRol())
                .build();
    }

    private LoginCommand toCommand(LoginRequest request) {
        return LoginCommand.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
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
