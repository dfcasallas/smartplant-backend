package com.Smartplants.controller;

import com.Smartplants.model.Salud;
import com.Smartplants.repository.SaludRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saludes")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class SaludController {

    private final SaludRepository repository;

    @GetMapping
    public List<Salud> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Salud crear(@RequestBody Salud salud) {
        return repository.save(salud);
    }

    @PutMapping("/{id}")
    public Salud actualizar(@PathVariable Long id, @RequestBody Salud salud) {
        Salud actual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado de salud no encontrado"));
        actual.setEstado(salud.getEstado());
        return repository.save(actual);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
