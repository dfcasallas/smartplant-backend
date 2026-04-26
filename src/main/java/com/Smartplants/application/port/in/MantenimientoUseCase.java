package com.Smartplants.application.port.in;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.domain.model.Mantenimiento;

import java.util.List;

public interface MantenimientoUseCase {
    List<Mantenimiento> listar();
    Mantenimiento crear(CatalogCommand command);
    Mantenimiento actualizar(Long id, CatalogCommand command);
    void eliminar(Long id);
}
