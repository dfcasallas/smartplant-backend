package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.PlantaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPlantaRepository extends JpaRepository<PlantaEntity, Long> {
}
