package com.example.bibliotekaSport.service.impl;

import com.example.bibliotekaSport.dto.CoachDto;
import com.example.bibliotekaSport.exception.NotFoundException;
import com.example.bibliotekaSport.repository.CoachRepository;
import com.example.bibliotekaSport.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository repository;

    @Override
    public List<CoachDto> getAll() {
        return repository.findAll();
    }

    @Override
    public CoachDto getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coach not found with id " + id));
    }

    @Override
    public void create(CoachDto dto) {
        repository.save(dto);
    }

    @Override
    public void update(Long id, CoachDto dto) {
        if (!repository.existsById(id))
            throw new NotFoundException("Coach not found with id " + id);
        repository.update(id, dto);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Coach not found with id " + id);
        repository.delete(id);
    }
}
