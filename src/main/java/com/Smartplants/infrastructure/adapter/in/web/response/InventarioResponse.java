package com.Smartplants.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class InventarioResponse {
    Long id;
    Long usuarioId;
    Long plantaId;
    String nombrePlanta;
    String nombrePersonalizado;
    LocalDate fechaAgregado;
}
