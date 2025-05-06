package com.example.bibliotekaSport.service;

import com.example.bibliotekaSport.dto.LeagueDto;

import java.util.List;

public interface LeagueService {
    List<LeagueDto> getAll();
    LeagueDto getById(Long id);
    void create(LeagueDto dto);
    void update(Long id, LeagueDto dto);
    void delete(Long id);
}
