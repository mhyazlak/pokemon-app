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

INSERT INTO `User` (username, password) VALUES ('Hakan', 'password');
INSERT INTO `User` (username, password) VALUES ('Mehmet', 'password');
INSERT INTO `Authority` (user_id, authority) VALUES (1, 'USER');
INSERT INTO `Authority` (user_id, authority) VALUES (2, 'ADMIN');