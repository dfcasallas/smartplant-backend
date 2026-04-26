package com.Smartplants.application.port.in;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.domain.model.Salud;

import java.util.List;

public interface SaludUseCase {
    List<Salud> listar();
    Salud crear(CatalogCommand command);
    Salud actualizar(Long id, CatalogCommand command);
    void eliminar(Long id);
}
