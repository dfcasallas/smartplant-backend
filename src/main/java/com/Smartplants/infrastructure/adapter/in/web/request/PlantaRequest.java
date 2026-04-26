package com.Smartplants.infrastructure.adapter.in.web.request;

import lombok.Data;

@Data
public class PlantaRequest {
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
