package com.Smartplants.application.service;

import com.Smartplants.application.command.RegistrarCuidadoCommand;
import com.Smartplants.application.port.in.CuidadoPlantaUseCase;
import com.Smartplants.application.port.out.CuidadoPlantaRepositoryPort;
import com.Smartplants.application.port.out.InventarioRepositoryPort;
import com.Smartplants.domain.exception.ResourceNotFoundException;
import com.Smartplants.domain.exception.ValidationException;
import com.Smartplants.domain.model.CuidadoPlanta;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.domain.model.TipoCuidado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuidadoPlantaApplicationService implements CuidadoPlantaUseCase {

    private final CuidadoPlantaRepositoryPort cuidadoRepository;
    private final InventarioRepositoryPort inventarioRepository;

    @Override
    public CuidadoPlanta registrar(RegistrarCuidadoCommand command) {
        validarCommand(command);

        InventarioPlanta inventario = inventarioRepository.findById(command.getInventarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado"));

        TipoCuidado tipoCuidado = parseTipoCuidado(command.getTipoCuidado());
        LocalDate fecha = LocalDate.now();

        CuidadoPlanta cuidado = CuidadoPlanta.builder()
                .inventarioId(inventario.getId())
                .tipoCuidado(tipoCuidado)
                .fecha(fecha)
                .observacion(normalizarObservacion(command.getObservacion()))
                .proximaFechaSugerida(calcularProximaFecha(tipoCuidado, inventario, fecha))
                .build();

        return cuidadoRepository.save(cuidado);
    }

    @Override
    public List<CuidadoPlanta> listarPorInventario(Long inventarioId) {
        validarInventarioId(inventarioId);
        if (!inventarioRepository.existsById(inventarioId)) {
            throw new ResourceNotFoundException("Inventario no encontrado");
        }
        return cuidadoRepository.findByInventarioId(inventarioId);
    }

    private void validarCommand(RegistrarCuidadoCommand command) {
        if (command == null) {
            throw new ValidationException("La solicitud de cuidado es obligatoria");
        }
        validarInventarioId(command.getInventarioId());
        if (command.getTipoCuidado() == null || command.getTipoCuidado().isBlank()) {
            throw new ValidationException("El tipoCuidado es obligatorio");
        }
        if (command.getObservacion() != null && command.getObservacion().isBlank()) {
            throw new ValidationException("La observacion no puede contener solo espacios");
        }
    }

    private void validarInventarioId(Long inventarioId) {
        if (inventarioId == null || inventarioId <= 0) {
            throw new ValidationException("El inventarioId es obligatorio");
        }
    }

    private TipoCuidado parseTipoCuidado(String valor) {
        try {
            return TipoCuidado.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new ValidationException("El tipoCuidado no es valido");
        }
    }

    private String normalizarObservacion(String observacion) {
        return observacion == null ? null : observacion.trim();
    }

    private LocalDate calcularProximaFecha(TipoCuidado tipoCuidado, InventarioPlanta inventario, LocalDate fechaBase) {
        return switch (tipoCuidado) {
            case RIEGO -> fechaBase.plusDays(diasParaRiego(inventario));
            case ABONO -> fechaBase.plusDays(30);
            case PODA -> fechaBase.plusDays(45);
            case EXPOSICION_SOL -> fechaBase.plusDays(1);
            case CAMBIO_UBICACION -> fechaBase.plusDays(7);
        };
    }

    private long diasParaRiego(InventarioPlanta inventario) {
        if (inventario.getPlanta() == null
                || inventario.getPlanta().getMantenimiento() == null
                || inventario.getPlanta().getMantenimiento().getNivel() == null) {
            return 5;
        }

        String nivel = inventario.getPlanta().getMantenimiento().getNivel().trim().toUpperCase();
        return switch (nivel) {
            case "BAJO" -> 8;
            case "MEDIO" -> 5;
            case "ALTO" -> 3;
            default -> 5;
        };
    }
}
