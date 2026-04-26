package com.Smartplants.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CatalogoResponse {
    Long id;
    String valor;
}
