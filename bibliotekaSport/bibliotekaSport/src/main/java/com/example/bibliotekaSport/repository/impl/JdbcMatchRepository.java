package com.example.bibliotekaSport.repository.impl;

import com.example.bibliotekaSport.dto.MatchDto;
import com.example.bibliotekaSport.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcMatchRepository implements MatchRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<MatchDto> findAll() {
        return jdbc.query("SELECT * FROM matches", mapper);
    }

    @Override
    public Optional<MatchDto> findById(Long id) {
        return jdbc.query("SELECT * FROM matches WHERE id = ?", mapper, id).stream().findFirst();
    }

    @Override
    public void save(MatchDto dto) {
        jdbc.update("INSERT INTO matches (home_team_id, away_team_id, match_date, home_score, away_score) VALUES (?, ?, ?, ?, ?)",
                dto.getHomeTeamId(), dto.getAwayTeamId(), dto.getDate(), dto.getHomeScore(), dto.getAwayScore());
    }

    @Override
    public void update(Long id, MatchDto dto) {
        jdbc.update("UPDATE matches SET home_team_id = ?, away_team_id = ?, match_date = ?, home_score = ?, away_score = ? WHERE id = ?",
                dto.getHomeTeamId(), dto.getAwayTeamId(), dto.getDate(), dto.getHomeScore(), dto.getAwayScore(), id);
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM matches WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM matches WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private final RowMapper<MatchDto> mapper = (rs, i) -> {
        MatchDto dto = new MatchDto();
        dto.setId(rs.getLong("id"));
        dto.setHomeTeamId(rs.getLong("home_team_id"));
        dto.setAwayTeamId(rs.getLong("away_team_id"));
        dto.setDate(rs.getDate("match_date").toLocalDate());
        dto.setHomeScore(rs.getInt("home_score"));
        dto.setAwayScore(rs.getInt("away_score"));
        return dto;
    };
}
