DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS coaches;
DROP TABLE IF EXISTS leagues;

CREATE TABLE leagues (
                         id INT IDENTITY PRIMARY KEY,
                         name NVARCHAR(100) NOT NULL,
                         country NVARCHAR(100) NOT NULL
);

CREATE TABLE coaches (
                         id INT IDENTITY PRIMARY KEY,
                         name NVARCHAR(100) NOT NULL,
                         nationality NVARCHAR(100) NOT NULL
);

CREATE TABLE teams (
                       id INT IDENTITY PRIMARY KEY,
                       name NVARCHAR(100) NOT NULL,
                       coach_id INT NOT NULL,
                       league_id INT NOT NULL,
                       FOREIGN KEY (coach_id) REFERENCES coaches(id),
                       FOREIGN KEY (league_id) REFERENCES leagues(id)
);

CREATE TABLE players (
                         id INT IDENTITY PRIMARY KEY,
                         name NVARCHAR(100) NOT NULL,
                         position NVARCHAR(50) NOT NULL,
                         team_id INT NOT NULL,
                         FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE matches (
                         id INT IDENTITY PRIMARY KEY,
                         home_team_id INT NOT NULL,
                         away_team_id INT NOT NULL,
                         date DATE NOT NULL,
                         home_score INT,
                         away_score INT,
                         FOREIGN KEY (home_team_id) REFERENCES teams(id),
                         FOREIGN KEY (away_team_id) REFERENCES teams(id)
);
