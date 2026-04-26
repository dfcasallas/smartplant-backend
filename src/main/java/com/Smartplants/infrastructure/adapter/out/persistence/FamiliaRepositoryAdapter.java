package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.FamiliaRepositoryPort;
import com.Smartplants.domain.model.Familia;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataFamiliaRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FamiliaRepositoryAdapter implements FamiliaRepositoryPort {

    private final SpringDataFamiliaRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public List<Familia> findAll() {
        return repository.findAll().stream()
                .map(mapper::toFamiliaDomain)
                .toList();
    }

    @Override
    public Optional<Familia> findById(Long id) {
        return repository.findById(id).map(mapper::toFamiliaDomain);
    }

    @Override
    public Familia save(Familia familia) {
        return mapper.toFamiliaDomain(repository.save(mapper.toFamiliaEntity(familia)));
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
