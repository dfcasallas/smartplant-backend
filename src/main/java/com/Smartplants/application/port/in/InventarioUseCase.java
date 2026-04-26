package com.Smartplants.application.port.in;

import com.Smartplants.application.command.AgregarPlantaInventarioCommand;
import com.Smartplants.domain.model.InventarioPlanta;

import java.util.List;

public interface InventarioUseCase {
    InventarioPlanta agregar(AgregarPlantaInventarioCommand command);
    List<InventarioPlanta> listarPorUsuario(Long usuarioId);
    InventarioPlanta consultarPorId(Long inventarioId);
    void eliminar(Long inventarioId);
}
