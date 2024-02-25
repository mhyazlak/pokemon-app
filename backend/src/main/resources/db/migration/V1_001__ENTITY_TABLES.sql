CREATE TABLE Pokemon (
    id                    BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name                  VARCHAR(25) NOT NULL,
    type_one              ENUM(
                              'NORMAL', 'FIRE', 'WATER', 'ELECTRIC', 'GRASS', 'ICE',
                              'FIGHTING', 'POISON', 'GROUND', 'FLYING', 'PSYCHIC', 'BUG',
                              'ROCK', 'GHOST', 'DARK', 'DRAGON', 'STEEL', 'FAIRY'
                           ),
    type_two              ENUM(
                              'NORMAL', 'FIRE', 'WATER', 'ELECTRIC', 'GRASS', 'ICE',
                              'FIGHTING', 'POISON', 'GROUND', 'FLYING', 'PSYCHIC', 'BUG',
                              'ROCK', 'GHOST', 'DARK', 'DRAGON', 'STEEL', 'FAIRY'
                           ) NULL,
    base_hp               INT NOT NULL,
    base_atk              INT NOT NULL,
    base_def              INT NOT NULL,
    base_spa              INT NOT NULL,
    base_spd              INT NOT NULL,
    base_spe              INT NOT NULL,
    sprite                VARCHAR(200) NOT NULL,
    icon_b64 TEXT
);

CREATE TABLE EvolutionChain (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pokemon_name VARCHAR(255) NOT NULL,
    stage INT NOT NULL,
    stage_one VARCHAR(255) NOT NULL,
    stage_two VARCHAR(255),
    stage_three VARCHAR(255),
    triggered_by VARCHAR(255)
);

