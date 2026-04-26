package com.Smartplants.infrastructure.adapter.in.web.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
