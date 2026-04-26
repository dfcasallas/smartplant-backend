package com.Smartplants.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuidadoPlanta {
    private Long id;
    private Long inventarioId;
    private TipoCuidado tipoCuidado;
    private LocalDate fecha;
    private String observacion;
    private LocalDate proximaFechaSugerida;
}
