-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: university1
-- ------------------------------------------------------
-- Server version	5.7.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `course_name` varchar(40) NOT NULL,
  `course_number` varchar(20) NOT NULL,
  `credit_hours` int(11) NOT NULL,
  `department` varchar(8) NOT NULL,
  PRIMARY KEY (`course_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('Introduction to Programming','CSE100',3,'CSE'),('Programming for Business and Engineering','CSE110',3,'CSE'),('Object Oriented Computing I','CSE201',3,'CSE'),('Object Oriented Computing II','CSE202',3,'CSE'),('Databases','CSE345',4,'CSE'),('Introduction to Programming','MIS100',3,'SBA'),('Programming for Business and Engineering','MIS110',3,'SBA');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade_report`
--

DROP TABLE IF EXISTS `grade_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade_report` (
  `student_number_foreign` varchar(15) NOT NULL,
  `section_identifier_foreign` varchar(25) NOT NULL,
  `grade` decimal(2,1) DEFAULT NULL,
  PRIMARY KEY (`student_number_foreign`,`section_identifier_foreign`),
  KEY `section_identifier_foreign_idx` (`section_identifier_foreign`),
  CONSTRAINT `section_identifier_foreign` FOREIGN KEY (`section_identifier_foreign`) REFERENCES `section` (`section_identifier`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_number_foreign` FOREIGN KEY (`student_number_foreign`) REFERENCES `student` (`student_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade_report`
--

LOCK TABLES `grade_report` WRITE;
/*!40000 ALTER TABLE `grade_report` DISABLE KEYS */;
INSERT INTO `grade_report` VALUES ('G000001','CRN0001',4.0),('G000001','CRN0003',4.0),('G000001','CRN0005',3.9),('G000001','CRN0007',3.8),('G000001','CRN0009',3.8),('G000002','CRN0001',3.8),('G000002','CRN0003',3.7),('G000002','CRN0005',3.7),('G000002','CRN0008',3.6),('G000003','CRN0001',3.9),('G000003','CRN0003',3.8),('G000003','CRN0006',3.5),('G000004','CRN0002',3.7),('G000004','CRN0004',3.6),('G000004','CRN0005',3.4),('G000005','CRN0001',3.7),('G000005','CRN0003',3.8),('G000005','CRN0006',3.7),('G000006','CRN0001',3.5),('G000006','CRN0003',3.3),('G000007','CRN0001',3.6),('G000007','CRN0003',3.8),('G000008','CRN0001',2.0),('G000008','CRN0002',3.0);
/*!40000 ALTER TABLE `grade_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prerequisites`
--

DROP TABLE IF EXISTS `prerequisites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prerequisites` (
  `course_number` varchar(20) NOT NULL,
  `prerequisite_number` varchar(30) NOT NULL,
  PRIMARY KEY (`prerequisite_number`,`course_number`),
  KEY `course_number_idx` (`course_number`),
  CONSTRAINT `course_number` FOREIGN KEY (`course_number`) REFERENCES `course` (`course_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prerequisites`
--

LOCK TABLES `prerequisites` WRITE;
/*!40000 ALTER TABLE `prerequisites` DISABLE KEYS */;
INSERT INTO `prerequisites` VALUES ('CSE201','CSE100/MIS100, CSE110/MIS110'),('CSE202','CSE110/MIS110, CSE201'),('CSE345','CSE201, CSE202');
/*!40000 ALTER TABLE `prerequisites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `section_identifier` varchar(25) NOT NULL,
  `course_number_foreign` varchar(20) NOT NULL,
  `semester` varchar(15) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `instructor` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`section_identifier`),
  KEY `course_number_idx` (`course_number_foreign`),
  CONSTRAINT `course_number_foreign` FOREIGN KEY (`course_number_foreign`) REFERENCES `course` (`course_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES ('CRN0001','CSE100','Winter2014',2014,'Jill Moss'),('CRN0002','MIS100','Winter2014',2014,'Jack Miller'),('CRN0003','CSE110','Summer2014',2014,'Jill Moss'),('CRN0004','MIS110','Summer2014',2014,'Jack Miller'),('CRN0005','CSE201','Fall2014',2014,'Kate Donaldson'),('CRN0006','CSE201','Winter2015',2015,'Kate Donaldson'),('CRN0007','CSE202','Winter2015',2015,'Samuel Smith'),('CRN0008','CSE202','Fall2015',2015,'Samuel Smith'),('CRN0009','CSE345','Fall2015',2015,'Meg Wu'),('CRN0010','CSE345','Winter2016',2016,'Jim Pearson');
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `name` varchar(30) NOT NULL,
  `student_number` varchar(15) NOT NULL,
  `class` varchar(15) DEFAULT NULL,
  `major` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`student_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('John Adams','G000001','Senior','Computer Science'),('Marie Johnson','G000002','Junior','Information Technology'),('Jane Jones','G000003','Senior','Computer Engineering'),('Paul Williams','G000004','Junior','Management Information Systems'),('Ann Mozart','G000005','Sophomore','Computer Science'),('Michael Green','G000006','Sophomore','Information Technology'),('Mary Smith','G000007','Junior','Computer Engineering'),('Adrian Parker','G000008','Freshman','Management Information Systems');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-26 20:58:13
