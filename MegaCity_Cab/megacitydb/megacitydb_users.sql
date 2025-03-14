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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `nic` varchar(20) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('user','rider','admin') NOT NULL DEFAULT 'user',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nic` (`nic`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_users_role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'hashini bhagya','2001','0713652498','hashini@gmail.com','$2a$10$DtdgD0kgOPkbt4wEhb4XyeGFVooDiriuYCq8LKME7SBpC5PDdYwlK','user'),(2,'visitha nirmal','200123','0776546213','visitha@gmail.com','$2a$10$fVOQnGAty84VZrrLQNTpRecCe/x3UYr1K9y0LkNsvTsFa4VrIOchy','rider'),(3,'Harsha lakshan','200228102403','0710379650','harshalakshan546@gmail.com','$2a$10$kLT8ecq8q8W5qR7qMunfquV1zj8fCVBGFSDcKU5dH4leazpcx9kMm','admin'),(5,'roshan','12356846','071956834','roshan@gmail.com','$2a$10$0Ay5IuqJjM96e2Ueswv9FuzdX1IawGvBoyw9Zyob74H7mDuSbY1aO','rider'),(7,'harsha lakshan','235678913','07189564236','harsha@rider.com','$2a$10$gzb0BD9w.WSeK5C1o136Juh2jLBJEe2GJNFizINY1bsdxSPLWhyLy','rider'),(8,'hashini','116481203262','0751142664','hashini@rider.com','$2a$10$6f1vx4Ce3GM3M7fyS2uH9O4GI7l0Uu3arU2xAQU99abJLYAUwcsci','rider'),(9,'bashi','33346463789415','0712345525','bash@gmail.com','$2a$10$xvBS9imvZ.L/yFIcxIiTmeqmZT0cxKWDIpM7PFL8yUbLJKRnpL5OC','rider'),(11,'bashi1','18549612','071131237411','bashi@gmail.com','$2a$10$vvrjqny346wRuiESxQ1y4O7pRnWEDNAEWy9rTSmvygzfxx1fMNZQ.','rider'),(12,'roshan','6156449','645132841521','roshan@user.com','$2a$10$Q86VjvSPdSFrAB9/jKIX0.zZkdpyKlhaInm058Kgw8If5Su0sD9u6','user'),(13,'deshan','7845024564545213','0780325698','deshan@gmail.com','$2a$10$h6fblHU39TiIai5vmA5qA.p7ePFi7fDuesAMXZXEDOnTQEE0DQvnq','user'),(14,'santha','1248926','246643','santha@gmail.com','$2a$10$SmUuRa4hmSUGu0lys2vHeuySpKj27OI8ujFUERUUajZxqxWrL0yNG','user'),(15,'wishwa','124892613251','2466433132','wishwa@gmail.com','$2a$10$ux6d3kjaJ2NOcskP.kfKpOd9PmBvIjFTYSmp8KXxN.oS9lGMheIbi','user'),(17,'kusal','631314564613','07536598562','kusal@gmail.com','$2a$10$3.atvDLujCxAyWv0qlMQu.BCJuSZawYMrlhcK1gcItB8Tzmdq1msK','user'),(18,'sanath','199832475869','0758946184','sanath@gmail.com','$2a$10$cc3p9zZUyKHrSMxUQi.gX.9mU4JgdoqgZU1T8LW0rzVYfVE5zxQ72','rider'),(19,'saman','199965328945','0712358469','saman@gmail.com','$2a$10$.dJynG6d5OZ47o6S0XiX6.wjH76y6854B1UHLdZTfT0ykChzo6GT6','user'),(20,'john','654112222222','123456','admin@gmail.com','$2a$10$PdpT0aaMUkXrk0O2fmvYweKK5FA513zHkQv.Vj1V7LGqiSnmaKW9C','admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
