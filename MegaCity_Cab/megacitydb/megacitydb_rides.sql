CREATE DATABASE  IF NOT EXISTS `megacitydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `megacitydb`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: megacitydb
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `rides`
--

DROP TABLE IF EXISTS `rides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rides` (
  `ride_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `pickup_location` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `scheduled_time` timestamp NULL DEFAULT NULL,
  `deadline_time` timestamp NULL DEFAULT NULL,
  `booked_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(50) DEFAULT 'pending',
  `assigned_rider_id` int DEFAULT NULL,
  `distance` decimal(10,2) NOT NULL,
  `cost` decimal(10,2) NOT NULL,
  `selected_vehicle` varchar(20) NOT NULL,
  PRIMARY KEY (`ride_id`),
  KEY `idx_assigned_rider` (`assigned_rider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rides`
--

LOCK TABLES `rides` WRITE;
/*!40000 ALTER TABLE `rides` DISABLE KEYS */;
INSERT INTO `rides` VALUES (1,1,'colombo fort','kandy','2025-02-25 07:30:00','2025-02-25 06:30:00','2025-02-23 21:08:02','COMPLETED',2,0.00,0.00,'CAR'),(3,1,'colombo fort','kandy','2025-02-28 06:31:00','2025-02-28 05:31:00','2025-02-23 21:51:23','COMPLETED',1,0.00,0.00,'CAR'),(4,1,'colombo fort','anuradhapura','2025-03-01 01:34:00',NULL,'2025-02-26 18:59:53','assigned',2,0.00,0.00,'CAR'),(5,1,'colombo fort','anuradhapura','2025-03-08 06:00:00',NULL,'2025-02-26 19:26:26','assigned',2,0.00,0.00,'CAR'),(6,2,'colombo fort','anuradhapura','2025-03-01 01:34:00',NULL,'2025-02-26 19:27:48','COMPLETED',3,0.00,0.00,'CAR'),(7,1,'colombo fort','anuradhapura','2025-03-04 02:08:00',NULL,'2025-02-26 19:35:02','COMPLETED',3,0.00,0.00,'CAR'),(8,1,'colombo fort','anuradhapura','2025-02-27 14:10:00',NULL,'2025-02-26 19:40:17','COMPLETED',3,0.00,0.00,'CAR'),(9,1,'colombo fort','kurunegala','2025-03-14 22:00:00',NULL,'2025-02-26 22:30:52','COMPLETED',3,0.00,0.00,'CAR'),(10,1,'colombo fort','polonnaruwa','2025-04-02 04:00:00',NULL,'2025-03-06 08:38:27','assigned',3,120.00,0.00,'CAR'),(11,6,'colombo fort','abanpola','2025-03-24 23:00:00',NULL,'2025-03-07 05:22:58','assigned',2,118.00,0.00,'CAR'),(12,6,'colombo fort','abanpola','2025-03-11 00:00:00',NULL,'2025-03-07 05:31:45','COMPLETED',1,118.00,0.00,'CAR'),(13,1,'colombo fort','dambulla','2025-03-29 00:30:00',NULL,'2025-03-07 05:49:14','COMPLETED',1,110.00,4951.00,'CAR'),(14,6,'colombo fort','dambulla','2025-03-18 03:09:00','2025-03-18 02:09:00','2025-03-07 08:39:13','ASSIGNED',4,110.00,3950.00,'THREE_WHEEL'),(15,1,'colombo fort','giriulla','2025-03-07 22:00:00','2025-03-07 21:00:00','2025-03-08 01:13:07','ASSIGNED',4,17.00,695.00,'THREE_WHEEL'),(16,1,'colombo fort','giriulla','2025-03-28 23:00:00','2025-03-28 22:00:00','2025-03-08 01:26:55','ASSIGNED',4,17.00,695.00,'THREE_WHEEL'),(17,1,'colombo fort','giriulla','2025-03-20 23:00:00','2025-03-20 22:00:00','2025-03-09 05:17:52','ASSIGNED',5,17.00,1120.00,'VAN'),(26,17,'bjsjgdjh','anuradhapura','2025-04-02 13:11:00','2025-04-02 12:11:00','2025-03-13 18:41:42','ASSIGNED',1,17.00,865.00,'CAR'),(27,1,'galle','colombo','2025-03-17 21:00:00','2025-03-17 20:00:00','2025-03-14 03:15:17','REQUESTED',NULL,14.00,730.00,'CAR'),(28,1,'amapara','colombo','2025-03-18 22:00:00','2025-03-18 21:00:00','2025-03-14 03:38:05','COMPLETED',6,98.00,3530.00,'THREE_WHEEL');
/*!40000 ALTER TABLE `rides` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-14 13:11:10
