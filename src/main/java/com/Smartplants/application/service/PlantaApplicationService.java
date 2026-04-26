package com.Smartplants.application.service;

import com.Smartplants.application.command.PlantaCommand;
import com.Smartplants.application.port.in.PlantaUseCase;
import com.Smartplants.application.port.out.FamiliaRepositoryPort;
import com.Smartplants.application.port.out.MantenimientoRepositoryPort;
import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.application.port.out.SaludRepositoryPort;
import com.Smartplants.application.port.out.TipoRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.Familia;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.domain.model.Salud;
import com.Smartplants.domain.model.Tipo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantaApplicationService implements PlantaUseCase {

    private final PlantaRepositoryPort plantaRepository;
    private final TipoRepositoryPort tipoRepository;
    private final FamiliaRepositoryPort familiaRepository;
    private final MantenimientoRepositoryPort mantenimientoRepository;
    private final SaludRepositoryPort saludRepository;

    @Override
    public List<Planta> listar() {
        return plantaRepository.findAll();
    }

    @Override
    public Planta obtenerPorId(Long id) {
        return buscarPlanta(id);
    }

    @Override
    public Planta crear(PlantaCommand command) {
        Planta planta = new Planta();
        aplicarCambios(planta, command);
        return plantaRepository.save(planta);
    }

    @Override
    public Planta actualizar(Long id, PlantaCommand command) {
        Planta planta = buscarPlanta(id);
        aplicarCambios(planta, command);
        return plantaRepository.save(planta);
    }

    @Override
    public void eliminar(Long id) {
        if (!plantaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Planta no encontrada");
        }
        plantaRepository.deleteById(id);
    }

    private Planta buscarPlanta(Long id) {
        return plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta no encontrada"));
    }

    private void aplicarCambios(Planta planta, PlantaCommand command) {
        planta.setNombre(validarTexto(command.getNombre(), "El nombre es obligatorio"));
        planta.setDescripcion(command.getDescripcion());
        planta.setLuz(command.getLuz());
        planta.setRiego(command.getRiego());
        planta.setTemperatura(command.getTemperatura());
        planta.setTamano(command.getTamano());
        planta.setAmbiente(command.getAmbiente());
        planta.setTipo(obtenerTipo(command.getTipoId()));
        planta.setFamilia(obtenerFamilia(command.getFamiliaId()));
        planta.setMantenimiento(obtenerMantenimiento(command.getMantenimientoId()));
        planta.setSalud(obtenerSalud(command.getSaludId()));
    }

    private Tipo obtenerTipo(Long tipoId) {
        validarId(tipoId, "El tipo es obligatorio");
        return tipoRepository.findById(tipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo no encontrado"));
    }

    private Familia obtenerFamilia(Long familiaId) {
        validarId(familiaId, "La familia es obligatoria");
        return familiaRepository.findById(familiaId)
                .orElseThrow(() -> new ResourceNotFoundException("Familia no encontrada"));
    }

    private Mantenimiento obtenerMantenimiento(Long mantenimientoId) {
        validarId(mantenimientoId, "El mantenimiento es obligatorio");
        return mantenimientoRepository.findById(mantenimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado"));
    }

    private Salud obtenerSalud(Long saludId) {
        validarId(saludId, "El estado de salud es obligatorio");
        return saludRepository.findById(saludId)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de salud no encontrado"));
    }

    private String validarTexto(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensajeError);
        }
        return valor.trim();
    }

    private void validarId(Long id, String mensajeError) {
        if (id == null) {
            throw new ValidationException(mensajeError);
        }
    }
}
