package com.example.bibliotekaSport.controller;

import com.example.bibliotekaSport.dto.PlayerDto;
import com.example.bibliotekaSport.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public List<PlayerDto> getAll() {
        return playerService.getAll();
    }

    @GetMapping("/{id}")
    public PlayerDto getById(@PathVariable Long id) {
        return playerService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PlayerDto playerDto) {
        playerService.create(playerDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody PlayerDto playerDto) {
        playerService.update(id, playerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
