-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Admin` (
                                              `idAdmin` INT NOT NULL AUTO_INCREMENT,
                                              `firstName` VARCHAR(45) NULL,
    `LastName` VARCHAR(45) NULL,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idAdmin`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Book` (
                                             `idBook` INT NOT NULL AUTO_INCREMENT,
                                             `title` VARCHAR(45) NOT NULL,
    `author` VARCHAR(45) NULL,
    `ISBN` VARCHAR(45) NULL,
    `publication` DATE NULL,
    `avatar` VARCHAR(200) NULL,
    PRIMARY KEY (`idBook`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Student` (
    `cne` VARCHAR(15) NOT NULL,
    `fullName` VARCHAR(100) NULL,
    `email` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`cne`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Reservation` (
                                                    `idBook` INT NOT NULL,
                                                    `cne` VARCHAR(15) NOT NULL,
    `reservation` DATE NULL,
    `return` DATE NULL,
    INDEX `fk_Reservation_Book_idx` (`idBook` ASC) VISIBLE,
    INDEX `fk_Reservation_Student1_idx` (`cne` ASC) VISIBLE,
    CONSTRAINT `fk_Reservation_Book`
    FOREIGN KEY (`idBook`)
    REFERENCES `mydb`.`Book` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_Reservation_Student1`
    FOREIGN KEY (`cne`)
    REFERENCES `mydb`.`Student` (`cne`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

select * from Student;