package com.Smartplants.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "planta")
@Data
public class PlantaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer luz;
    private Integer riego;
    private Integer temperatura;
    private Integer tamano;
    private Integer ambiente;

    @ManyToOne
    private TipoEntity tipo;

    @ManyToOne
    private FamiliaEntity familia;

    @ManyToOne
    private MantenimientoEntity mantenimiento;

    @ManyToOne
    private SaludEntity salud;
}
