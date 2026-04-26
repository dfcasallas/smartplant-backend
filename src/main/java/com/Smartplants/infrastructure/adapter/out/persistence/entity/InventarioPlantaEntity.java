package com.Smartplants.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "inventario_planta")
@Data
public class InventarioPlantaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "planta_id", nullable = false)
    private PlantaEntity planta;

    private String nombrePersonalizado;

    private LocalDate fechaAgregado;
}
