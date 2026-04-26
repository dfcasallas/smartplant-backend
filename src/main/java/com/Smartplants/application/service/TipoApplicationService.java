package com.Smartplants.application.service;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.TipoUseCase;
import com.Smartplants.application.port.out.TipoRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoApplicationService implements TipoUseCase {

    private final TipoRepositoryPort repository;

    @Override
    public List<Tipo> listar() {
        return repository.findAll();
    }

    @Override
    public Tipo crear(CatalogCommand command) {
        return repository.save(Tipo.builder()
                .nombre(validarTexto(command.getValor(), "El nombre del tipo es obligatorio"))
                .build());
    }

    @Override
    public Tipo actualizar(Long id, CatalogCommand command) {
        Tipo tipo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo no encontrado"));
        tipo.setNombre(validarTexto(command.getValor(), "El nombre del tipo es obligatorio"));
        return repository.save(tipo);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo no encontrado");
        }
        repository.deleteById(id);
    }

    private String validarTexto(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensajeError);
        }
        return valor.trim();
    }
}
