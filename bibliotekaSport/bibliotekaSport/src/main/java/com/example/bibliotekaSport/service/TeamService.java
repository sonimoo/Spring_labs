package com.example.bibliotekaSport.service;

import com.example.bibliotekaSport.dto.TeamDto;

import java.util.List;

public interface TeamService {
    List<TeamDto> getAll();
    TeamDto getById(Long id);
    void create(TeamDto teamDto);
    void update(Long id, TeamDto teamDto);
    void delete(Long id);
}
