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
public class InventarioPlanta {
    private Long id;
    private Long usuarioId;
    private Planta planta;
    private String nombrePersonalizado;
    private LocalDate fechaAgregado;
}
