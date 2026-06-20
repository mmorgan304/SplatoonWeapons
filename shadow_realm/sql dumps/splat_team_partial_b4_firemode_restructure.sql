-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: splatoon_3_weapons
-- ------------------------------------------------------
-- Server version	8.4.8

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `special_weapons`
--

LOCK TABLES `special_weapons` WRITE;
/*!40000 ALTER TABLE `special_weapons` DISABLE KEYS */;
INSERT INTO `special_weapons` VALUES (1,'Trizooka'),(2,'Big Bubbler'),(3,'Zipcaster'),(4,'Tenta Missiles'),(5,'Ink Storm'),(6,'Booyah Bomb'),(7,'Kraken Royale'),(8,'Wave Breaker'),(9,'Ink Vac'),(10,'Killer Wail 5.1'),(11,'Inkjet'),(12,'Ultra Stamp'),(13,'Crab Tank'),(14,'Reefslider'),(15,'Triple Inkstrike'),(16,'Tacticooler'),(17,'Super Chump'),(18,'Splattercolor Screen'),(19,'Triple Splashdown');
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
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weapons`
--

LOCK TABLES `weapons` WRITE;
/*!40000 ALTER TABLE `weapons` DISABLE KEYS */;
INSERT INTO `weapons` VALUES (1,'Sploosh-o-matic',4,12,1,8.2,1,190,12,38,19,5,'',NULL,0,NULL,NULL),(2,'Neo Sploosh-o-matic',11,10,1,8.2,1,170,12,38,19,5,'',NULL,0,NULL,NULL),(3,'Splattershot Jr.',1,2,1,11.3,1,180,36,28,14,5,'',NULL,0,NULL,NULL),(4,'Custom Splattershot Jr',13,8,1,11.3,1,190,36,28,14,5,'',NULL,0,NULL,NULL),(5,'Splash-o-matic',3,13,1,12.1,1,210,43,28,14,5,'',NULL,0,NULL,NULL),(6,'Neo Splash-o-matic',2,15,1,12.1,1,200,43,28,14,5,'',NULL,0,NULL,NULL),(7,'Splash-o-matic GCK-O',7,5,1,12.1,1,180,43,28,14,5,'',NULL,0,NULL,NULL),(8,'Aerospray MG',12,14,1,11.3,1,180,36,24,12,4,'',NULL,0,NULL,NULL),(9,'Aerospray RG',10,6,1,11.3,1,190,36,24,12,4,'',NULL,0,NULL,NULL),(10,'Colorz Aerospray',3,18,1,11.3,1,180,36,24,12,4,'',NULL,0,NULL,NULL),(11,'Splattershot',2,1,1,12.9,2,200,52,36,18,6,'',NULL,0,NULL,NULL),(12,'Tentatek Spattershot',1,15,1,12.9,2,190,52,36,18,6,'',NULL,0,NULL,NULL),(13,'Glamorz Splattershot',3,7,1,12.9,2,190,52,36,18,6,'',NULL,0,NULL,NULL),(14,'Hero Shot Replica',2,1,1,12.9,2,100,52,36,18,6,'',NULL,0,NULL,NULL),(15,'Octo Shot Replica',2,1,1,12.9,2,200,52,36,18,6,'',NULL,0,NULL,NULL),(16,'Order Shot Replica',2,1,1,12.9,2,200,52,36,18,6,'',NULL,0,NULL,NULL),(17,'.52 Gal',9,10,1,13.3,2,200,55,52,26,9,'',NULL,0,NULL,NULL),(18,'.52 Gal Deco',4,18,1,13.3,2,190,55,52,26,9,'',NULL,0,NULL,NULL),(19,'N-Zap \'85',2,16,1,12.5,1,200,50,30,15,5,'',NULL,0,NULL,NULL),(20,'N-Zap \'89',5,17,1,12.5,1,170,50,30,15,5,'',NULL,0,NULL,NULL),(21,'Splattershot Pro',14,13,1,17.0,2,190,70,45,23,8,'',NULL,0,NULL,NULL),(22,'Forge Splattershot Pro',2,6,1,17.0,2,200,70,45,23,8,'',NULL,0,NULL,NULL),(23,'Splattershot Pro FRZ-N',1,4,1,17.0,2,190,70,45,23,8,'',NULL,0,NULL,NULL),(24,'.96 Gal',10,9,1,18.0,2,190,74,62,35,12,'',NULL,0,NULL,NULL),(25,'.96 Gal Deco',9,7,1,18.0,2,210,74,62,35,12,'',NULL,0,NULL,NULL),(26,'Clawz .96 Gal',14,16,1,18.0,2,190,74,62,35,12,'',NULL,0,NULL,NULL),(27,'Jet Squelcher',14,9,1,22.5,2,180,82,32,16,8,'',NULL,0,NULL,NULL),(28,'Custom Jet Squelcher',7,5,1,22.5,2,180,82,32,16,8,'',NULL,0,NULL,NULL),(29,'Jet Squelcher COB-R',3,19,1,22.5,2,190,82,32,16,8,'',NULL,0,NULL,NULL),(30,'Splattershot Nova',8,10,1,16.0,2,190,68,24,12,6,'',NULL,0,NULL,NULL),(31,'Annaki Splattershot Nova',6,11,1,16.0,2,210,68,24,12,6,'',NULL,0,NULL,NULL),(32,'Luna Blaster',1,3,8,11.2,1,170,18,125,50,24,'',NULL,1,NULL,NULL),(33,'Luna Blaster Neo',12,12,8,11.2,1,190,18,125,50,24,'',NULL,1,NULL,NULL),(34,'Order Blaster Replica',1,3,8,11.2,1,170,18,125,50,24,'',NULL,1,NULL,NULL),(35,'Blaster',5,2,8,13.3,2,190,27,125,50,24,'',NULL,1,NULL,NULL),(36,'Custom Blaster',8,19,8,13.3,2,190,27,125,50,24,'',NULL,1,NULL,NULL),(37,'Gleamz Blaster',11,13,8,13.3,2,180,27,125,50,24,'',NULL,1,NULL,NULL),(38,'Range Blaster',2,8,8,17.0,2,210,56,125,50,24,'',NULL,1,NULL,NULL),(39,'Custom Range Blaster',1,7,8,17.0,2,210,56,125,50,24,'',NULL,1,NULL,NULL),(40,'Clash Blaster',1,1,8,11.0,1,180,21,60,30,20,'',NULL,0,NULL,NULL),(41,'Clash Blaster Neo',4,17,8,11.0,1,170,21,60,30,20,'',NULL,0,NULL,NULL),(42,'Rapid Blaster',6,15,8,16.7,2,190,62,85,35,35,'',NULL,0,NULL,NULL),(43,'Rapid Blaster Deco',13,11,8,16.7,2,210,62,85,35,35,'',NULL,0,NULL,NULL),(44,'Rapid Blaster Pro',7,9,8,19.2,2,180,72,85,35,40,'',NULL,0,NULL,NULL),(45,'Rapid Blaster Pro Deco',14,10,8,19.2,2,200,72,85,35,40,'',NULL,0,NULL,NULL),(46,'Rapid Blaster Pro WNT-R',2,16,8,19.2,2,200,72,85,35,40,'',NULL,0,NULL,NULL),(47,'S-BLAST \'92',10,14,8,16.5,2,180,45,125,50,24,'',NULL,1,NULL,NULL),(48,'S-BLAST \'91',3,6,8,16.5,2,210,45,125,50,24,'',NULL,1,NULL,NULL),(49,'L-3 Nozzlenose',4,13,1,13.5,2,190,62,93,16,8,'',NULL,0,NULL,NULL),(50,'L-3 Nozzlenose D',3,12,1,13.5,2,200,62,93,16,8,'',NULL,0,NULL,NULL),(51,'Glitterz L-3 Nozzlenose',1,11,1,13.5,2,200,62,93,16,8,'',NULL,0,NULL,NULL),(52,'H-3 Nozzlenose',8,16,1,17.0,2,190,70,135,23,20,'',NULL,1,NULL,NULL),(53,'H-3 Nozzlenose D',9,2,1,17.0,2,200,70,135,23,20,'',NULL,1,NULL,NULL),(54,'H-3 Nozzlenose VIP-R',2,15,1,17.0,2,200,70,135,23,20,'',NULL,1,NULL,NULL),(55,'Squeezer',9,1,1,20.0,2,220,77,38,15,8,'',NULL,0,NULL,NULL),(56,'Foil Squeezer',5,18,1,20.0,2,190,77,38,15,8,'',NULL,0,NULL,NULL),(57,'Carbon Roller',5,3,2,9.5,1,160,20,100,25,10,'',NULL,1,NULL,NULL),(58,'Carbon Roller Deco',3,1,2,9.5,1,190,20,100,25,10,'',NULL,1,NULL,NULL),(59,'Carbon Roller ANG-L',12,17,2,9.5,1,170,20,100,25,10,'',NULL,1,NULL,NULL),(60,'Splat Roller',4,2,2,11.8,2,180,48,150,35,21,'',NULL,1,NULL,NULL),(61,'Krak-On Splat Roller',11,7,2,11.8,2,170,48,150,35,21,'',NULL,1,NULL,NULL),(62,'Order Roller Replica',4,2,2,11.8,2,180,48,150,35,21,'',NULL,1,NULL,NULL),(63,'Dynamo Roller',10,16,2,18.5,3,200,76,180,40,45,'',NULL,1,NULL,NULL),(64,'Gold Dynamo Roller',1,17,2,18.5,3,180,76,180,40,45,'',NULL,1,NULL,NULL),(65,'Starz Dynamo Roller',8,10,2,18.5,3,180,76,180,40,45,'',NULL,1,NULL,NULL),(66,'Flingza Roller',6,4,2,14.0,2,210,58,150,30,19,'',NULL,1,NULL,NULL),(67,'Foil Flingza Roller',2,18,2,14.0,2,200,58,150,30,19,'',NULL,1,NULL,NULL),(68,'Big Swig Roller',9,9,2,12.5,2,180,56,120,40,18,'',NULL,1,NULL,NULL),(69,'Big Swig Roller Express',14,5,2,12.5,2,180,56,120,40,18,'',NULL,1,NULL,NULL),(70,'Planetz Big Swig Roller',13,19,2,12.5,2,180,56,120,40,18,'',NULL,1,NULL,NULL),(71,'Inkbrush',1,10,9,7.0,1,180,5,30,15,6,'',NULL,0,NULL,NULL),(72,'Inkbrush Nouveau',6,12,9,7.0,1,190,5,30,15,8,'',NULL,0,NULL,NULL),(73,'Octobrush',2,3,9,10.5,2,190,23,40,20,10,'',NULL,0,NULL,NULL),(74,'Octobrush Nouveau',11,5,9,10.5,2,180,23,40,20,10,'',NULL,0,NULL,NULL),(75,'Cometz Octobrush',5,7,9,10.5,2,190,23,40,20,10,'',NULL,0,NULL,NULL),(76,'Orderbrush Replica',2,3,9,10.5,2,190,23,40,20,10,'',NULL,0,NULL,NULL),(77,'Painbrush',4,8,9,13.1,2,190,33,60,30,23,'',NULL,0,NULL,NULL),(78,'Painbrush Nouveau',8,4,9,13.1,2,190,33,60,30,23,'',NULL,0,NULL,NULL),(79,'Painbrush BRN-Z',9,1,9,13.1,2,190,33,60,30,23,'',NULL,0,NULL,NULL),(80,'Classic Squiffer',8,2,3,18.5,2,190,75,140,40,45,'',NULL,0,NULL,NULL),(81,'New Squiffer',5,3,3,18.5,2,190,75,140,40,45,'',NULL,1,NULL,NULL),(82,'Splat Charger',1,9,3,26.0,2,190,88,160,40,60,'',NULL,1,NULL,NULL),(83,'Z+F Splat Charger',9,15,3,26.0,2,200,88,160,40,60,'',NULL,1,NULL,NULL),(84,'Splat Charger CAM-O',10,13,3,26.0,2,200,88,160,40,60,'',NULL,1,NULL,NULL),(85,'Order Charger Replica',1,9,3,26.0,2,190,88,160,40,60,'',NULL,1,NULL,NULL),(86,'Splatterscope',1,9,3,26.5,2,190,91,160,40,60,'',NULL,1,NULL,NULL),(87,'Z+F Splatterscope',9,15,3,26.5,2,200,91,160,40,60,'',NULL,1,NULL,NULL),(88,'Splatterscope CAM-O',10,13,3,26.5,2,200,91,160,40,60,'',NULL,1,NULL,NULL),(89,'E-liter 4K',6,8,3,31.0,3,210,96,180,40,92,'',NULL,1,NULL,NULL),(90,'Custom E-liter 4K',11,7,3,31.0,3,210,96,180,40,92,'',NULL,1,NULL,NULL),(91,'E-liter 4K Scope',6,8,3,33.0,3,210,100,180,40,92,'',NULL,1,NULL,NULL),(92,'Custom E-liter 4K Scope',11,7,3,33.0,3,210,100,180,40,92,'',NULL,1,NULL,NULL),(93,'Bamboozler 14 Mk I',5,10,3,21.0,1,190,78,85,30,20,'',NULL,0,NULL,NULL),(94,'Bamboozler 14 Mk II',12,17,3,21.0,1,190,78,85,30,20,'',NULL,0,NULL,NULL),(95,'Goo Tuber',13,4,3,21.0,2,200,78,130,40,71,'',NULL,1,NULL,NULL),(96,'Custom Goo Tuber',12,12,3,21.0,2,200,78,130,40,71,'',NULL,1,NULL,NULL),(97,'Snipewriter 5H',10,16,3,26.0,2,220,91,68,40,72,'',NULL,0,NULL,NULL),(98,'Snipewriter 5B',9,5,3,26.0,2,210,91,68,40,72,'',NULL,0,NULL,NULL),(99,'Slosher',1,15,4,14.5,2,210,58,70,50,29,'',NULL,0,NULL,NULL),(100,'Slosher Deco',14,3,4,14.5,2,180,58,70,50,29,'',NULL,0,NULL,NULL),(101,'Order Slosher Replica',1,15,4,14.5,2,210,58,70,50,29,'',NULL,0,NULL,NULL),(102,'Tri-Slosher',7,11,4,11.0,1,180,31,62,35,23,'',NULL,0,NULL,NULL),(103,'Tri-Slosher Nouveau',12,16,4,11.0,1,200,31,62,35,23,'',NULL,0,NULL,NULL),(104,'Tri-Slosher ASH-N',1,18,4,11.0,1,180,31,62,35,23,'',NULL,0,NULL,NULL),(105,'Sloshing Machine',12,6,4,14.7,2,210,60,76,38,38,'',NULL,0,NULL,NULL),(106,'Sloshing Machine Neo',8,1,4,14.7,2,190,60,38,38,38,'',NULL,0,NULL,NULL),(107,'Bloblobber',10,5,4,15.0,2,190,85,128,32,32,'',NULL,1,NULL,NULL),(108,'Bloblobber Deco',14,7,4,15.0,2,190,85,128,32,32,'',NULL,1,NULL,NULL),(109,'Explosher',8,5,4,20.7,3,200,77,90,35,55,'',NULL,0,NULL,NULL),(110,'Custom Explosher',9,19,4,20.7,3,190,77,90,35,55,'',NULL,0,NULL,NULL),(111,'Dread Wringer',2,14,4,15.5,2,190,60,90,35,45,'',NULL,0,NULL,NULL),(112,'Dread Wringer D',11,8,4,15.5,2,190,60,90,35,45,'',NULL,0,NULL,NULL),(113,'Hornz Dread Wringer',4,13,4,15.5,2,200,60,90,35,45,'',NULL,0,NULL,NULL);
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
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-04 15:22:57
