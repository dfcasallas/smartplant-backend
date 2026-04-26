package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.CuidadoPlantaRepositoryPort;
import com.Smartplants.domain.model.CuidadoPlanta;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataCuidadoPlantaRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CuidadoPlantaRepositoryAdapter implements CuidadoPlantaRepositoryPort {

    private final SpringDataCuidadoPlantaRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public CuidadoPlanta save(CuidadoPlanta cuidadoPlanta) {
        return mapper.toCuidadoDomain(repository.save(mapper.toCuidadoEntity(cuidadoPlanta)));
    }

    @Override
    public List<CuidadoPlanta> findByInventarioId(Long inventarioId) {
        return repository.findByInventarioId(inventarioId).stream()
                .map(mapper::toCuidadoDomain)
                .toList();
    }
}
