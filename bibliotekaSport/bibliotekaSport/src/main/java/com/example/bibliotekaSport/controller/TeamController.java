package com.example.bibliotekaSport.controller;

import com.example.bibliotekaSport.dto.TeamDto;
import com.example.bibliotekaSport.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public List<TeamDto> getAll() {
        return teamService.getAll();
    }

    @GetMapping("/{id}")
    public TeamDto getById(@PathVariable Long id) {
        return teamService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TeamDto teamDto) {
        teamService.create(teamDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody TeamDto teamDto) {
        teamService.update(id, teamDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
