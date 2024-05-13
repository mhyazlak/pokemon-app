DELIMITER $$

CREATE FUNCTION `CALC_MAX_HP`(
    IN base_hp INT,
    IN iv_hp INT,
    IN ev_hp INT,
    IN `level` INT
) RETURNS INT
BEGIN
    RETURN FLOOR(0.01 * (2 * base_hp + iv_hp + FLOOR(0.25 * ev_hp)) * `level`) + `level` + 10;
END$$

DELIMITER ;
