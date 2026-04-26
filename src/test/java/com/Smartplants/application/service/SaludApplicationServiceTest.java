package com.Smartplants.application.service;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.out.SaludRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Salud;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SaludApplicationServiceTest {

    @Test
    void crearDebeNormalizarElEstado() {
        InMemorySaludRepository repository = new InMemorySaludRepository();
        SaludApplicationService service = new SaludApplicationService(repository);

        Salud salud = service.crear(CatalogCommand.builder().valor("  En observacion  ").build());

        assertEquals(1L, salud.getId());
        assertEquals("En observacion", salud.getEstado());
    }

    @Test
    void crearDebeFallarSiElEstadoEstaVacio() {
        SaludApplicationService service = new SaludApplicationService(new InMemorySaludRepository());

        ValidationException exception = assertThrows(ValidationException.class,
                () -> service.crear(CatalogCommand.builder().valor("   ").build()));

        assertEquals("El estado de salud es obligatorio", exception.getMessage());
    }

    @Test
    void actualizarDebeFallarSiNoExisteLaSalud() {
        SaludApplicationService service = new SaludApplicationService(new InMemorySaludRepository());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.actualizar(50L, CatalogCommand.builder().valor("Sana").build()));

        assertEquals("Estado de salud no encontrado", exception.getMessage());
    }

    private static final class InMemorySaludRepository implements SaludRepositoryPort {
        private final Map<Long, Salud> storage = new HashMap<>();
        private long sequence = 1L;

        @Override
        public List<Salud> findAll() {
            return storage.values().stream().toList();
        }

        @Override
        public Optional<Salud> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Salud save(Salud salud) {
            if (salud.getId() == null) {
                salud.setId(sequence++);
            }
            storage.put(salud.getId(), salud);
            return salud;
        }

        @Override
        public boolean existsById(Long id) {
            return storage.containsKey(id);
        }

        @Override
        public void deleteById(Long id) {
            storage.remove(id);
        }
    }
}
