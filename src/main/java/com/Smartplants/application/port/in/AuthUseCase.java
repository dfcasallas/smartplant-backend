package com.Smartplants.application.port.in;

import com.Smartplants.application.command.LoginCommand;
import com.Smartplants.application.command.RegistrarUsuarioCommand;
import com.Smartplants.domain.model.Usuario;

public interface AuthUseCase {
    Usuario registrar(RegistrarUsuarioCommand command);
    Usuario login(LoginCommand command);
}
