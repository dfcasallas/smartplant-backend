package com.Smartplants.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SugerenciaResponse {
    Long plantaId;
    String nombre;
    String descripcion;
    String mantenimiento;
    int puntaje;
}
