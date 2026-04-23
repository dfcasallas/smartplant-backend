package com.Smartplants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Smartplants.model.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
}