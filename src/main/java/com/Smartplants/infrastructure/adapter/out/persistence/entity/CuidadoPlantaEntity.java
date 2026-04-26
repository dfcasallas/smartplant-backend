package com.Smartplants.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "cuidado_planta")
@Data
public class CuidadoPlantaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long inventarioId;

    @Enumerated(EnumType.STRING)
    private com.Smartplants.domain.model.TipoCuidado tipoCuidado;

    private LocalDate fecha;

    private String observacion;

    private LocalDate proximaFechaSugerida;
}
