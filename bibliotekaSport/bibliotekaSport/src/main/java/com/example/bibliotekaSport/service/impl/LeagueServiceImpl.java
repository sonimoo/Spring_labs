package com.example.bibliotekaSport.service.impl;

import com.example.bibliotekaSport.dto.LeagueDto;
import com.example.bibliotekaSport.exception.NotFoundException;
import com.example.bibliotekaSport.repository.LeagueRepository;
import com.example.bibliotekaSport.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository repository;

    @Override
    public List<LeagueDto> getAll() {
        return repository.findAll();
    }

    @Override
    public LeagueDto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("League not found with id " + id));
    }

    @Override
    public void create(LeagueDto dto) {
        repository.save(dto);
    }

    @Override
    public void update(Long id, LeagueDto dto) {
        if (!repository.existsById(id))
            throw new NotFoundException("League not found with id " + id);
        repository.update(id, dto);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("League not found with id " + id);
        repository.delete(id);
    }
}
