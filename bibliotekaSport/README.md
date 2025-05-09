
# Лабораторная работа №3. Spring Boot приложение для управления библиотекой спортивных данных

## 1. Введение

В рамках данной лабораторной работы было разработано Spring Boot приложение для управления спортивной информационной системой. Основная задача — создать REST API для работы с данными о командах, игроках, тренерах, матчах и лигах, используя **Spring JDBC** вместо Hibernate и JPA.

Ключевые особенности реализации:
- Использование **Spring JDBC** для работы с базой данных.
- Работа с **DTO-объектами**, полностью отделенными от Entity.
- Реализация **CRUD-операций** для всех сущностей через REST.
- Поддержка **связанных данных** в ответах (например, команда содержит тренера, игроков и т.д.).
- Валидация входных данных и проверка существования внешних ID при создании и обновлении данных.

Проект основан на первой лабораторной работе, где использовались контроллеры и сервисы — они были адаптированы с минимальными изменениями для поддержки JDBC.

---

## 2. Структура проекта

Проект организован по слоистой архитектуре с разделением на контроллеры, сервисы, репозитории и DTO.

### Контроллеры

Контроллеры принимают запросы и передают их соответствующим сервисам. Все данные, передаваемые между слоями, являются DTO.

Пример контроллера для тренера:

```java
package com.example.bibliotekaSport.controller;

import com.example.bibliotekaSport.dto.CoachDto;
import com.example.bibliotekaSport.service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService service;

    @GetMapping
    public List<CoachDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CoachDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CoachDto dto) {
        service.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CoachDto dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

```

### DTO

DTO-классы используются для передачи данных между слоями. 

```java
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
```
### Репозитории

Репозитории выполняют CRUD-операции через JDBC. Репозитории являются интерфейсами, а их реализации используют JdbcTemplate для работы с базой данных.

```java
package com.example.bibliotekaSport.repository;

import com.example.bibliotekaSport.dto.CoachDto;

import java.util.List;
import java.util.Optional;

public interface CoachRepository {
    List<CoachDto> findAll();
    Optional<CoachDto> findById(Long id);
    void save(CoachDto dto);
    void update(Long id, CoachDto dto);
    void delete(Long id);
    boolean existsById(Long id);
}
```
```java
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
     
     // и все остальные методы, обновить удалить и все остальные

    private final RowMapper<CoachDto> mapper = (rs, i) -> {
        CoachDto dto = new CoachDto();
        dto.setId(rs.getLong("id"));
        dto.setName(rs.getString("name"));
        dto.setNationality(rs.getString("nationality"));
        return dto;
    };
}

```

### Сервисы

Сервисы отвечают за бизнес-логику и используют репозитории для выполнения операций с базой данных. Пример интерфейса сервиса:

```java
package com.example.bibliotekaSport.service;

import com.example.bibliotekaSport.dto.CoachDto;

import java.util.List;

public interface CoachService {
    List<CoachDto> getAll();
    CoachDto getById(Long id);
    void create(CoachDto dto);
    void update(Long id, CoachDto dto);
    void delete(Long id);
}
```

```java
package com.example.bibliotekaSport.service.impl;

import com.example.bibliotekaSport.dto.CoachDto;
import com.example.bibliotekaSport.exception.NotFoundException;
import com.example.bibliotekaSport.repository.CoachRepository;
import com.example.bibliotekaSport.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository repository;

    @Override
    public List<CoachDto> getAll() {
        return repository.findAll();
    }

    // и еще несклько

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Coach not found with id " + id);
        repository.delete(id);
    }
}
```


## 3. Описание сущностей и их связей

Приложение работает с пятью основными сущностями:

- **Team (Команда):**
  - Поля: id, name, coach_id, league_id.
  - Связи: один тренер, одна лига, список игроков, список матчей.

- **Player (Игрок):**
  - Поля: id, name, age, team_id.
  - Связь: принадлежит одной команде.

- **Coach (Тренер):**
  - Поля: id, name, experience.
  - Связь: может быть привязан к одной или нескольким командам.

- **League (Лига):**
  - Поля: id, name, country.

- **Match (Матч):**
  - Поля: id, date, home_team_id, away_team_id, score.

---

## 4. Тестирование через insomnia

![alt text](image.png)
![alt text](image-1.png)
![alt text](image-2.png)
![alt text](image-3.png)
![alt text](image-4.png)
![alt text](image-5.png)

##  Выводы

В рамках работы удалось успешно реализовать REST API на Spring Boot с использованием Spring JDBC. Все требуемые сущности поддерживают полные CRUD-операции. DTO-объекты формируются корректно и содержат вложенные связанные данные. Все входные данные валидируются, включая проверку внешних ключей.

Проект является устойчивой основой для дальнейшего развития, например:
- добавление аутентификации и авторизации,
- фильтрация и сортировка данных,
- выгрузка статистики и генерация отчетов.

