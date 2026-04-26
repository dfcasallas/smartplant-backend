package com.Smartplants.application.service;

import com.Smartplants.application.command.AgregarPlantaInventarioCommand;
import com.Smartplants.application.port.in.InventarioUseCase;
import com.Smartplants.application.port.out.InventarioRepositoryPort;
import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.domain.model.Planta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioApplicationService implements InventarioUseCase {

    private final InventarioRepositoryPort inventarioRepository;
    private final PlantaRepositoryPort plantaRepository;

    @Override
    public InventarioPlanta agregar(AgregarPlantaInventarioCommand command) {
        validarCommand(command);

        Planta planta = plantaRepository.findById(command.getPlantaId())
                .orElseThrow(() -> new ResourceNotFoundException("Planta no encontrada"));

        InventarioPlanta inventarioPlanta = InventarioPlanta.builder()
                .usuarioId(command.getUsuarioId())
                .planta(planta)
                .nombrePersonalizado(command.getNombrePersonalizado().trim())
                .fechaAgregado(LocalDate.now())
                .build();

        return inventarioRepository.save(inventarioPlanta);
    }

    @Override
    public List<InventarioPlanta> listarPorUsuario(Long usuarioId) {
        validarUsuarioId(usuarioId);
        return inventarioRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public InventarioPlanta consultarPorId(Long inventarioId) {
        validarInventarioId(inventarioId);
        return inventarioRepository.findById(inventarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado"));
    }

    @Override
    public void eliminar(Long inventarioId) {
        validarInventarioId(inventarioId);
        if (!inventarioRepository.existsById(inventarioId)) {
            throw new ResourceNotFoundException("Inventario no encontrado");
        }
        inventarioRepository.deleteById(inventarioId);
    }

    private void validarCommand(AgregarPlantaInventarioCommand command) {
        if (command == null) {
            throw new ValidationException("La solicitud de inventario es obligatoria");
        }
        validarUsuarioId(command.getUsuarioId());
        if (command.getPlantaId() == null) {
            throw new ValidationException("El plantaId es obligatorio");
        }
        if (command.getNombrePersonalizado() == null || command.getNombrePersonalizado().isBlank()) {
            throw new ValidationException("El nombrePersonalizado es obligatorio");
        }
    }

    private void validarUsuarioId(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new ValidationException("El usuarioId es obligatorio");
        }
    }

    private void validarInventarioId(Long inventarioId) {
        if (inventarioId == null || inventarioId <= 0) {
            throw new ValidationException("El inventarioId es obligatorio");
        }
    }
}
