package com.Smartplants.infrastructure.adapter.in.web.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class LoginResponse {
    Long id;
    String nombre;
    String email;
    String rol;
    LocalDateTime ultimaConexion;
    String token;
}
