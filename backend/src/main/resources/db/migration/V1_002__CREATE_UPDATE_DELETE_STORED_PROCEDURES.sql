DELIMITER $$

CREATE PROCEDURE`CREATE_USER`(
    IN `p_username` VARCHAR(25),
    IN `p_password` VARCHAR(200),
    IN `p_email` VARCHAR(100),
    IN `p_alias_name` VARCHAR(40)
)
BEGIN
    DECLARE p_new_user_id BIGINT;

    INSERT INTO `User` (username, password, email, alias_name)
    VALUES (p_username, p_password, p_email, p_alias_name);

    SET p_new_user_id = LAST_INSERT_ID();

    INSERT INTO `Permission` (user_id, trainer, admin)
    VALUES (p_new_user_id, 1, 0);

    SELECT p_new_user_id;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE `CREATE_TEAM`(
    IN `p_team_name` VARCHAR(25),
	IN `p_user_id` BIGINT
)
BEGIN
	DECLARE p_new_team_id BIGINT;

    INSERT INTO `Team` (`name`, `user_id`)
    VALUES (`p_team_name`,`p_user_id`);
    SET p_new_team_id = LAST_INSERT_ID();
    SELECT p_new_team_id;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE `CREATE_TEAMMEMBER`(
    IN `p_team_id` BIGINT,
    IN `p_pokemon_id` BIGINT,
    IN `p_level` INT,
    IN `p_iv_hp` INT, IN `p_ev_hp` INT,
    IN `p_iv_atk` INT, IN `p_ev_atk` INT,
    IN `p_iv_def` INT, IN `p_ev_def` INT,
    IN `p_iv_spa` INT, IN `p_ev_spa` INT,
    IN `p_iv_spd` INT, IN `p_ev_spd` INT,
    IN `p_iv_speed` INT, IN `p_ev_speed` INT,
    IN `p_nature_id` BIGINT
)
BEGIN
    DECLARE v_base_hp INT;
    DECLARE v_base_atk INT;
    DECLARE v_base_def INT;
    DECLARE v_base_spa INT;
    DECLARE v_base_spd INT;
    DECLARE v_base_speed INT;
    DECLARE v_calc_hp INT;
    DECLARE v_calc_atk INT;
    DECLARE v_calc_def INT;
    DECLARE v_calc_spa INT;
    DECLARE v_calc_spd INT;
    DECLARE v_calc_speed INT;

    SELECT p.base_hp, p.base_atk, p.base_def, p.base_spa, p.base_spd, p.base_speed
    INTO v_base_hp, v_base_atk, v_base_def, v_base_spa, v_base_spd, v_base_speed
    FROM Pokemon p
    WHERE p.id = p_pokemon_id;

    SET v_calc_hp = ((2 * v_base_hp + p_iv_hp + p_ev_hp / 4) * p_level / 100) + p_level + 10;
    SET v_calc_atk = ((2 * v_base_atk + p_iv_atk + p_ev_atk / 4) * p_level / 100) + 5;
    SET v_calc_def = ((2 * v_base_def + p_iv_def + p_ev_def / 4) * p_level / 100) + 5;
    SET v_calc_spa = ((2 * v_base_spa + p_iv_spa + p_ev_spa / 4) * p_level / 100) + 5;
    SET v_calc_spd = ((2 * v_base_spd + p_iv_spd + p_ev_spd / 4) * p_level / 100) + 5;
    SET v_calc_speed = ((2 * v_base_speed + p_iv_speed + p_ev_speed / 4) * p_level / 100) + 5;

    INSERT INTO `TeamMember` (
        team_id,
        pokemon_id,
        max_hp,
        calc_atk,
        calc_def,
        calc_spa,
        calc_spd,
        calc_speed,
        iv_hp,
        iv_atk,
        iv_def,
        iv_spa,
        iv_spd,
        iv_speed,
        ev_hp,
        ev_atk,
        ev_def,
        ev_spa,
        ev_spd,
        ev_speed,
        nature_id,
        valid
    )
    VALUES (
        p_team_id,
        p_pokemon_id,
        v_calc_hp,
        v_calc_atk,
        v_calc_def,
        v_calc_spa,
        v_calc_spd,
        v_calc_speed,
        p_iv_hp,
        p_iv_atk,
        p_iv_def,
        p_iv_spa,
        p_iv_spd,
        p_iv_speed,
        p_ev_hp,
        p_ev_atk,
        p_ev_def,
        p_ev_spa,
        p_ev_spd,
        p_ev_speed,
        p_nature_id,
        1
    );
    
   SELECT LAST_INSERT_ID();

END$$

DELIMITER ;