CREATE DATABASE  IF NOT EXISTS `splatoon_3_weapons` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `splatoon_3_weapons`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: splatoon_3_weapons
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inkfish`
--

DROP TABLE IF EXISTS `inkfish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inkfish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `color_chip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inkfish`
--

LOCK TABLES `inkfish` WRITE;
/*!40000 ALTER TABLE `inkfish` DISABLE KEYS */;
INSERT INTO `inkfish` VALUES (1,'Azhdarcho','$2a$12$4o9fl7OV5zmpsYuh1F4J8uy/HiTWu6lONXssxSGndhzdD3AR9ciOC');
/*!40000 ALTER TABLE `inkfish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `special_weapons`
--

DROP TABLE IF EXISTS `special_weapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `special_weapons` (
  `special_weapon_id` int NOT NULL AUTO_INCREMENT,
  `special_weapon_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`special_weapon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special_weapons`
--

LOCK TABLES `special_weapons` WRITE;
/*!40000 ALTER TABLE `special_weapons` DISABLE KEYS */;
INSERT INTO `special_weapons` VALUES (1,'Big Bubbler'),(2,'Booyah Bomb'),(3,'Crab Tank'),(4,'Ink Storm'),(5,'Ink Vac'),(6,'Inkjet'),(7,'Killer Wail 5.1'),(8,'Kraken Royale'),(9,'Reefslider'),(10,'Splattercolor Screen'),(11,'Super Chump'),(12,'Tacticooler'),(13,'Tenta Missiles'),(14,'Triple Inkstrike'),(15,'Triple Splashdown'),(16,'Trizooka'),(17,'Ultra Stamp'),(18,'Wave Breaker'),(19,'Zipcaster'),(20,'Trizooka'),(21,'Big Bubbler'),(22,'Zipcaster'),(23,'Tenta Missiles'),(24,'Ink Storm'),(25,'Booyah Bomb'),(26,'Kraken Royale'),(27,'Wave Breaker'),(28,'Ink Vac'),(29,'Killer Wail 5.1'),(30,'Inkjet'),(31,'Ultra Stamp'),(32,'Crab Tank'),(33,'Reefslider'),(34,'Triple Inkstrike'),(35,'Tacticooler'),(36,'Super Chumps'),(37,'Splattercolor Screen'),(38,'Triple Splashdown');
/*!40000 ALTER TABLE `special_weapons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subweapons`
--

DROP TABLE IF EXISTS `subweapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subweapons` (
  `subweapon_id` int NOT NULL AUTO_INCREMENT,
  `subweapon_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subweapon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subweapons`
--

LOCK TABLES `subweapons` WRITE;
/*!40000 ALTER TABLE `subweapons` DISABLE KEYS */;
INSERT INTO `subweapons` VALUES (1,'Splat Bomb'),(2,'Suction Bomb'),(3,'Burst Bomb'),(4,'Curling Bomb'),(5,'Autobomb'),(6,'Ink Mine'),(7,'Toxic Mist'),(8,'Point Sensor'),(9,'Splash Wall'),(10,'Sprinkler'),(11,'Squid Beakon'),(12,'Fizzy Bomb'),(13,'Torpedo'),(14,'Angle Shooter');
/*!40000 ALTER TABLE `subweapons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weapon_type`
--

DROP TABLE IF EXISTS `weapon_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weapon_type` (
  `weapon_type_id` int NOT NULL AUTO_INCREMENT,
  `weapon_type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`weapon_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weapon_type`
--

LOCK TABLES `weapon_type` WRITE;
/*!40000 ALTER TABLE `weapon_type` DISABLE KEYS */;
INSERT INTO `weapon_type` VALUES (1,'Shooters'),(2,'Rollers'),(3,'Chargers'),(4,'Sloshers'),(5,'Splatlings'),(6,'Dualies'),(7,'Brellas'),(8,'Blasters'),(9,'Brushes'),(10,'Stringers'),(11,'Splatanas');
/*!40000 ALTER TABLE `weapon_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weapons`
--

DROP TABLE IF EXISTS `weapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weapons` (
  `weapon_id` int NOT NULL AUTO_INCREMENT,
  `weapon_name` varchar(45) DEFAULT NULL,
  `subweapon_id` int DEFAULT NULL,
  `special_weapon_id` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `matchmaking_range` decimal(10,1) DEFAULT NULL,
  `weight_id` int DEFAULT NULL,
  `special_points` int DEFAULT NULL,
  `range_actual` int DEFAULT NULL,
  `max_damage` int DEFAULT NULL,
  `min_damage` int DEFAULT NULL,
  `fire_rate` int DEFAULT NULL,
  `secret_name` varchar(50) DEFAULT NULL,
  `avg_paint_per_match` float DEFAULT NULL,
  `has_oneshot` tinyint(1) DEFAULT NULL,
  `damage_direct` int DEFAULT NULL,
  `damage_indirect` int DEFAULT NULL,
  PRIMARY KEY (`weapon_id`),
  KEY `fk_weapons_subweapon` (`subweapon_id`),
  KEY `fk_weapons_specialweapon` (`special_weapon_id`),
  KEY `fk_weapons_weapontype` (`class_id`),
  KEY `fk_weapons_weight` (`weight_id`),
  CONSTRAINT `fk_weapons_specialweapon` FOREIGN KEY (`special_weapon_id`) REFERENCES `special_weapons` (`special_weapon_id`),
  CONSTRAINT `fk_weapons_subweapon` FOREIGN KEY (`subweapon_id`) REFERENCES `subweapons` (`subweapon_id`),
  CONSTRAINT `fk_weapons_weapontype` FOREIGN KEY (`class_id`) REFERENCES `weapon_type` (`weapon_type_id`),
  CONSTRAINT `fk_weapons_weight` FOREIGN KEY (`weight_id`) REFERENCES `weight` (`weight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weapons`
--

LOCK TABLES `weapons` WRITE;
/*!40000 ALTER TABLE `weapons` DISABLE KEYS */;
/*!40000 ALTER TABLE `weapons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weight`
--

DROP TABLE IF EXISTS `weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weight` (
  `weight_id` int NOT NULL,
  `weight_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`weight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weight`
--

LOCK TABLES `weight` WRITE;
/*!40000 ALTER TABLE `weight` DISABLE KEYS */;
INSERT INTO `weight` VALUES (1,'Lightweight'),(2,'Middleweight'),(3,'Heavyweight');
/*!40000 ALTER TABLE `weight` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-30 11:07:51
