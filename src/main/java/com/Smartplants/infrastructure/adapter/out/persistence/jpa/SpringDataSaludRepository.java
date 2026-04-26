package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.SaludEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSaludRepository extends JpaRepository<SaludEntity, Long> {
}
