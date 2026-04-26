package com.Smartplants.infrastructure.adapter.in.web.request;

import lombok.Data;

@Data
public class SugerenciaRequest {
    private String mantenimiento;
    private Integer luz;
    private Integer riego;
    private Integer temperatura;
    private Integer tamano;
    private Integer ambiente;
}
