package com.example.bibliotekaSport.service;

import com.example.bibliotekaSport.dto.MatchDto;

import java.util.List;

public interface MatchService {
    List<MatchDto> getAll();
    MatchDto getById(Long id);
    void create(MatchDto dto);
    void update(Long id, MatchDto dto);
    void delete(Long id);
}
