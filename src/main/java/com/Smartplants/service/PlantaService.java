package com.Smartplants.service;

import com.Smartplants.model.Familia;
import com.Smartplants.model.Mantenimiento;
import com.Smartplants.model.Planta;
import com.Smartplants.model.Salud;
import com.Smartplants.model.Tipo;
import com.Smartplants.repository.FamiliaRepository;
import com.Smartplants.repository.MantenimientoRepository;
import com.Smartplants.repository.PlantaRepository;
import com.Smartplants.repository.SaludRepository;
import com.Smartplants.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantaService {

    private final PlantaRepository repository;
    private final TipoRepository tipoRepository;
    private final FamiliaRepository familiaRepository;
    private final MantenimientoRepository mantenimientoRepository;
    private final SaludRepository saludRepository;

    public List<Planta> listar() {
        return repository.findAll();
    }

    public Planta obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planta no encontrada"));
    }

    public Planta guardar(Planta planta) {
        return repository.save(prepararRelaciones(planta));
    }

    public Planta actualizar(Long id, Planta nueva) {
        Planta planta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planta no encontrada"));

        planta.setNombre(nueva.getNombre());
        planta.setTipo(obtenerTipo(nueva));
        planta.setFamilia(obtenerFamilia(nueva));
        planta.setMantenimiento(obtenerMantenimiento(nueva));
        planta.setSalud(obtenerSalud(nueva));

        return repository.save(planta);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private Planta prepararRelaciones(Planta planta) {
        planta.setTipo(obtenerTipo(planta));
        planta.setFamilia(obtenerFamilia(planta));
        planta.setMantenimiento(obtenerMantenimiento(planta));
        planta.setSalud(obtenerSalud(planta));
        return planta;
    }

    private Tipo obtenerTipo(Planta planta) {
        if (planta.getTipo() == null || planta.getTipo().getId() == null) {
            throw new RuntimeException("El tipo es obligatorio");
        }
        return tipoRepository.findById(planta.getTipo().getId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
    }

    private Familia obtenerFamilia(Planta planta) {
        if (planta.getFamilia() == null || planta.getFamilia().getId() == null) {
            throw new RuntimeException("La familia es obligatoria");
        }
        return familiaRepository.findById(planta.getFamilia().getId())
                .orElseThrow(() -> new RuntimeException("Familia no encontrada"));
    }

    private Mantenimiento obtenerMantenimiento(Planta planta) {
        if (planta.getMantenimiento() == null || planta.getMantenimiento().getId() == null) {
            throw new RuntimeException("El mantenimiento es obligatorio");
        }
        return mantenimientoRepository.findById(planta.getMantenimiento().getId())
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));
    }

    private Salud obtenerSalud(Planta planta) {
        if (planta.getSalud() == null || planta.getSalud().getId() == null) {
            throw new RuntimeException("El estado de salud es obligatorio");
        }
        return saludRepository.findById(planta.getSalud().getId())
                .orElseThrow(() -> new RuntimeException("Estado de salud no encontrado"));
    }
}
