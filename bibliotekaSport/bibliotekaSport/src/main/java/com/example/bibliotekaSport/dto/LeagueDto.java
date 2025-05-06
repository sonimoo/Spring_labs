package com.example.bibliotekaSport.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LeagueDto {
    private Long id;

    @NotBlank(message = "League name must not be blank")
    private String name;

    @NotBlank(message = "Country must not be blank")
    private String country;
}
