package com.Smartplants.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.Smartplants.model.Planta;
import com.Smartplants.service.PlantaService;

import java.util.List;

@RestController
@RequestMapping("/api/plantas")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class PlantaController {

    private final PlantaService service;

    @GetMapping
    public List<Planta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Planta obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Planta crear(@RequestBody Planta planta) {
        return service.guardar(planta);
    }

    @PutMapping("/{id}")
    public Planta actualizar(@PathVariable Long id, @RequestBody Planta planta) {
        return service.actualizar(id, planta);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
