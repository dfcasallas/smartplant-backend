package com.Smartplants.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenciasUsuario {
    private String mantenimiento;
    private Integer luz;
    private Integer riego;
    private Integer temperatura;
    private Integer tamano;
    private Integer ambiente;
}
