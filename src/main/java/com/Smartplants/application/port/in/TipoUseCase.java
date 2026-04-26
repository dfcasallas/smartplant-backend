package com.Smartplants.application.port.in;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.domain.model.Tipo;

import java.util.List;

public interface TipoUseCase {
    List<Tipo> listar();
    Tipo crear(CatalogCommand command);
    Tipo actualizar(Long id, CatalogCommand command);
    void eliminar(Long id);
}
