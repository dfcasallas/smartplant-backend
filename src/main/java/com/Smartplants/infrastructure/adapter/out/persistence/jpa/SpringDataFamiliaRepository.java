package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.FamiliaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataFamiliaRepository extends JpaRepository<FamiliaEntity, Long> {
}
