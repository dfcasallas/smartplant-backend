package com.Smartplants.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Planta {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer luz;
    private Integer riego;
    private Integer temperatura;
    private Integer tamano;
    private Integer ambiente;
    private Tipo tipo;
    private Familia familia;
    private Mantenimiento mantenimiento;
    private Salud salud;
}
