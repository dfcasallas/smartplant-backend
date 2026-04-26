package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.Planta;

import java.util.List;
import java.util.Optional;

public interface PlantaRepositoryPort {
    List<Planta> findAll();
    Optional<Planta> findById(Long id);
    Planta save(Planta planta);
    boolean existsById(Long id);
    void deleteById(Long id);
}
