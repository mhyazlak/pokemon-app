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
