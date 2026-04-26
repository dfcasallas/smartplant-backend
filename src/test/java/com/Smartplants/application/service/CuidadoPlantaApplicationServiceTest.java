package com.Smartplants.application.service;

import com.Smartplants.application.command.RegistrarCuidadoCommand;
import com.Smartplants.application.port.out.CuidadoPlantaRepositoryPort;
import com.Smartplants.application.port.out.InventarioRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.CuidadoPlanta;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.domain.model.TipoCuidado;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CuidadoPlantaApplicationServiceTest {

    @Test
    void registraRiegoParaMantenimientoBajoYCalculaMasOchoDias() {
        CuidadoPlanta cuidado = registrarConNivel("BAJO", "RIEGO");
        assertEquals(LocalDate.now().plusDays(8), cuidado.getProximaFechaSugerida());
    }

    @Test
    void registraRiegoParaMantenimientoMedioYCalculaMasCincoDias() {
        CuidadoPlanta cuidado = registrarConNivel("MEDIO", "RIEGO");
        assertEquals(LocalDate.now().plusDays(5), cuidado.getProximaFechaSugerida());
    }

    @Test
    void registraRiegoParaMantenimientoAltoYCalculaMasTresDias() {
        CuidadoPlanta cuidado = registrarConNivel("ALTO", "RIEGO");
        assertEquals(LocalDate.now().plusDays(3), cuidado.getProximaFechaSugerida());
    }

    @Test
    void registraAbonoYCalculaMasTreintaDias() {
        CuidadoPlanta cuidado = registrarConNivel("BAJO", "ABONO");
        assertEquals(LocalDate.now().plusDays(30), cuidado.getProximaFechaSugerida());
    }

    @Test
    void registraPodaYCalculaMasCuarentaYCincoDias() {
        CuidadoPlanta cuidado = registrarConNivel("BAJO", "PODA");
        assertEquals(LocalDate.now().plusDays(45), cuidado.getProximaFechaSugerida());
    }

    @Test
    void fallaSiInventarioNoExiste() {
        CuidadoPlantaApplicationService service = new CuidadoPlantaApplicationService(
                new InMemoryCuidadoRepository(),
                new InMemoryInventarioRepository()
        );

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.registrar(
                RegistrarCuidadoCommand.builder()
                        .inventarioId(99L)
                        .tipoCuidado("RIEGO")
                        .observacion("Regado")
                        .build()
        ));

        assertEquals("Inventario no encontrado", exception.getMessage());
    }

    @Test
    void fallaSiTipoCuidadoEsNuloOInvalido() {
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        inventarioRepository.save(inventario(1L, "BAJO"));

        CuidadoPlantaApplicationService service = new CuidadoPlantaApplicationService(
                new InMemoryCuidadoRepository(),
                inventarioRepository
        );

        ValidationException nulo = assertThrows(ValidationException.class, () -> service.registrar(
                RegistrarCuidadoCommand.builder()
                        .inventarioId(1L)
                        .tipoCuidado(null)
                        .build()
        ));
        assertEquals("El tipoCuidado es obligatorio", nulo.getMessage());

        ValidationException invalido = assertThrows(ValidationException.class, () -> service.registrar(
                RegistrarCuidadoCommand.builder()
                        .inventarioId(1L)
                        .tipoCuidado("INVALIDO")
                        .build()
        ));
        assertEquals("El tipoCuidado no es valido", invalido.getMessage());
    }

    @Test
    void listaCuidadosPorInventario() {
        InMemoryCuidadoRepository cuidadoRepository = new InMemoryCuidadoRepository();
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        inventarioRepository.save(inventario(1L, "BAJO"));

        cuidadoRepository.save(cuidado(1L, 1L, TipoCuidado.RIEGO));
        cuidadoRepository.save(cuidado(2L, 1L, TipoCuidado.ABONO));
        cuidadoRepository.save(cuidado(3L, 2L, TipoCuidado.PODA));

        CuidadoPlantaApplicationService service = new CuidadoPlantaApplicationService(cuidadoRepository, inventarioRepository);

        List<CuidadoPlanta> cuidados = service.listarPorInventario(1L);

        assertEquals(2, cuidados.size());
        assertEquals(1L, cuidados.get(0).getInventarioId());
        assertEquals(1L, cuidados.get(1).getInventarioId());
    }

    private CuidadoPlanta registrarConNivel(String nivel, String tipoCuidado) {
        InMemoryInventarioRepository inventarioRepository = new InMemoryInventarioRepository();
        inventarioRepository.save(inventario(1L, nivel));

        CuidadoPlantaApplicationService service = new CuidadoPlantaApplicationService(
                new InMemoryCuidadoRepository(),
                inventarioRepository
        );

        return service.registrar(RegistrarCuidadoCommand.builder()
                .inventarioId(1L)
                .tipoCuidado(tipoCuidado)
                .observacion("Cuidado realizado")
                .build());
    }

    private InventarioPlanta inventario(Long id, String nivelMantenimiento) {
        return InventarioPlanta.builder()
                .id(id)
                .usuarioId(1L)
                .planta(Planta.builder()
                        .id(1L)
                        .nombre("Cactus")
                        .mantenimiento(Mantenimiento.builder().id(1L).nivel(nivelMantenimiento).build())
                        .build())
                .nombrePersonalizado("Mi cactus")
                .fechaAgregado(LocalDate.now())
                .build();
    }

    private CuidadoPlanta cuidado(Long id, Long inventarioId, TipoCuidado tipo) {
        return CuidadoPlanta.builder()
                .id(id)
                .inventarioId(inventarioId)
                .tipoCuidado(tipo)
                .fecha(LocalDate.now())
                .observacion("Obs")
                .proximaFechaSugerida(LocalDate.now().plusDays(1))
                .build();
    }

    private static final class InMemoryCuidadoRepository implements CuidadoPlantaRepositoryPort {
        private final Map<Long, CuidadoPlanta> storage = new HashMap<>();
        private long sequence = 1L;

        @Override
        public CuidadoPlanta save(CuidadoPlanta cuidadoPlanta) {
            if (cuidadoPlanta.getId() == null) {
                cuidadoPlanta.setId(sequence++);
            }
            storage.put(cuidadoPlanta.getId(), cuidadoPlanta);
            return cuidadoPlanta;
        }

        @Override
        public List<CuidadoPlanta> findByInventarioId(Long inventarioId) {
            return storage.values().stream()
                    .filter(item -> item.getInventarioId().equals(inventarioId))
                    .sorted((left, right) -> left.getId().compareTo(right.getId()))
                    .toList();
        }
    }

    private static final class InMemoryInventarioRepository implements InventarioRepositoryPort {
        private final Map<Long, InventarioPlanta> storage = new HashMap<>();

        @Override
        public InventarioPlanta save(InventarioPlanta inventarioPlanta) {
            storage.put(inventarioPlanta.getId(), inventarioPlanta);
            return inventarioPlanta;
        }

        @Override
        public List<InventarioPlanta> findByUsuarioId(Long usuarioId) {
            return storage.values().stream().toList();
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
}
