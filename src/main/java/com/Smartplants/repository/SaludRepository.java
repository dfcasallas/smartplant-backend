package com.Smartplants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Smartplants.model.Salud;

public interface SaludRepository extends JpaRepository<Salud, Long> {
}