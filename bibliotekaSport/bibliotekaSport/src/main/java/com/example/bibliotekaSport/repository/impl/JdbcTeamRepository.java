package com.example.bibliotekaSport.repository.impl;

import com.example.bibliotekaSport.dto.TeamDto;
import com.example.bibliotekaSport.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTeamRepository implements TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TeamDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM teams", teamMapper);
    }

    @Override
    public Optional<TeamDto> findById(Long id) {
        List<TeamDto> result = jdbcTemplate.query("SELECT * FROM teams WHERE id = ?", teamMapper, id);
        return result.stream().findFirst();
    }

    @Override
    public void save(TeamDto team) {
        jdbcTemplate.update(
                "INSERT INTO teams (name, coach_id, league_id) VALUES (?, ?, ?)",
                team.getName(), team.getCoachId(), team.getLeagueId()
        );
    }

    @Override
    public void update(Long id, TeamDto team) {
        jdbcTemplate.update(
                "UPDATE teams SET name = ?, coach_id = ?, league_id = ? WHERE id = ?",
                team.getName(), team.getCoachId(), team.getLeagueId(), id
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM teams WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM teams WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private final RowMapper<TeamDto> teamMapper = new RowMapper<>() {
        @Override
        public TeamDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            TeamDto team = new TeamDto();
            team.setId(rs.getLong("id"));
            team.setName(rs.getString("name"));
            team.setCoachId(rs.getLong("coach_id"));
            team.setLeagueId(rs.getLong("league_id"));
            return team;
        }
    };
}
