package com.example.bibliotekaSport.repository;

import com.example.bibliotekaSport.dto.LeagueDto;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository {
    List<LeagueDto> findAll();
    Optional<LeagueDto> findById(Long id);
    void save(LeagueDto dto);
    void update(Long id, LeagueDto dto);
    void delete(Long id);
    boolean existsById(Long id);
}
