package com.Smartplants.infrastructure.adapter.in.web.request;

import lombok.Data;

@Data
public class RegistrarCuidadoRequest {
    private String tipoCuidado;
    private String observacion;
}
