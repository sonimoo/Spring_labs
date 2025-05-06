package com.example.bibliotekaSport.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoachDto {
    private Long id;

    @NotBlank(message = "Coach name must not be blank")
    private String name;

    @NotBlank(message = "Nationality must not be blank")
    private String nationality;
}
