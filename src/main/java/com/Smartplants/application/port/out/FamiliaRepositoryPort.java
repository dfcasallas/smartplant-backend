package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.Familia;

import java.util.List;
import java.util.Optional;

public interface FamiliaRepositoryPort {
    List<Familia> findAll();
    Optional<Familia> findById(Long id);
    Familia save(Familia familia);
    boolean existsById(Long id);
    void deleteById(Long id);
}
