CREATE DATABASE IF NOT EXISTS `weather_info`;

USE weather_info;

CREATE TABLE `weather` (
  `id` INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`),
  `location` varchar(255) NOT NULL UNIQUE,
  `temp_c` FLOAT,
  `wind_mph` FLOAT,
  `pressure_mb` FLOAT,
  `humidity_per` INT,
  `condition_text` varchar(255)
  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;