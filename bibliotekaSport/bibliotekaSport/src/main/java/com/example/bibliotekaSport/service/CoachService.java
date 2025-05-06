package com.example.bibliotekaSport.service;

import com.example.bibliotekaSport.dto.CoachDto;

import java.util.List;

public interface CoachService {
    List<CoachDto> getAll();
    CoachDto getById(Long id);
    void create(CoachDto dto);
    void update(Long id, CoachDto dto);
    void delete(Long id);
}
