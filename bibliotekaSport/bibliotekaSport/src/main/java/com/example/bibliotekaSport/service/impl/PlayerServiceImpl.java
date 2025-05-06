package com.example.bibliotekaSport.service.impl;

import com.example.bibliotekaSport.dto.PlayerDto;
import com.example.bibliotekaSport.exception.NotFoundException;
import com.example.bibliotekaSport.repository.PlayerRepository;
import com.example.bibliotekaSport.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;

    @Override
    public List<PlayerDto> getAll() {
        return repository.findAll();
    }

    @Override
    public PlayerDto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with id " + id));
    }

    @Override
    public void create(PlayerDto dto) {
        repository.save(dto);
    }

    @Override
    public void update(Long id, PlayerDto dto) {
        if (!repository.existsById(id))
            throw new NotFoundException("Player not found with id " + id);
        repository.update(id, dto);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Player not found with id " + id);
        repository.delete(id);
    }
}
