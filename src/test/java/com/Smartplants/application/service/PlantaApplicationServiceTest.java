package com.Smartplants.application.service;

import com.Smartplants.application.command.PlantaCommand;
import com.Smartplants.application.port.out.FamiliaRepositoryPort;
import com.Smartplants.application.port.out.MantenimientoRepositoryPort;
import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.application.port.out.SaludRepositoryPort;
import com.Smartplants.application.port.out.TipoRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Familia;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.domain.model.Salud;
import com.Smartplants.domain.model.Tipo;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlantaApplicationServiceTest {

    @Test
    void crearDebeResolverCatalogosYNormalizarNombre() {
        InMemoryPlantaRepository plantaRepository = new InMemoryPlantaRepository();
        InMemoryTipoRepository tipoRepository = new InMemoryTipoRepository();
        InMemoryFamiliaRepository familiaRepository = new InMemoryFamiliaRepository();
        InMemoryMantenimientoRepository mantenimientoRepository = new InMemoryMantenimientoRepository();
        InMemorySaludRepository saludRepository = new InMemorySaludRepository();

        tipoRepository.save(Tipo.builder().id(1L).nombre("Interior").build());
        familiaRepository.save(Familia.builder().id(2L).nombre("Tropical").build());
        mantenimientoRepository.save(Mantenimiento.builder().id(3L).nivel("Medio").build());
        saludRepository.save(Salud.builder().id(4L).estado("Sana").build());

        PlantaApplicationService service = new PlantaApplicationService(
                plantaRepository,
                tipoRepository,
                familiaRepository,
                mantenimientoRepository,
                saludRepository
        );

        Planta creada = service.crear(PlantaCommand.builder()
                .nombre("  Monstera  ")
                .tipoId(1L)
                .familiaId(2L)
                .mantenimientoId(3L)
                .saludId(4L)
                .build());

        assertEquals(1L, creada.getId());
        assertEquals("Monstera", creada.getNombre());
        assertEquals("Interior", creada.getTipo().getNombre());
        assertEquals("Tropical", creada.getFamilia().getNombre());
        assertEquals("Medio", creada.getMantenimiento().getNivel());
        assertEquals("Sana", creada.getSalud().getEstado());
    }

    @Test
    void crearDebeFallarSiFaltaElTipo() {
        PlantaApplicationService service = new PlantaApplicationService(
                new InMemoryPlantaRepository(),
                new InMemoryTipoRepository(),
                new InMemoryFamiliaRepository(),
                new InMemoryMantenimientoRepository(),
                new InMemorySaludRepository()
        );

        ValidationException exception = assertThrows(ValidationException.class, () -> service.crear(
                PlantaCommand.builder()
                        .nombre("Monstera")
                        .familiaId(2L)
                        .mantenimientoId(3L)
                        .saludId(4L)
                        .build()
        ));

        assertEquals("El tipo es obligatorio", exception.getMessage());
    }

    @Test
    void actualizarDebeFallarSiLaPlantaNoExiste() {
        PlantaApplicationService service = new PlantaApplicationService(
                new InMemoryPlantaRepository(),
                new InMemoryTipoRepository(),
                new InMemoryFamiliaRepository(),
                new InMemoryMantenimientoRepository(),
                new InMemorySaludRepository()
        );

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.actualizar(
                99L,
                PlantaCommand.builder()
                        .nombre("Monstera")
                        .tipoId(1L)
                        .familiaId(2L)
                        .mantenimientoId(3L)
                        .saludId(4L)
                        .build()
        ));

        assertEquals("Planta no encontrada", exception.getMessage());
    }

    private static final class InMemoryPlantaRepository implements PlantaRepositoryPort {
        private final Map<Long, Planta> storage = new HashMap<>();
        private long sequence = 1L;

        @Override
        public List<Planta> findAll() {
            return storage.values().stream().toList();
        }

        @Override
        public Optional<Planta> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Planta save(Planta planta) {
            if (planta.getId() == null) {
                planta.setId(sequence++);
            }
            storage.put(planta.getId(), planta);
            return planta;
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

    private static final class InMemoryTipoRepository implements TipoRepositoryPort {
        private final Map<Long, Tipo> storage = new HashMap<>();

        @Override
        public List<Tipo> findAll() {
            return storage.values().stream().toList();
        }

        @Override
        public Optional<Tipo> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Tipo save(Tipo tipo) {
            storage.put(tipo.getId(), tipo);
            return tipo;
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

    private static final class InMemoryFamiliaRepository implements FamiliaRepositoryPort {
        private final Map<Long, Familia> storage = new HashMap<>();

        @Override
        public List<Familia> findAll() {
            return storage.values().stream().toList();
        }

        @Override
        public Optional<Familia> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Familia save(Familia familia) {
            storage.put(familia.getId(), familia);
            return familia;
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

    private static final class InMemoryMantenimientoRepository implements MantenimientoRepositoryPort {
        private final Map<Long, Mantenimiento> storage = new HashMap<>();

        @Override
        public List<Mantenimiento> findAll() {
            return storage.values().stream().toList();
        }

        @Override
        public Optional<Mantenimiento> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Mantenimiento save(Mantenimiento mantenimiento) {
            storage.put(mantenimiento.getId(), mantenimiento);
            return mantenimiento;
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

    private static final class InMemorySaludRepository implements SaludRepositoryPort {
        private final Map<Long, Salud> storage = new HashMap<>();

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
