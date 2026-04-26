package com.Smartplants.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CuidadoPlantaResponse {
    Long id;
    Long inventarioId;
    String tipoCuidado;
    LocalDate fecha;
    String observacion;
    LocalDate proximaFechaSugerida;
}
