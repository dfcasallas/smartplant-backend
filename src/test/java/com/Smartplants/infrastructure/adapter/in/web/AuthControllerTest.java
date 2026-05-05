package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.LoginCommand;
import com.Smartplants.application.command.RegistrarUsuarioCommand;
import com.Smartplants.application.port.in.AuthUseCase;
import com.Smartplants.domain.model.Rol;
import com.Smartplants.domain.model.Usuario;
import com.Smartplants.infrastructure.adapter.in.web.request.LoginRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.LoginResponse;
import com.Smartplants.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthControllerTest {

    @Test
    void loginDevuelveTokenJwt() {
        JwtService jwtService = new JwtService("clave_larga_segura_de_minimo_32_caracteres_test", 86400000);
        AuthController controller = new AuthController(new StubAuthUseCase(), jwtService);
        LoginRequest request = new LoginRequest();
        request.setEmail("daniel@email.com");
        request.setPassword("123456");

        LoginResponse response = controller.login(request);

        assertEquals(1L, response.getId());
        assertEquals("Daniel", response.getNombre());
        assertEquals("daniel@email.com", response.getEmail());
        assertEquals("USER", response.getRol());
        assertNotNull(response.getToken());
        assertEquals("daniel@email.com", jwtService.extractSubject(response.getToken()));
    }

    private static final class StubAuthUseCase implements AuthUseCase {

        @Override
        public Usuario registrar(RegistrarUsuarioCommand command) {
            throw new UnsupportedOperationException("No usado en esta prueba");
        }

        @Override
        public Usuario login(LoginCommand command) {
            return Usuario.builder()
                    .id(1L)
                    .nombre("Daniel")
                    .email(command.getEmail())
                    .rol(Rol.USER)
                    .ultimaConexion(LocalDateTime.now())
                    .build();
        }
    }
}
