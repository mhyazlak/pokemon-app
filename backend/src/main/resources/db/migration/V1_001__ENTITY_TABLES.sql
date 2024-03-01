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

CREATE TABLE Move (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    power INT,
    accuracy INT
);

CREATE TABLE `User` (
  id BIGINT AUTO_INCREMENT NOT NULL,
  username VARCHAR(25) NOT NULL,
  password VARCHAR(200) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE `Authority` (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  authority VARCHAR(25) NOT NULL
);

CREATE TABLE `Team` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `selected` bit NOT NULL default 0,
    CONSTRAINT `fk_user_team` FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

CREATE TABLE `TeamMember` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `team_id` BIGINT NOT NULL,
    `pokemon_id` BIGINT NOT NULL,
    `valid` BIT NOT NULL,
    CONSTRAINT `fk_team_teammember` FOREIGN KEY (`team_id`) REFERENCES `Team`(`id`),
    CONSTRAINT `fk_pokemon_teammember` FOREIGN KEY (`pokemon_id`) REFERENCES `Pokemon`(`id`)
);

CREATE TABLE Moveset (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `member_id` BIGINT NOT NULL,
    `move_id` INT NOT NULL,
    CONSTRAINT `fk_member_moveset` FOREIGN KEY (`member_id`) REFERENCES `TeamMember`(`id`),
    CONSTRAINT `fk_move_moveset` FOREIGN KEY (`move_id`) REFERENCES `Move`(`id`)
);

