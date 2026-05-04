package com.Smartplants.application.port.out;

public interface PasswordHasherPort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String storedPassword);
    boolean isEncoded(String storedPassword);
}
