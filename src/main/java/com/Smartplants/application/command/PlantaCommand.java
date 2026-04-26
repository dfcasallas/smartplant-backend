package com.Smartplants.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantaCommand {
    private String nombre;
    private String descripcion;
    private Integer luz;
    private Integer riego;
    private Integer temperatura;
    private Integer tamano;
    private Integer ambiente;
    private Long tipoId;
    private Long familiaId;
    private Long mantenimientoId;
    private Long saludId;
}
