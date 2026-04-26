package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.InventarioPlanta;

import java.util.List;
import java.util.Optional;

public interface InventarioRepositoryPort {
    InventarioPlanta save(InventarioPlanta inventarioPlanta);
    List<InventarioPlanta> findByUsuarioId(Long usuarioId);
    Optional<InventarioPlanta> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
}
