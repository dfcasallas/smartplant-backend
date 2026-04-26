package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.SaludRepositoryPort;
import com.Smartplants.domain.model.Salud;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataSaludRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SaludRepositoryAdapter implements SaludRepositoryPort {

    private final SpringDataSaludRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public List<Salud> findAll() {
        return repository.findAll().stream()
                .map(mapper::toSaludDomain)
                .toList();
    }

    @Override
    public Optional<Salud> findById(Long id) {
        return repository.findById(id).map(mapper::toSaludDomain);
    }

    @Override
    public Salud save(Salud salud) {
        return mapper.toSaludDomain(repository.save(mapper.toSaludEntity(salud)));
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
