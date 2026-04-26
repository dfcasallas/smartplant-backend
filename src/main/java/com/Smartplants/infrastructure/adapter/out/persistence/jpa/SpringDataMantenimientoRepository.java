package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.MantenimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMantenimientoRepository extends JpaRepository<MantenimientoEntity, Long> {
}
