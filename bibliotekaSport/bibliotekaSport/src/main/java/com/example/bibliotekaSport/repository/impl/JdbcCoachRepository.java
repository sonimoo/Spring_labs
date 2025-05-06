package com.example.bibliotekaSport.repository.impl;

import com.example.bibliotekaSport.dto.CoachDto;
import com.example.bibliotekaSport.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcCoachRepository implements CoachRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<CoachDto> findAll() {
        return jdbc.query("SELECT * FROM coaches", mapper);
    }

    @Override
    public Optional<CoachDto> findById(Long id) {
        return jdbc.query("SELECT * FROM coaches WHERE id = ?", mapper, id).stream().findFirst();
    }

    @Override
    public void save(CoachDto dto) {
        jdbc.update("INSERT INTO coaches (name, nationality) VALUES (?, ?)",
                dto.getName(), dto.getNationality());
    }

    @Override
    public void update(Long id, CoachDto dto) {
        jdbc.update("UPDATE coaches SET name = ?, nationality = ? WHERE id = ?",
                dto.getName(), dto.getNationality(), id);
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM coaches WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM coaches WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    private final RowMapper<CoachDto> mapper = (rs, i) -> {
        CoachDto dto = new CoachDto();
        dto.setId(rs.getLong("id"));
        dto.setName(rs.getString("name"));
        dto.setNationality(rs.getString("nationality"));
        return dto;
    };
}
