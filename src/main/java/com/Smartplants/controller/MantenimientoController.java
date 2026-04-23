package com.Smartplants.controller;

import com.Smartplants.model.Mantenimiento;
import com.Smartplants.repository.MantenimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimientos")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class MantenimientoController {

    private final MantenimientoRepository repository;

    @GetMapping
    public List<Mantenimiento> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Mantenimiento crear(@RequestBody Mantenimiento mantenimiento) {
        return repository.save(mantenimiento);
    }

    @PutMapping("/{id}")
    public Mantenimiento actualizar(@PathVariable Long id, @RequestBody Mantenimiento mantenimiento) {
        Mantenimiento actual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));
        actual.setNivel(mantenimiento.getNivel());
        return repository.save(actual);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
