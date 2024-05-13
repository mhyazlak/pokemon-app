CREATE TABLE `User` (
	id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	username VARCHAR(25) NOT NULL,
	password VARCHAR(200) NOT NULL,
    email VARCHAR(100) NOT NULL,
    alias_name VARCHAR(40) NOT NULL
);

CREATE TABLE `Permission` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id BIGINT,
	trainer bit,
    admin bit
);

CREATE TABLE `Pokemon` (
	id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	name VARCHAR(25) NOT NULL,
	type_one ENUM(
	    'NORMAL',
	    'FIRE',
	    'WATER',
	    'ELECTRIC',
	    'GRASS',
	    'ICE',
	    'FIGHTING',
	    'POISON',
	    'GROUND',
	    'FLYING',
	    'PSYCHIC',
	    'BUG',
	    'ROCK',
	    'GHOST',
	    'DARK',
	    'DRAGON',
	    'STEEL',
	    'FAIRY'
  ),
  type_two ENUM(
		'NORMAL',
		'FIRE',
		'WATER',
		'ELECTRIC',
		'GRASS',
		'ICE',
		'FIGHTING',
		'POISON',
		'GROUND',
		'FLYING',
		'PSYCHIC',
		'BUG',
		'ROCK',
		'GHOST',
		'DARK',
		'DRAGON',
		'STEEL',
		'FAIRY'
	) NULL,
	base_hp INT NOT NULL,
	base_atk INT NOT NULL,
	base_def INT NOT NULL,
	base_spa INT NOT NULL,
	base_spd INT NOT NULL,
	base_speed INT NOT NULL,
	front_sprite VARCHAR(200) NOT NULL,
	back_sprite VARCHAR(200) NOT NULL,
	icon_b64 TEXT
);


#DROP TABLE IF EXISTS `Move`;
CREATE TABLE `Move` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type ENUM(
		'NORMAL',
		'FIRE',
		'WATER',
		'ELECTRIC',
		'GRASS',
		'ICE',
		'FIGHTING',
		'POISON',
		'GROUND',
		'FLYING',
		'PSYCHIC',
		'BUG',
		'ROCK',
		'GHOST',
		'DARK',
		'DRAGON',
		'STEEL',
		'FAIRY'
  	),
    power INT,
    accuracy INT,
    damage_class ENUM(
    	'PHYSICAL', 
    	'SPECIAL',
    	'STATUS'
	)
);

#DROP TABLE IF EXISTS `EvolutionChain`;
CREATE TABLE `EvolutionChain` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pokemon_id BIGINT NOT NULL, 
    stage INT NOT NULL,
    stage_one_id  BIGINT NOT NULL,
    stage_two_id BIGINT,
    stage_three_id BIGINT,
    triggered_by VARCHAR(255),
    CONSTRAINT `fk_pokemon_evo_chain` FOREIGN KEY (`pokemon_id`) REFERENCES `Pokemon`(`id`),
    CONSTRAINT `fk_stage_one_evo_chain` FOREIGN KEY (`stage_one_id`) REFERENCES `Pokemon`(`id`),
    CONSTRAINT `fk_stage_two_evo_chain` FOREIGN KEY (`stage_two_id`) REFERENCES `Pokemon`(`id`),
    CONSTRAINT `fk_stage_three_evo_chain` FOREIGN KEY (`stage_three_id`) REFERENCES `Pokemon`(`id`)
);

CREATE TABLE `Nature` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(20) NOT NULL,
    atk_mod DECIMAL(2,1) NOT NULL,
    def_mod DECIMAL(2,1) NOT NULL,
    spa_mod DECIMAL(2,1) NOT NULL,
    spd_mod DECIMAL(2,1) NOT NULL,
    speed_mod DECIMAL(2,1) NOT NULL
);

#DROP TABLE IF EXISTS `Team`;
CREATE TABLE `Team` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    selected bit NOT NULL default 0,
    CONSTRAINT `fk_user_team` FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

#DROP TABLE IF EXISTS `TeamMember`;
CREATE TABLE `TeamMember` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    team_id BIGINT NOT NULL,
    pokemon_id BIGINT NOT NULL,
    max_hp INT NOT NULL,
    calc_atk INT NOT NULL,
    calc_def INT NOT NULL,
    calc_spa INT NOT NULL,
    calc_spd INT NOT NULL,
    calc_speed INT NOT NULL,
    iv_hp INT NOT NULL,
    iv_atk INT NOT NULL,
    iv_def INT NOT NULL,
    iv_spa INT NOT NULL,
    iv_spd INT NOT NUll,
    iv_speed INT NOT NULL,
    ev_hp INT NOT NULL,
    ev_atk INT NOT NULL,
    ev_def INT NOT NULL,
    ev_spa INT NOT NULL,
    ev_spd INT NOT NUll,
    ev_speed INT NOT NULL,
    nature_id BIGINT NOT NULL,
    `selected` BIT NOT NULL DEFAULT 0,
    valid BIT NOT NULL DEFAULT 1,
    CONSTRAINT `fk_team_teammember` FOREIGN KEY (`team_id`) REFERENCES `Team`(`id`),
    CONSTRAINT `fk_pokemon_teammember` FOREIGN KEY (`pokemon_id`) REFERENCES `Pokemon`(`id`),
    CONSTRAINT `fk_nature_teammember` FOREIGN KEY (`nature_id`) REFERENCES `Nature`(`id`)
);

#DROP TABLE IF EXISTS `Moveset`;
CREATE TABLE `Moveset` (
    `id` BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `member_id` BIGINT NOT NULL,
    `move_id` BIGINT NOT NULL,
    CONSTRAINT `fk_member_moveset` FOREIGN KEY (`member_id`) REFERENCES `TeamMember`(`id`),
    CONSTRAINT `fk_move_moveset` FOREIGN KEY (`move_id`) REFERENCES `Move`(`id`)
);


#DROP TABLE IF EXISTS `Gamestate`;
CREATE TABLE `Gamestate` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY
);

#DROP TABLE IF EXISTS `Match`;
CREATE TABLE `Match` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY
);

#DROP TABLE IF EXISTS `PlayerAction`;
CREATE TABLE `PlayerAction` (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY
);