package com.Smartplants.infrastructure.security;

import com.Smartplants.application.port.in.PlantaUseCase;
import com.Smartplants.application.port.out.UsuarioRepositoryPort;
import com.Smartplants.domain.model.Rol;
import com.Smartplants.domain.model.Usuario;
import com.Smartplants.infrastructure.adapter.in.web.PlantaController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlantaController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class, JwtService.class})
@ImportAutoConfiguration({
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        ServletWebSecurityAutoConfiguration.class
})
@TestPropertySource(properties = {
        "jwt.secret=clave_larga_segura_de_minimo_32_caracteres_test",
        "jwt.expiration-ms=86400000"
})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private PlantaUseCase plantaUseCase;

    @MockitoBean
    private UsuarioRepositoryPort usuarioRepository;

    @Test
    void endpointProtegidoSinTokenResponde401() throws Exception {
        mockMvc.perform(get("/api/plantas"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void endpointProtegidoConTokenValidoRespondeOk() throws Exception {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Daniel")
                .email("daniel@email.com")
                .rol(Rol.USER)
                .build();
        String token = jwtService.generateToken(usuario);

        when(usuarioRepository.findByEmail("daniel@email.com")).thenReturn(Optional.of(usuario));
        when(plantaUseCase.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/plantas").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
