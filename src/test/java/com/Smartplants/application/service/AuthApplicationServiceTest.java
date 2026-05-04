package com.Smartplants.application.service;

import com.Smartplants.application.command.LoginCommand;
import com.Smartplants.application.command.RegistrarUsuarioCommand;
import com.Smartplants.application.port.out.PasswordHasherPort;
import com.Smartplants.application.port.out.UsuarioRepositoryPort;
import com.Smartplants.domain.exception.AuthenticationException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Rol;
import com.Smartplants.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthApplicationServiceTest {

    @Test
    void registraUsuarioCorrectamente() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        AuthApplicationService service = service(usuarioRepository);

        Usuario usuario = service.registrar(RegistrarUsuarioCommand.builder()
                .nombre("Daniel")
                .email("DANIEL@email.com")
                .password("123456")
                .rol("ADMIN")
                .build());

        assertNotNull(usuario.getId());
        assertEquals("Daniel", usuario.getNombre());
        assertEquals("daniel@email.com", usuario.getEmail());
        assertEquals("{hash}123456", usuario.getPassword());
        assertEquals(Rol.ADMIN, usuario.getRol());
        assertNull(usuario.getUltimaConexion());
    }

    @Test
    void noPermiteEmailDuplicado() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        AuthApplicationService service = service(usuarioRepository);

        service.registrar(RegistrarUsuarioCommand.builder()
                .nombre("Daniel")
                .email("daniel@email.com")
                .password("123456")
                .rol("USER")
                .build());

        ValidationException exception = assertThrows(ValidationException.class, () -> service.registrar(
                RegistrarUsuarioCommand.builder()
                        .nombre("Daniel 2")
                        .email("DANIEL@email.com")
                        .password("abcdef")
                        .rol("USER")
                        .build()
        ));

        assertEquals("El email ya esta registrado", exception.getMessage());
    }

    @Test
    void loginCorrectoActualizaUltimaConexion() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        AuthApplicationService service = service(usuarioRepository);

        Usuario registrado = service.registrar(RegistrarUsuarioCommand.builder()
                .nombre("Daniel")
                .email("daniel@email.com")
                .password("123456")
                .rol("USER")
                .build());

        Usuario login = service.login(LoginCommand.builder()
                .email("daniel@email.com")
                .password("123456")
                .build());

        assertEquals(registrado.getId(), login.getId());
        assertEquals("Daniel", login.getNombre());
        assertNotNull(login.getUltimaConexion());
        assertEquals("{hash}123456", login.getPassword());
    }

    @Test
    void loginConPasswordIncorrectaFalla() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        AuthApplicationService service = service(usuarioRepository);

        Usuario registrado = service.registrar(RegistrarUsuarioCommand.builder()
                .nombre("Daniel")
                .email("daniel@email.com")
                .password("123456")
                .rol("USER")
                .build());

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> service.login(
                LoginCommand.builder()
                        .email("daniel@email.com")
                        .password("bad-password")
                        .build()
        ));

        assertEquals("Credenciales invalidas", exception.getMessage());
        assertNull(registrado.getUltimaConexion());
    }

    @Test
    void asignaRolUserPorDefecto() {
        AuthApplicationService service = service(new InMemoryUsuarioRepository());

        Usuario usuario = service.registrar(RegistrarUsuarioCommand.builder()
                .nombre("Daniel")
                .email("daniel@email.com")
                .password("123456")
                .build());

        assertEquals(Rol.USER, usuario.getRol());
    }

    @Test
    void loginAceptaPasswordPlanoExistenteYLoMigraAHash() {
        InMemoryUsuarioRepository usuarioRepository = new InMemoryUsuarioRepository();
        usuarioRepository.save(Usuario.builder()
                .nombre("Legacy")
                .email("legacy@email.com")
                .password("123456")
                .rol(Rol.USER)
                .build());
        AuthApplicationService service = service(usuarioRepository);

        Usuario login = service.login(LoginCommand.builder()
                .email("legacy@email.com")
                .password("123456")
                .build());

        assertEquals("{hash}123456", login.getPassword());
        assertNotNull(login.getUltimaConexion());
    }

    private AuthApplicationService service(InMemoryUsuarioRepository usuarioRepository) {
        return new AuthApplicationService(usuarioRepository, new TestPasswordHasher());
    }

    private static final class TestPasswordHasher implements PasswordHasherPort {

        @Override
        public String encode(String rawPassword) {
            return "{hash}" + rawPassword;
        }

        @Override
        public boolean matches(String rawPassword, String storedPassword) {
            return encode(rawPassword).equals(storedPassword);
        }

        @Override
        public boolean isEncoded(String storedPassword) {
            return storedPassword != null && storedPassword.startsWith("{hash}");
        }
    }

    private static final class InMemoryUsuarioRepository implements UsuarioRepositoryPort {
        private final Map<Long, Usuario> storage = new HashMap<>();
        private long sequence = 1L;

        @Override
        public Usuario save(Usuario usuario) {
            if (usuario.getId() == null) {
                usuario.setId(sequence++);
            }
            storage.put(usuario.getId(), usuario);
            return usuario;
        }

        @Override
        public List<Usuario> findAll() {
            return storage.values().stream().toList();
        }

        @Override
        public Optional<Usuario> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Optional<Usuario> findByEmail(String email) {
            return storage.values().stream()
                    .filter(usuario -> usuario.getEmail().equals(email))
                    .findFirst();
        }

        @Override
        public boolean existsByEmail(String email) {
            return findByEmail(email).isPresent();
        }
    }
}
