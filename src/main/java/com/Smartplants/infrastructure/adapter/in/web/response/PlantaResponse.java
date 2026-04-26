package com.Smartplants.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlantaResponse {
    Long id;
    String nombre;
    String descripcion;
    CatalogoResponse tipo;
    CatalogoResponse familia;
    CatalogoResponse mantenimiento;
    CatalogoResponse salud;
}
