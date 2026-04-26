package com.Smartplants.application.service;

import com.Smartplants.application.command.CatalogCommand;
import com.Smartplants.application.port.in.MantenimientoUseCase;
import com.Smartplants.application.port.out.MantenimientoRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Mantenimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MantenimientoApplicationService implements MantenimientoUseCase {

    private final MantenimientoRepositoryPort repository;

    @Override
    public List<Mantenimiento> listar() {
        return repository.findAll();
    }

    @Override
    public Mantenimiento crear(CatalogCommand command) {
        return repository.save(Mantenimiento.builder()
                .nivel(validarTexto(command.getValor(), "El nivel de mantenimiento es obligatorio"))
                .build());
    }

    @Override
    public Mantenimiento actualizar(Long id, CatalogCommand command) {
        Mantenimiento mantenimiento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado"));
        mantenimiento.setNivel(validarTexto(command.getValor(), "El nivel de mantenimiento es obligatorio"));
        return repository.save(mantenimiento);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Mantenimiento no encontrado");
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
