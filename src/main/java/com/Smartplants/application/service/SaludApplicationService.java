package com.Smartplants.application.service;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.SaludUseCase;
import com.Smartplants.application.port.out.SaludRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Salud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaludApplicationService implements SaludUseCase {

    private final SaludRepositoryPort repository;

    @Override
    public List<Salud> listar() {
        return repository.findAll();
    }

    @Override
    public Salud crear(CatalogCommand command) {
        return repository.save(Salud.builder()
                .estado(validarTexto(command.getValor(), "El estado de salud es obligatorio"))
                .build());
    }

    @Override
    public Salud actualizar(Long id, CatalogCommand command) {
        Salud salud = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de salud no encontrado"));
        salud.setEstado(validarTexto(command.getValor(), "El estado de salud es obligatorio"));
        return repository.save(salud);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Estado de salud no encontrado");
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
