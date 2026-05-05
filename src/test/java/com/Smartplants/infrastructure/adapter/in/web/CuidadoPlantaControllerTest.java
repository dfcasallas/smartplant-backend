package com.Smartplants.infrastructure.adapter.in.web;

import com.Smartplants.application.command.RegistrarCuidadoCommand;
import com.Smartplants.application.port.in.CuidadoPlantaUseCase;
import com.Smartplants.domain.model.CuidadoPlanta;
import com.Smartplants.domain.model.TipoCuidado;
import com.Smartplants.infrastructure.adapter.in.web.request.RegistrarCuidadoRequest;
import com.Smartplants.infrastructure.adapter.in.web.response.CuidadoPlantaResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CuidadoPlantaControllerTest {

    @Test
    void listaCuidadosPorInventario() {
        CuidadoPlantaController controller = new CuidadoPlantaController(new StubCuidadoPlantaUseCase());

        List<CuidadoPlantaResponse> response = controller.listar(11L);

        assertEquals(1, response.size());
        assertEquals(21L, response.get(0).getId());
        assertEquals(11L, response.get(0).getInventarioId());
        assertEquals("RIEGO", response.get(0).getTipoCuidado());
        assertEquals(LocalDate.of(2026, 5, 4), response.get(0).getFecha());
        assertEquals("Tierra seca", response.get(0).getObservacion());
        assertEquals(LocalDate.of(2026, 5, 9), response.get(0).getProximaFechaSugerida());
    }

    @Test
    void registraCuidadoConInventarioDeRuta() {
        StubCuidadoPlantaUseCase useCase = new StubCuidadoPlantaUseCase();
        CuidadoPlantaController controller = new CuidadoPlantaController(useCase);
        RegistrarCuidadoRequest request = new RegistrarCuidadoRequest();
        request.setTipoCuidado("RIEGO");
        request.setObservacion("Tierra seca");

        CuidadoPlantaResponse response = controller.registrar(11L, request);

        assertEquals(11L, useCase.lastCommand.getInventarioId());
        assertEquals("RIEGO", useCase.lastCommand.getTipoCuidado());
        assertEquals("Tierra seca", useCase.lastCommand.getObservacion());
        assertEquals(21L, response.getId());
    }

    private static CuidadoPlanta cuidado() {
        return CuidadoPlanta.builder()
                .id(21L)
                .inventarioId(11L)
                .tipoCuidado(TipoCuidado.RIEGO)
                .fecha(LocalDate.of(2026, 5, 4))
                .observacion("Tierra seca")
                .proximaFechaSugerida(LocalDate.of(2026, 5, 9))
                .build();
    }

    private static final class StubCuidadoPlantaUseCase implements CuidadoPlantaUseCase {
        private RegistrarCuidadoCommand lastCommand;

        @Override
        public CuidadoPlanta registrar(RegistrarCuidadoCommand command) {
            this.lastCommand = command;
            return cuidado();
        }

        @Override
        public List<CuidadoPlanta> listarPorInventario(Long inventarioId) {
            return List.of(cuidado());
        }
    }
}
