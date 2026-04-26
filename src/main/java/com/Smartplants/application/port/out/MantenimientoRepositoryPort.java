package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.Mantenimiento;

import java.util.List;
import java.util.Optional;

public interface MantenimientoRepositoryPort {
    List<Mantenimiento> findAll();
    Optional<Mantenimiento> findById(Long id);
    Mantenimiento save(Mantenimiento mantenimiento);
    boolean existsById(Long id);
    void deleteById(Long id);
}
