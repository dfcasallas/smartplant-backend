package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.PlantaRepositoryPort;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataPlantaRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlantaRepositoryAdapter implements PlantaRepositoryPort {

    private final SpringDataPlantaRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public List<Planta> findAll() {
        return repository.findAll().stream()
                .map(mapper::toPlantaDomain)
                .toList();
    }

    @Override
    public Optional<Planta> findById(Long id) {
        return repository.findById(id).map(mapper::toPlantaDomain);
    }

    @Override
    public Planta save(Planta planta) {
        return mapper.toPlantaDomain(repository.save(mapper.toPlantaEntity(planta)));
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
