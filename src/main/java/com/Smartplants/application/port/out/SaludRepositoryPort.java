package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.Salud;

import java.util.List;
import java.util.Optional;

public interface SaludRepositoryPort {
    List<Salud> findAll();
    Optional<Salud> findById(Long id);
    Salud save(Salud salud);
    boolean existsById(Long id);
    void deleteById(Long id);
}
