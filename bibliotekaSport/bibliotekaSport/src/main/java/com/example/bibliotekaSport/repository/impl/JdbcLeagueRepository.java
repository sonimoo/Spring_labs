package com.example.bibliotekaSport.repository.impl;

import com.example.bibliotekaSport.dto.LeagueDto;
import com.example.bibliotekaSport.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcLeagueRepository implements LeagueRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<LeagueDto> findAll() {
        return jdbc.query("SELECT * FROM leagues", mapper);
    }

    @Override
    public Optional<LeagueDto> findById(Long id) {
        return jdbc.query("SELECT * FROM leagues WHERE id = ?", mapper, id).stream().findFirst();
    }

    @Override
    public void save(LeagueDto dto) {
        jdbc.update("INSERT INTO leagues (name, country) VALUES (?, ?)",
                dto.getName(), dto.getCountry());
    }

    @Override
    public void update(Long id, LeagueDto dto) {
        jdbc.update("UPDATE leagues SET name = ?, country = ? WHERE id = ?",
                dto.getName(), dto.getCountry(), id);
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM leagues WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM leagues WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private final RowMapper<LeagueDto> mapper = (rs, i) -> {
        LeagueDto dto = new LeagueDto();
        dto.setId(rs.getLong("id"));
        dto.setName(rs.getString("name"));
        dto.setCountry(rs.getString("country"));
        return dto;
    };
}
