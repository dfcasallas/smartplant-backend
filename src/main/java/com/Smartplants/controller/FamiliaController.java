package com.Smartplants.controller;

import com.Smartplants.model.Familia;
import com.Smartplants.repository.FamiliaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/familias")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class FamiliaController {

    private final FamiliaRepository repository;

    @GetMapping
    public List<Familia> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Familia crear(@RequestBody Familia familia) {
        return repository.save(familia);
    }

    @PutMapping("/{id}")
    public Familia actualizar(@PathVariable Long id, @RequestBody Familia familia) {
        Familia actual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Familia no encontrada"));
        actual.setNombre(familia.getNombre());
        return repository.save(actual);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
