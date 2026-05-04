package com.Smartplants.application.service;

import com.Smartplants.application.command.LoginCommand;
import com.Smartplants.application.command.RegistrarUsuarioCommand;
import com.Smartplants.application.port.in.AuthUseCase;
import com.Smartplants.application.port.out.PasswordHasherPort;
import com.Smartplants.application.port.out.UsuarioRepositoryPort;
import com.Smartplants.domain.exception.AuthenticationException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Rol;
import com.Smartplants.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthApplicationService implements AuthUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;

    @Override
    public Usuario registrar(RegistrarUsuarioCommand command) {
        validarRegistro(command);

        String email = normalizarEmail(command.getEmail());
        if (usuarioRepository.existsByEmail(email)) {
            throw new ValidationException("El email ya esta registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(command.getNombre().trim())
                .email(email)
                .password(passwordHasher.encode(command.getPassword()))
                .rol(parseRol(command.getRol()))
                .build();

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario login(LoginCommand command) {
        validarLogin(command);

        String email = normalizarEmail(command.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Credenciales invalidas"));

        if (!passwordMatches(command.getPassword(), usuario.getPassword())) {
            throw new AuthenticationException("Credenciales invalidas");
        }

        usuario.setUltimaConexion(LocalDateTime.now());
        if (!passwordHasher.isEncoded(usuario.getPassword())) {
            usuario.setPassword(passwordHasher.encode(command.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    private boolean passwordMatches(String rawPassword, String storedPassword) {
        if (passwordHasher.isEncoded(storedPassword)) {
            return passwordHasher.matches(rawPassword, storedPassword);
        }

        return storedPassword != null && storedPassword.equals(rawPassword);
    }

    private void validarRegistro(RegistrarUsuarioCommand command) {
        if (command == null) {
            throw new ValidationException("La solicitud de registro es obligatoria");
        }
        validarTexto(command.getNombre(), "El nombre es obligatorio");
        validarTexto(command.getEmail(), "El email es obligatorio");
        validarTexto(command.getPassword(), "El password es obligatorio");
    }

    private void validarLogin(LoginCommand command) {
        if (command == null) {
            throw new ValidationException("La solicitud de login es obligatoria");
        }
        validarTexto(command.getEmail(), "El email es obligatorio");
        validarTexto(command.getPassword(), "El password es obligatorio");
    }

    private void validarTexto(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensajeError);
        }
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private Rol parseRol(String valor) {
        if (valor == null || valor.isBlank()) {
            return Rol.USER;
        }
        try {
            return Rol.valueOf(valor.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new ValidationException("El rol no es valido");
        }
    }
}
