package com.Smartplants.application.port.in;

import com.Smartplants.application.command.PlantaCommand;
import com.Smartplants.domain.model.Planta;

import java.util.List;

public interface PlantaUseCase {
    List<Planta> listar();
    Planta obtenerPorId(Long id);
    Planta crear(PlantaCommand command);
    Planta actualizar(Long id, PlantaCommand command);
    void eliminar(Long id);
}
