package com.example.bibliotekaSport.repository.impl;

import com.example.bibliotekaSport.dto.PlayerDto;
import com.example.bibliotekaSport.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcPlayerRepository implements PlayerRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<PlayerDto> findAll() {
        return jdbc.query(
                "SELECT p.id, p.name, p.position, tp.team_id " +
                        "FROM players p " +
                        "LEFT JOIN team_players tp ON p.id = tp.player_id",
                mapper
        );
    }

    @Override
    public Optional<PlayerDto> findById(Long id) {
        return jdbc.query(
                "SELECT p.id, p.name, p.position, tp.team_id " +
                        "FROM players p " +
                        "LEFT JOIN team_players tp ON p.id = tp.player_id " +
                        "WHERE p.id = ?",
                mapper,
                id
        ).stream().findFirst();
    }

    @Override
    public void save(PlayerDto dto) {
        jdbc.update("INSERT INTO players (name, position) VALUES (?, ?)", dto.getName(), dto.getPosition());
        Long playerId = jdbc.queryForObject("SELECT MAX(id) FROM players", Long.class);
        jdbc.update("INSERT INTO team_players (team_id, player_id) VALUES (?, ?)", dto.getTeamId(), playerId);
    }

    @Override
    public void update(Long id, PlayerDto dto) {
        jdbc.update("UPDATE players SET name = ?, position = ? WHERE id = ?", dto.getName(), dto.getPosition(), id);
        jdbc.update("UPDATE team_players SET team_id = ? WHERE player_id = ?", dto.getTeamId(), id);
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM team_players WHERE player_id = ?", id);
        jdbc.update("DELETE FROM players WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM players WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private final RowMapper<PlayerDto> mapper = (rs, i) -> {
        PlayerDto dto = new PlayerDto();
        dto.setId(rs.getLong("id"));
        dto.setName(rs.getString("name"));
        dto.setPosition(rs.getString("position"));
        dto.setTeamId(rs.getLong("team_id")); // nullable (LEFT JOIN)
        return dto;
    };
}
