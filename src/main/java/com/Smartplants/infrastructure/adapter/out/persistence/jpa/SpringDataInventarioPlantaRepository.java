package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.InventarioPlantaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataInventarioPlantaRepository extends JpaRepository<InventarioPlantaEntity, Long> {
    List<InventarioPlantaEntity> findByUsuarioId(Long usuarioId);
}
