package com.Smartplants.application.port.out;

import com.Smartplants.domain.model.CuidadoPlanta;

import java.util.List;

public interface CuidadoPlantaRepositoryPort {
    CuidadoPlanta save(CuidadoPlanta cuidadoPlanta);
    List<CuidadoPlanta> findByInventarioId(Long inventarioId);
}
