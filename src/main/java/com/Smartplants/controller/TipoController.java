package com.Smartplants.controller;

import com.Smartplants.model.Tipo;
import com.Smartplants.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class TipoController {

    private final TipoRepository repository;

    @GetMapping
    public List<Tipo> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Tipo crear(@RequestBody Tipo tipo) {
        return repository.save(tipo);
    }

    @PutMapping("/{id}")
    public Tipo actualizar(@PathVariable Long id, @RequestBody Tipo tipo) {
        Tipo actual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
        actual.setNombre(tipo.getNombre());
        return repository.save(actual);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
