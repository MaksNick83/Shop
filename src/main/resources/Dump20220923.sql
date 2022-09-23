-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: shop
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `nomenclatures`
--

DROP TABLE IF EXISTS `nomenclatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nomenclatures` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nomenclatures`
--

LOCK TABLES `nomenclatures` WRITE;
/*!40000 ALTER TABLE `nomenclatures` DISABLE KEYS */;
INSERT INTO `nomenclatures` VALUES (1,'Modem','Super modem',10.1),(2,'Mouse','Super mouse',5),(3,'Keyboard','Super keyboard',6);
/*!40000 ALTER TABLE `nomenclatures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_nomenclatures`
--

DROP TABLE IF EXISTS `order_nomenclatures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_nomenclatures` (
  `order_id` int unsigned NOT NULL,
  `nomenclature_id` int unsigned NOT NULL,
  `count` double DEFAULT '0',
  PRIMARY KEY (`order_id`,`nomenclature_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_nomenclatures`
--

LOCK TABLES `order_nomenclatures` WRITE;
/*!40000 ALTER TABLE `order_nomenclatures` DISABLE KEYS */;
INSERT INTO `order_nomenclatures` VALUES (2,1,6),(2,2,10),(2,3,5),(34,1,6),(34,2,10),(34,3,5),(35,1,12),(35,2,20),(35,3,10),(36,1,24),(36,2,40),(36,3,20),(37,1,48),(37,2,80),(37,3,40),(38,1,96),(38,2,160),(38,3,80),(39,1,192),(39,2,320),(39,3,160),(40,1,384),(40,2,640),(40,3,320),(41,1,768),(41,2,1280),(41,3,640),(42,1,1536),(42,2,2560),(42,3,1280),(43,1,3072),(43,2,5120),(43,3,2560),(44,1,192),(44,2,320),(44,3,160),(45,1,384),(45,2,640),(45,3,320);
/*!40000 ALTER TABLE `order_nomenclatures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numbers_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,'2022-09-21','102'),(34,'2022-09-21','104'),(35,'2022-09-21','104'),(36,'2022-09-21','104'),(37,'2022-09-21','104'),(38,'2022-09-21','104'),(39,'2022-09-20','104'),(40,'2022-09-20','104'),(41,'2022-09-20','104'),(42,'2022-09-20','104'),(43,'2022-09-20','104'),(44,'2022-09-21','104'),(45,'2022-09-21','104');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-23 15:17:58
