package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.InventarioRepositoryPort;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataInventarioPlantaRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventarioRepositoryAdapter implements InventarioRepositoryPort {

    private final SpringDataInventarioPlantaRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public InventarioPlanta save(InventarioPlanta inventarioPlanta) {
        return mapper.toInventarioDomain(repository.save(mapper.toInventarioEntity(inventarioPlanta)));
    }

    @Override
    public List<InventarioPlanta> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toInventarioDomain)
                .toList();
    }

    @Override
    public Optional<InventarioPlanta> findById(Long id) {
        return repository.findById(id).map(mapper::toInventarioDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
