package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.CuidadoPlantaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataCuidadoPlantaRepository extends JpaRepository<CuidadoPlantaEntity, Long> {
    List<CuidadoPlantaEntity> findByInventarioId(Long inventarioId);
}
