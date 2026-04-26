package com.Smartplants.application.service;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.FamiliaUseCase;
import com.Smartplants.application.port.out.FamiliaRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Familia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamiliaApplicationService implements FamiliaUseCase {

    private final FamiliaRepositoryPort repository;

    @Override
    public List<Familia> listar() {
        return repository.findAll();
    }

    @Override
    public Familia crear(CatalogCommand command) {
        return repository.save(Familia.builder()
                .nombre(validarTexto(command.getValor(), "El nombre de la familia es obligatorio"))
                .build());
    }

    @Override
    public Familia actualizar(Long id, CatalogCommand command) {
        Familia familia = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Familia no encontrada"));
        familia.setNombre(validarTexto(command.getValor(), "El nombre de la familia es obligatorio"));
        return repository.save(familia);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Familia no encontrada");
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
