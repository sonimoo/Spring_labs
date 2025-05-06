package com.example.bibliotekaSport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerDto {
    private Long id;

    @NotBlank(message = "Player name must not be blank")
    private String name;

    @NotBlank(message = "Position must not be blank")
    private String position;

    @NotNull(message = "Team ID is required")
    private Long teamId;
}
