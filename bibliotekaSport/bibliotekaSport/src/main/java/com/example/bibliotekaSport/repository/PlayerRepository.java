package com.example.bibliotekaSport.repository;

import com.example.bibliotekaSport.dto.PlayerDto;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    List<PlayerDto> findAll();
    Optional<PlayerDto> findById(Long id);
    void save(PlayerDto dto);
    void update(Long id, PlayerDto dto);
    void delete(Long id);
    boolean existsById(Long id);
}
