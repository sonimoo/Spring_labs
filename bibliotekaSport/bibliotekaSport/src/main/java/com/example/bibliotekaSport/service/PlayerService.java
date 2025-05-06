package com.example.bibliotekaSport.service;

import com.example.bibliotekaSport.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> getAll();
    PlayerDto getById(Long id);
    void create(PlayerDto dto);
    void update(Long id, PlayerDto dto);
    void delete(Long id);
}
