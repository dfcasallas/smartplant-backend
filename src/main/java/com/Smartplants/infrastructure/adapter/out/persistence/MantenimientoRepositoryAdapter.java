package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.MantenimientoRepositoryPort;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataMantenimientoRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MantenimientoRepositoryAdapter implements MantenimientoRepositoryPort {

    private final SpringDataMantenimientoRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public List<Mantenimiento> findAll() {
        return repository.findAll().stream()
                .map(mapper::toMantenimientoDomain)
                .toList();
    }

    @Override
    public Optional<Mantenimiento> findById(Long id) {
        return repository.findById(id).map(mapper::toMantenimientoDomain);
    }

    @Override
    public Mantenimiento save(Mantenimiento mantenimiento) {
        return mapper.toMantenimientoDomain(repository.save(mapper.toMantenimientoEntity(mantenimiento)));
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
