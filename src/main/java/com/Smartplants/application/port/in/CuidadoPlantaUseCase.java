package com.Smartplants.application.port.in;

import com.Smartplants.application.command.RegistrarCuidadoCommand;
import com.Smartplants.domain.model.CuidadoPlanta;

import java.util.List;

public interface CuidadoPlantaUseCase {
    CuidadoPlanta registrar(RegistrarCuidadoCommand command);
    List<CuidadoPlanta> listarPorInventario(Long inventarioId);
}
