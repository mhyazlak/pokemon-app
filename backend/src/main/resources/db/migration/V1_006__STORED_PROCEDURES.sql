DELIMITER $$

CREATE DEFINER=`user`@`%` PROCEDURE`CREATE_USER`(
    IN `p_username` VARCHAR(25),
    IN `p_password` VARCHAR(200)
)
BEGIN
    DECLARE p_new_user_id BIGINT;

    INSERT INTO `User` (`username`, `password`)
    VALUES (p_username, p_password);

    SET p_new_user_id = LAST_INSERT_ID();

    INSERT INTO `Authority` (`user_id`, `authority`)
    VALUES (p_new_user_id, 'USER');

    SELECT p_new_user_id;
END$$

DELIMITER ;

DELIMITER $$

CREATE DEFINER=`user`@`%` PROCEDURE `CREATE_TEAM`(
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
