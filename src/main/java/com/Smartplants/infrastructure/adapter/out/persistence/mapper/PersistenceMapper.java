package com.Smartplants.infrastructure.adapter.out.persistence.mapper;

import com.Smartplants.domain.model.Familia;
import com.Smartplants.domain.model.CuidadoPlanta;
import com.Smartplants.domain.model.InventarioPlanta;
import com.Smartplants.domain.model.Mantenimiento;
import com.Smartplants.domain.model.Planta;
import com.Smartplants.domain.model.Salud;
import com.Smartplants.domain.model.Tipo;
import com.Smartplants.domain.model.Usuario;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.CuidadoPlantaEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.FamiliaEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.InventarioPlantaEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.MantenimientoEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.PlantaEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.SaludEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.TipoEntity;
import com.Smartplants.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class PersistenceMapper {

    public Tipo toTipoDomain(TipoEntity entity) {
        if (entity == null) {
            return null;
        }
        return Tipo.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }

    public Familia toFamiliaDomain(FamiliaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Familia.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }

    public Mantenimiento toMantenimientoDomain(MantenimientoEntity entity) {
        if (entity == null) {
            return null;
        }
        return Mantenimiento.builder()
                .id(entity.getId())
                .nivel(entity.getNivel())
                .build();
    }

    public Salud toSaludDomain(SaludEntity entity) {
        if (entity == null) {
            return null;
        }
        return Salud.builder()
                .id(entity.getId())
                .estado(entity.getEstado())
                .build();
    }

    public Planta toPlantaDomain(PlantaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Planta.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .luz(entity.getLuz())
                .riego(entity.getRiego())
                .temperatura(entity.getTemperatura())
                .tamano(entity.getTamano())
                .ambiente(entity.getAmbiente())
                .tipo(toTipoDomain(entity.getTipo()))
                .familia(toFamiliaDomain(entity.getFamilia()))
                .mantenimiento(toMantenimientoDomain(entity.getMantenimiento()))
                .salud(toSaludDomain(entity.getSalud()))
                .build();
    }

    public TipoEntity toTipoEntity(Tipo domain) {
        if (domain == null) {
            return null;
        }
        TipoEntity entity = new TipoEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }

    public FamiliaEntity toFamiliaEntity(Familia domain) {
        if (domain == null) {
            return null;
        }
        FamiliaEntity entity = new FamiliaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        return entity;
    }

    public MantenimientoEntity toMantenimientoEntity(Mantenimiento domain) {
        if (domain == null) {
            return null;
        }
        MantenimientoEntity entity = new MantenimientoEntity();
        entity.setId(domain.getId());
        entity.setNivel(domain.getNivel());
        return entity;
    }

    public SaludEntity toSaludEntity(Salud domain) {
        if (domain == null) {
            return null;
        }
        SaludEntity entity = new SaludEntity();
        entity.setId(domain.getId());
        entity.setEstado(domain.getEstado());
        return entity;
    }

    public PlantaEntity toPlantaEntity(Planta domain) {
        if (domain == null) {
            return null;
        }
        PlantaEntity entity = new PlantaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setLuz(domain.getLuz());
        entity.setRiego(domain.getRiego());
        entity.setTemperatura(domain.getTemperatura());
        entity.setTamano(domain.getTamano());
        entity.setAmbiente(domain.getAmbiente());
        entity.setTipo(toTipoEntity(domain.getTipo()));
        entity.setFamilia(toFamiliaEntity(domain.getFamilia()));
        entity.setMantenimiento(toMantenimientoEntity(domain.getMantenimiento()));
        entity.setSalud(toSaludEntity(domain.getSalud()));
        return entity;
    }

    public InventarioPlanta toInventarioDomain(InventarioPlantaEntity entity) {
        if (entity == null) {
            return null;
        }
        return InventarioPlanta.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuarioId())
                .planta(toPlantaDomain(entity.getPlanta()))
                .nombrePersonalizado(entity.getNombrePersonalizado())
                .fechaAgregado(entity.getFechaAgregado())
                .build();
    }

    public InventarioPlantaEntity toInventarioEntity(InventarioPlanta domain) {
        if (domain == null) {
            return null;
        }
        InventarioPlantaEntity entity = new InventarioPlantaEntity();
        entity.setId(domain.getId());
        entity.setUsuarioId(domain.getUsuarioId());
        entity.setPlanta(toPlantaEntity(domain.getPlanta()));
        entity.setNombrePersonalizado(domain.getNombrePersonalizado());
        entity.setFechaAgregado(domain.getFechaAgregado());
        return entity;
    }

    public CuidadoPlanta toCuidadoDomain(CuidadoPlantaEntity entity) {
        if (entity == null) {
            return null;
        }
        return CuidadoPlanta.builder()
                .id(entity.getId())
                .inventarioId(entity.getInventarioId())
                .tipoCuidado(entity.getTipoCuidado())
                .fecha(entity.getFecha())
                .observacion(entity.getObservacion())
                .proximaFechaSugerida(entity.getProximaFechaSugerida())
                .build();
    }

    public CuidadoPlantaEntity toCuidadoEntity(CuidadoPlanta domain) {
        if (domain == null) {
            return null;
        }
        CuidadoPlantaEntity entity = new CuidadoPlantaEntity();
        entity.setId(domain.getId());
        entity.setInventarioId(domain.getInventarioId());
        entity.setTipoCuidado(domain.getTipoCuidado());
        entity.setFecha(domain.getFecha());
        entity.setObservacion(domain.getObservacion());
        entity.setProximaFechaSugerida(domain.getProximaFechaSugerida());
        return entity;
    }

    public Usuario toUsuarioDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        return Usuario.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .rol(entity.getRol())
                .ultimaConexion(entity.getUltimaConexion())
                .build();
    }

    public UsuarioEntity toUsuarioEntity(Usuario domain) {
        if (domain == null) {
            return null;
        }
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setRol(domain.getRol());
        entity.setUltimaConexion(domain.getUltimaConexion());
        return entity;
    }
}
