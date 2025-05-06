package com.example.bibliotekaSport.repository;

import com.example.bibliotekaSport.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    List<TeamDto> findAll();
    Optional<TeamDto> findById(Long id);
    void save(TeamDto team);
    void update(Long id, TeamDto team);
    void delete(Long id);
    boolean existsById(Long id);
}
