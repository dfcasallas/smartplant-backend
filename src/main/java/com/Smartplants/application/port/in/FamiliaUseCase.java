package com.Smartplants.application.port.in;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.domain.model.Familia;

import java.util.List;

public interface FamiliaUseCase {
    List<Familia> listar();
    Familia crear(CatalogCommand command);
    Familia actualizar(Long id, CatalogCommand command);
    void eliminar(Long id);
}
