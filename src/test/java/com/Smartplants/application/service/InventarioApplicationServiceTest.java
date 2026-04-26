package com.Smartplants.application.service;

import com.Smartplants.application.command.AgregarPlantaInventarioCommand;
import com.Smartplants.application.port.out.InventarioRepositoryPort;
import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.domain.model.Planta;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventarioApplicationServiceTest {

    @Test
    void agregaPlantaAlInventarioCorrectamente() {
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        InMemoryPlantaRepository plantaRepository = new InMemoryPlantaRepository();
        plantaRepository.save(Planta.builder().id(1L).nombre("Cactus").build());

        InventarioApplicationService service = new InventarioApplicationService(inventarioRepository, plantaRepository);

        InventarioPlanta inventario = service.agregar(AgregarPlantaInventarioCommand.builder()
                .usuarioId(1L)
                .plantaId(1L)
                .nombrePersonalizado("Mi cactus de la ventana")
                .build());

        assertNotNull(inventario.getId());
        assertEquals(1L, inventario.getUsuarioId());
        assertEquals(1L, inventario.getPlanta().getId());
        assertEquals("Cactus", inventario.getPlanta().getNombre());
        assertEquals("Mi cactus de la ventana", inventario.getNombrePersonalizado());
        assertEquals(LocalDate.now(), inventario.getFechaAgregado());
    }

    @Test
    void listaInventarioPorUsuario() {
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        InMemoryPlantaRepository plantaRepository = new InMemoryPlantaRepository();
        InventarioApplicationService service = new InventarioApplicationService(inventarioRepository, plantaRepository);

        inventarioRepository.save(inventario(1L, 10L, 1L, "Cactus", "Ventana"));
        inventarioRepository.save(inventario(2L, 10L, 2L, "Rosa", "Patio"));
        inventarioRepository.save(inventario(3L, 20L, 3L, "Palma", "Sala"));

        List<InventarioPlanta> inventarioUsuario = service.listarPorUsuario(10L);

        assertEquals(2, inventarioUsuario.size());
        assertEquals(10L, inventarioUsuario.get(0).getUsuarioId());
        assertEquals(10L, inventarioUsuario.get(1).getUsuarioId());
    }

    @Test
    void consultaInventarioPorId() {
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        InMemoryPlantaRepository plantaRepository = new InMemoryPlantaRepository();
        InventarioApplicationService service = new InventarioApplicationService(inventarioRepository, plantaRepository);

        inventarioRepository.save(inventario(5L, 1L, 1L, "Cactus", "Mi cactus"));

        InventarioPlanta inventario = service.consultarPorId(5L);

        assertEquals(5L, inventario.getId());
        assertEquals("Mi cactus", inventario.getNombrePersonalizado());
    }

    @Test
    void eliminaInventarioPorId() {
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        InMemoryPlantaRepository plantaRepository = new InMemoryPlantaRepository();
        InventarioApplicationService service = new InventarioApplicationService(inventarioRepository, plantaRepository);

        inventarioRepository.save(inventario(7L, 1L, 1L, "Cactus", "Mi cactus"));

        service.eliminar(7L);

        assertEquals(false, inventarioRepository.existsById(7L));
    }

    @Test
    void fallaSiPlantaNoExiste() {
        InventarioApplicationService service = new InventarioApplicationService(
                new InMemoryInventarioRepository(),
                new InMemoryPlantaRepository()
        );

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.agregar(
                AgregarPlantaInventarioCommand.builder()
                        .usuarioId(1L)
                        .plantaId(99L)
                        .nombrePersonalizado("Mi cactus")
                        .build()
        ));

        assertEquals("Planta no encontrada", exception.getMessage());
    }

    @Test
    void fallaSiNombrePersonalizadoEstaVacio() {
        InMemoryPlantaRepository plantaRepository = new InMemoryPlantaRepository();
        plantaRepository.save(Planta.builder().id(1L).nombre("Cactus").build());

        InventarioApplicationService service = new InventarioApplicationService(
                new InMemoryInventarioRepository(),
                plantaRepository
        );

        ValidationException exception = assertThrows(ValidationException.class, () -> service.agregar(
                AgregarPlantaInventarioCommand.builder()
                        .usuarioId(1L)
                        .plantaId(1L)
                        .nombrePersonalizado("   ")
                        .build()
        ));

        assertEquals("El nombrePersonalizado es obligatorio", exception.getMessage());
    }

    private InventarioPlanta inventario(Long id, Long usuarioId, Long plantaId, String nombrePlanta, String nombrePersonalizado) {
        return InventarioPlanta.builder()
                .id(id)
                .usuarioId(usuarioId)
                .planta(Planta.builder().id(plantaId).nombre(nombrePlanta).build())
                .nombrePersonalizado(nombrePersonalizado)
                .fechaAgregado(LocalDate.now())
                .build();
    }

    private static final class InMemoryInventarioRepository implements InventarioRepositoryPort {
        private final Map<Long, InventarioPlanta> storage = new HashMap<>();
        private long sequence = 1L;

        @Override
        public InventarioPlanta save(InventarioPlanta inventarioPlanta) {
            if (inventarioPlanta.getId() == null) {
                inventarioPlanta.setId(sequence++);
            }
            storage.put(inventarioPlanta.getId(), inventarioPlanta);
            return inventarioPlanta;
        }

        @Override
        public List<InventarioPlanta> findByUsuarioId(Long usuarioId) {
            return storage.values().stream()
                    .filter(item -> item.getUsuarioId().equals(usuarioId))
                    .sorted((left, right) -> left.getId().compareTo(right.getId()))
                    .toList();
        }

        @Override
        public Optional<InventarioPlanta> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
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

    private static final class InMemoryPlantaRepository implements PlantaRepositoryPort {
        private final Map<Long, Planta> storage = new HashMap<>();

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
