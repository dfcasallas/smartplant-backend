package com.Smartplants.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    private Tipo tipo;

    @ManyToOne
    private Familia familia;

    @ManyToOne
    private Mantenimiento mantenimiento;

    @ManyToOne
    private Salud salud;
}