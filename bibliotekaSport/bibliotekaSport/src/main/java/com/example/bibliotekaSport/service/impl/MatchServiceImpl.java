package com.example.bibliotekaSport.service.impl;

import com.example.bibliotekaSport.dto.MatchDto;
import com.example.bibliotekaSport.exception.NotFoundException;
import com.example.bibliotekaSport.repository.MatchRepository;
import com.example.bibliotekaSport.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository repository;

    @Override
    public List<MatchDto> getAll() {
        return repository.findAll();
    }

    @Override
    public MatchDto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Match not found with id " + id));
    }

    @Override
    public void create(MatchDto dto) {
        repository.save(dto);
    }

    @Override
    public void update(Long id, MatchDto dto) {
        if (!repository.existsById(id))
            throw new NotFoundException("Match not found with id " + id);
        repository.update(id, dto);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Match not found with id " + id);
        repository.delete(id);
    }
}
