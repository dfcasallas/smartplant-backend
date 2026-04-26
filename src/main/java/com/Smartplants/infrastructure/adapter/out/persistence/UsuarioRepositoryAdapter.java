package com.Smartplants.infrastructure.adapter.out.persistence;

import com.Smartplants.application.port.out.UsuarioRepositoryPort;
import com.Smartplants.domain.model.Usuario;
import com.Smartplants.infrastructure.adapter.out.persistence.jpa.SpringDataUsuarioRepository;
import com.Smartplants.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository repository;
    private final PersistenceMapper mapper;

    @Override
    public Usuario save(Usuario usuario) {
        return mapper.toUsuarioDomain(repository.save(mapper.toUsuarioEntity(usuario)));
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll().stream()
                .map(mapper::toUsuarioDomain)
                .toList();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id).map(mapper::toUsuarioDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toUsuarioDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
