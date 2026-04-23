package com.Smartplants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Smartplants.model.Planta;

public interface PlantaRepository extends JpaRepository<Planta, Long> {
}