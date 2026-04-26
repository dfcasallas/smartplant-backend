package com.Smartplants.application.port.in;

import com.Smartplants.domain.model.Usuario;

import java.util.List;

public interface UsuarioUseCase {
    List<Usuario> listar();
    Usuario obtenerPorId(Long id);
}
