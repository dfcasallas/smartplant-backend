package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.AgregarPlantaInventarioCommand;
import com.Smartplants.application.port.in.InventarioUseCase;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.infrastructure.adapter.in.web.request.AgregarInventarioRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.InventarioResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventarioControllerTest {

    @Test
    void listaInventarioPorUsuario() {
        InventarioController controller = new InventarioController(new StubInventarioUseCase());

        List<InventarioResponse> response = controller.listarPorUsuario(7L);

        assertEquals(1, response.size());
        assertEquals(11L, response.get(0).getId());
        assertEquals(7L, response.get(0).getUsuarioId());
        assertEquals(3L, response.get(0).getPlantaId());
        assertEquals("Monstera", response.get(0).getNombrePlanta());
        assertEquals("Sala", response.get(0).getNombrePersonalizado());
        assertEquals(LocalDate.of(2026, 5, 4), response.get(0).getFechaAgregado());
    }

    @Test
    void consultaInventarioPorId() {
        InventarioController controller = new InventarioController(new StubInventarioUseCase());

        InventarioResponse response = controller.consultarPorId(11L);

        assertEquals(11L, response.getId());
        assertEquals("Sala", response.getNombrePersonalizado());
    }

    @Test
    void agregaPlantaAlInventarioConUsuarioDeRuta() {
        StubInventarioUseCase useCase = new StubInventarioUseCase();
        InventarioController controller = new InventarioController(useCase);
        AgregarInventarioRequest request = new AgregarInventarioRequest();
        request.setPlantaId(3L);
        request.setNombrePersonalizado("Sala");

        InventarioResponse response = controller.agregar(7L, request);

        assertEquals(7L, useCase.lastCommand.getUsuarioId());
        assertEquals(3L, useCase.lastCommand.getPlantaId());
        assertEquals("Sala", useCase.lastCommand.getNombrePersonalizado());
        assertEquals(11L, response.getId());
    }

    private static InventarioPlanta inventario() {
        return InventarioPlanta.builder()
                .id(11L)
                .usuarioId(7L)
                .planta(Planta.builder().id(3L).nombre("Monstera").build())
                .nombrePersonalizado("Sala")
                .fechaAgregado(LocalDate.of(2026, 5, 4))
                .build();
    }

    private static final class StubInventarioUseCase implements InventarioUseCase {
        private AgregarPlantaInventarioCommand lastCommand;

        @Override
        public InventarioPlanta agregar(AgregarPlantaInventarioCommand command) {
            this.lastCommand = command;
            return inventario();
        }

        @Override
        public List<InventarioPlanta> listarPorUsuario(Long usuarioId) {
            return List.of(inventario());
        }

        @Override
        public InventarioPlanta consultarPorId(Long inventarioId) {
            return inventario();
        }

        @Override
        public void eliminar(Long inventarioId) {
        }
    }
}
