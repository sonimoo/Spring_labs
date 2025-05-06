# Лабораторная №3: Spring Boot приложение для управления спортивной библиотекой (JDBC-реализация)

## Введение

Данный проект представляет собой полнофункциональное RESTful веб-приложение, разработанное на основе Spring Boot. Основное назначение — управление спортивными объектами: командами, игроками, тренерами, лигами и матчами. Вся логика доступа к данным реализована через Spring JDBC (JdbcTemplate), без использования ORM-инструментов. Приложение строго следует принципам многослойной архитектуры: контроллеры, сервисы, DAO и DTO. Все операции с базой данных выполняются вручную через SQL-запросы.

## Цели работы

- Продемонстрировать реализацию CRUD-приложения с использованием Spring Boot и JDBC.
- Реализовать взаимодействие с СУБД MSSQL Server без ORM (Hibernate, JPA).
- Организовать архитектуру проекта по слоям (Controller, Service, Repository).
- Использовать DTO-объекты для передачи данных между слоями.
- Обеспечить корректную обработку ошибок и валидацию входных данных.
- Проверить работу API через визуальный интерфейс Swagger UI.

## Используемые технологии

- Java 17+
- Spring Boot 3.2.5
- Spring Web (REST API)
- Spring JDBC
- Lombok
- Swagger (springdoc-openapi)
- MSSQL Server
- Maven
- IntelliJ IDEA Ultimate

## Архитектура проекта

### Структура слоёв

- **Controller** — REST-контроллеры для всех сущностей.
- **Service/ServiceImpl** — бизнес-логика, валидации, связывание данных.
- **Repository/Impl** — DAO-интерфейсы и реализации с использованием JdbcTemplate.
- **DTO** — классы передачи данных, отображают как входные данные (POST/PUT), так и выходные (GET).
- **Exception** — кастомные исключения (`NotFoundException`), применяемые при отсутствии нужных записей.

### Полная структура каталогов

```text
src/main/java/com/example/bibliotekaSport
├── controller         // REST-контроллеры
├── dto                // DTO-классы всех сущностей
├── exception          // Исключения
├── repository         // DAO-интерфейсы
│   └── impl           // Реализации DAO через JdbcTemplate
├── service            // Интерфейсы бизнес-логики
│   └── impl           // Реализации бизнес-логики
└── BibliotekaSportApplication.java  // Главный класс запуска

src/main/resources
├── application.properties  // Настройки подключения к базе
└── schema.sql              // SQL-структура таблиц (DDL)
```

## Сущности и связи между ними

- **League**: уникальный идентификатор, название, страна. Может содержать множество команд.
- **Coach**: уникальный идентификатор, имя и стаж. Один тренер закреплён за одной командой.
- **Team**: содержит ссылку на `coachId`, `leagueId`, список `playerIds`. Содержит также список матчей, в которых участвовала команда.
- **Player**: принадлежит одной команде, хранит имя и игровую позицию.
- **Match**: фиксирует результат матча между двумя командами с датой, счётом и ID обеих команд.

Все связи реализованы через внешние ключи. DTO при `GET` возвращают связанные сущности, при `POST`/`PUT` принимаются только их ID.

## Пример DTO: TeamDto

```java
package com.example.bibliotekaSport.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;

    @NotBlank(message = "Team name must not be blank")
    private String name;

    @NotNull(message = "Coach ID is required")
    private Long coachId;

    @NotNull(message = "League ID is required")
    private Long leagueId;

    @NotNull(message = "Player IDs are required")
    private List<Long> playerIds;

    // Расширенные поля для ответа (GET)
    private CoachDto coach;
    private LeagueDto league;
    private List<PlayerDto> players;
    private List<MatchDto> matches;
}
```

## Валидация и обработка ошибок

- Применены аннотации: `@NotBlank`, `@NotNull`, `@Size`, `@Min`, `@Max` в DTO.
- Перед сохранением или обновлением данных осуществляется проверка на существование переданных ID.
- При нарушении условий генерируется `NotFoundException`, который автоматически обрабатывается Spring Boot и возвращает статус `404` с сообщением.

## Swagger UI и тестирование API

Для визуального тестирования всех HTTP-запросов используется Swagger UI:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Примеры операций:

- `GET /api/players` — получить список всех игроков
- `POST /api/teams` — создать команду с привязкой к тренеру, лиге и списку игроков
- `PUT /api/matches/{id}` — обновить матч
- `DELETE /api/leagues/{id}` — удалить лигу

## Примеры данных для ручной вставки в MSSQL

```sql
INSERT INTO leagues (name, country) VALUES
('Premier League', 'England'),
('La Liga', 'Spain'),
('Serie A', 'Italy'),
('Bundesliga', 'Germany'),
('Ligue 1', 'France');

INSERT INTO coaches (name, experience) VALUES
('Pep Guardiola', 20),
('Carlo Ancelotti', 25),
('Xavi Hernandez', 5),
('Luis Enrique', 18),
('Jurgen Klopp', 23);
```

## Ответы на вопросы

**Что означает ошибка 500 (Internal Server Error) при работе с API?**  
Это означает, что на сервере произошло необработанное исключение. В контексте данной лабораторной чаще всего:

- Отсутствует нужная запись (например, `teamId`, `coachId`, `leagueId`), но нет корректной обработки в сервисе — необходимо использовать `existsById()` и выбрасывать `NotFoundException`.
- Ошибки преобразования типов (например, `null` вместо `int`, `String` вместо `Long`).
- Неверная дата (например, `date` в формате, который не распарсится).

Такие ошибки можно избежать с помощью валидации входных данных и обязательной проверки существования всех зависимостей до выполнения SQL-запросов.
 преподавателя

**Можно ли создавать вложенные сущности при создании команды?**  
Нет. По условиям лабораторной необходимо передавать только ID уже существующих сущностей (например, coachId).

**Как в DTO отображаются связанные сущности?**  
В `GET`-ответах DTO содержат развёрнутые сущности (`CoachDto`, `LeagueDto`, и т.д.). В `POST` и `PUT` используются только ID.

**Почему не используются Entity-классы?**  
Потому что запрещено использовать JPA и Hibernate. Вместо этого вся логика реализована вручную через DAO и DTO.

**Где реализована проверка существования coachId/leagueId и т.п.?**  
В слоях `ServiceImpl`, например, `TeamServiceImpl` вызывает метод `existsById` перед созданием команды.

**Каким образом проверялась работа приложения?**  
Через Swagger UI. Тестировались все 5 CRUD-функций для каждой сущности. Также выполнялись SQL-запросы напрямую в SSMS для проверки результатов.

**Что происходит при попытке привязать команду к несуществующему тренеру?**  
Возвращается `404 Not Found` с описанием ошибки через `NotFoundException`.

## Вывод

Лабораторная работа успешно продемонстрировала применение Spring Boot и Spring JDBC в проектировании и реализации REST API. Все сущности были корректно связаны, реализована работа с DTO, обеспечена ручная валидация и тестирование через Swagger. Проект выполнен в полном соответствии с методическими указаниями и целями лабораторной работы.
