package com.Smartplants.application.service;

import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.domain.model.PreferenciasUsuario;
import com.Smartplants.domain.model.ResultadoSugerencia;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SugerenciaApplicationServiceTest {

    @Test
    void recomiendaLaPlantaConMayorPuntaje() {
        InMemoryPlantaRepository repository = new InMemoryPlantaRepository();
        repository.save(planta(1L, "Cactus", "BAJO", 3, 1, 3, 1, 1));
        repository.save(planta(2L, "Rosa", "MEDIO", 2, 2, 2, 2, 2));

        SugerenciaApplicationService service = new SugerenciaApplicationService(repository);

        ResultadoSugerencia resultado = service.sugerir(preferencias("BAJO", 3, 1, 3, 1, 1));

        assertEquals(1L, resultado.getPlanta().getId());
        assertEquals("Cactus", resultado.getPlanta().getNombre());
        assertEquals(5, resultado.getPuntaje());
    }

    @Test
    void manejaEmpateDeFormaDeterminista() {
        InMemoryPlantaRepository repository = new InMemoryPlantaRepository();
        repository.save(planta(1L, "Cactus", "BAJO", 3, 1, 3, 1, 1));
        repository.save(planta(2L, "Suculenta", "BAJO", 3, 1, 3, 1, 1));

        SugerenciaApplicationService service = new SugerenciaApplicationService(repository);

        ResultadoSugerencia resultado = service.sugerir(preferencias("BAJO", 3, 1, 3, 1, 1));

        assertEquals(1L, resultado.getPlanta().getId());
        assertEquals("Cactus", resultado.getPlanta().getNombre());
        assertEquals(5, resultado.getPuntaje());
    }

    @Test
    void rechazaDatosInvalidos() {
        SugerenciaApplicationService service = new SugerenciaApplicationService(new InMemoryPlantaRepository());

        ValidationException exception = assertThrows(ValidationException.class,
                () -> service.sugerir(preferencias(" ", 3, 1, 3, 1, 1)));

        assertEquals("El mantenimiento es obligatorio", exception.getMessage());
    }

    @Test
    void fallaSiNoHayPlantasDisponibles() {
        SugerenciaApplicationService service = new SugerenciaApplicationService(new InMemoryPlantaRepository());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.sugerir(preferencias("BAJO", 3, 1, 3, 1, 1)));

        assertEquals("No hay plantas disponibles para sugerencias", exception.getMessage());
    }

    private PreferenciasUsuario preferencias(String mantenimiento, int luz, int riego, int temperatura, int tamano, int ambiente) {
        return PreferenciasUsuario.builder()
                .mantenimiento(mantenimiento)
                .luz(luz)
                .riego(riego)
                .temperatura(temperatura)
                .tamano(tamano)
                .ambiente(ambiente)
                .build();
    }

    private Planta planta(Long id, String nombre, String mantenimiento, int luz, int riego, int temperatura, int tamano, int ambiente) {
        return Planta.builder()
                .id(id)
                .nombre(nombre)
                .descripcion("Descripcion de " + nombre)
                .luz(luz)
                .riego(riego)
                .temperatura(temperatura)
                .tamano(tamano)
                .ambiente(ambiente)
                .mantenimiento(Mantenimiento.builder().id(id).nivel(mantenimiento).build())
                .build();
    }

    private static final class InMemoryPlantaRepository implements PlantaRepositoryPort {
        private final Map<Long, Planta> storage = new HashMap<>();

        @Override
        public List<Planta> findAll() {
            return storage.values().stream()
                    .sorted((left, right) -> left.getId().compareTo(right.getId()))
                    .toList();
        }

        @Override
        public Optional<Planta> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Planta save(Planta planta) {
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
}
