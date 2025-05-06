package com.example.bibliotekaSport.repository;

import com.example.bibliotekaSport.dto.MatchDto;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    List<MatchDto> findAll();
    Optional<MatchDto> findById(Long id);
    void save(MatchDto dto);
    void update(Long id, MatchDto dto);
    void delete(Long id);
    boolean existsById(Long id);
}
