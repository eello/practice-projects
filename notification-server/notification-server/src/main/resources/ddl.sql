CREATE DATABASE IF NOT EXISTS notification CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

USE notification;

CREATE TABLE IF NOT EXISTS `notification`.`noti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `body` TEXT(65535) NOT NULL,
  `requestAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`)) ENGINE = InnoDB;