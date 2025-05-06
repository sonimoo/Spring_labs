package com.example.bibliotekaSport.repository;

import com.example.bibliotekaSport.dto.CoachDto;

import java.util.List;
import java.util.Optional;

public interface CoachRepository {
    List<CoachDto> findAll();
    Optional<CoachDto> findById(Long id);
    void save(CoachDto dto);
    void update(Long id, CoachDto dto);
    void delete(Long id);
    boolean existsById(Long id);
}
