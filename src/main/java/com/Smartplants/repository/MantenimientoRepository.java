package com.Smartplants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Smartplants.model.Mantenimiento;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
}