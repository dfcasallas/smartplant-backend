package com.Smartplants.infrastructure.adapter.out.persistence.jpa;

import com.Smartplants.infrastructure.adapter.out.persistence.entity.TipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTipoRepository extends JpaRepository<TipoEntity, Long> {
}
