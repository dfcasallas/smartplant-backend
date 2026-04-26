package com.Smartplants.infrastructure.adapter.in.web.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private String rol;
}
