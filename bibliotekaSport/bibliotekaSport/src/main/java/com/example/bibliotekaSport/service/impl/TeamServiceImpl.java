package com.example.bibliotekaSport.service.impl;

import com.example.bibliotekaSport.dto.*;
import com.example.bibliotekaSport.exception.NotFoundException;
import com.example.bibliotekaSport.repository.TeamRepository;
import com.example.bibliotekaSport.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public List<TeamDto> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public TeamDto getById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team not found with id " + id));
    }

    @Override
    public void create(TeamDto teamDto) {
        teamRepository.save(teamDto);
    }

    @Override
    public void update(Long id, TeamDto teamDto) {
        if (!teamRepository.existsById(id)) {
            throw new NotFoundException("Team not found with id " + id);
        }
        teamRepository.update(id, teamDto);
    }

    @Override
    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new NotFoundException("Team not found with id " + id);
        }
        teamRepository.delete(id);
    }
}
