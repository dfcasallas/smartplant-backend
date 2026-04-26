package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.Tipo;

import java.util.List;
import java.util.Optional;

public interface TipoRepositoryPort {
    List<Tipo> findAll();
    Optional<Tipo> findById(Long id);
    Tipo save(Tipo tipo);
    boolean existsById(Long id);
    void deleteById(Long id);
}
