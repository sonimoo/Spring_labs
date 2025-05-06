package com.example.bibliotekaSport.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchDto {
    private Long id;

    @NotNull(message = "Home team ID is required")
    private Long homeTeamId;

    @NotNull(message = "Away team ID is required")
    private Long awayTeamId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private Integer homeScore;
    private Integer awayScore;
}
