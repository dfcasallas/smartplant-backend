package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.TipoRepositoryPort;
import com.Smartplants.domain.model.Tipo;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataTipoRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TipoRepositoryAdapter implements TipoRepositoryPort {

    private final SpringDataTipoRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public List<Tipo> findAll() {
        return repository.findAll().stream()
                .map(mapper::toTipoDomain)
                .toList();
    }

    @Override
    public Optional<Tipo> findById(Long id) {
        return repository.findById(id).map(mapper::toTipoDomain);
    }

    @Override
    public Tipo save(Tipo tipo) {
        return mapper.toTipoDomain(repository.save(mapper.toTipoEntity(tipo)));
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
