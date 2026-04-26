package com.Smartplants.infrastructure.adapter.in.web.request;

import lombok.Data;

@Data
public class AgregarInventarioRequest {
    private Long plantaId;
    private String nombrePersonalizado;
}
