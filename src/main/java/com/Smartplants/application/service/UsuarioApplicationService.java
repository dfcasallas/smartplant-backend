package com.Smartplants.application.service;

import com.Smartplants.application.port.in.UsuarioUseCase;
import com.Smartplants.application.port.out.UsuarioRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioApplicationService implements UsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        validarId(id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("El usuarioId es obligatorio");
        }
    }
}
