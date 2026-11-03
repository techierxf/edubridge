-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 03, 2026 at 10:04 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edubridge_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `enrollment_applications`
--

CREATE TABLE `enrollment_applications` (
  `id` bigint(20) NOT NULL,
  `reference_no` varchar(20) NOT NULL,
  `student_account_id` int(11) NOT NULL,
  `student_type` varchar(255) DEFAULT NULL,
  `academic_year` varchar(255) DEFAULT NULL,
  `semester` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `complete_address` text DEFAULT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `year_level` int(11) DEFAULT NULL,
  `previous_school_name` varchar(255) DEFAULT NULL,
  `previous_course` varchar(255) DEFAULT NULL,
  `last_year_level` int(11) DEFAULT NULL,
  `guardian_full_name` varchar(255) DEFAULT NULL,
  `guardian_relationship` varchar(255) DEFAULT NULL,
  `guardian_contact_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` datetime(6) DEFAULT NULL,
  `reviewed_by` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrollment_applications`
--

INSERT INTO `enrollment_applications` (`id`, `reference_no`, `student_account_id`, `student_type`, `academic_year`, `semester`, `full_name`, `date_of_birth`, `sex`, `email`, `contact_number`, `complete_address`, `course_id`, `year_level`, `previous_school_name`, `previous_course`, `last_year_level`, `guardian_full_name`, `guardian_relationship`, `guardian_contact_number`, `status`, `remarks`, `submitted_at`, `created_at`, `reviewed_by`) VALUES
(1, 'EDU-2F03E548', 4, 'new', '2026-2027', '2nd', 'Bloodseeker', '2010-06-29', 'male', 'wengweng@gmail.com', '09123456782', 'bogo city', 'BSECE', 2, '', '', NULL, 'Broodmother', 'mother', '09123456798', 'REJECTED', 'Please enter your name wisely!', '2026-11-02 23:19:51', '2026-11-03 07:19:51.000000', NULL),
(2, 'EDU-E457556C', 5, 'new', '2027-2028', '2nd', 'Jelev Bulao', '2002-11-11', 'female', 'jelevbulao@gmail.com', '09940186109', '14 Yakal St. Brgy Capri Novaliches, Quezon City', 'BSEd', 4, '', '', NULL, 'Raf Mercado', 'other', '09123356712', 'APPROVED', 'bring your documents on friday noon!', '2026-11-03 03:25:06', '2026-11-03 11:25:06.000000', NULL),
(3, 'EDU-3654E80B', 6, 'new', '2026-2027', '2nd', 'John Brenz Romero', '2004-07-15', 'male', 'brenzoromero@gmail.com', '09123135789', 'Caloocan City', 'BSCS', 2, '', '', NULL, 'Brenzgusionromero', 'guardian', '09123156798', 'PENDING', NULL, '2026-11-03 03:59:00', '2026-11-03 11:59:00.000000', NULL),
(4, 'EDU-A77EAAE1', 4, 'new', '2026-2027', '2nd', 'Bloodseeker Strygwyr', '2010-06-29', 'male', 'wengweng@gmail.com', '09123456782', 'bogo city', 'BSECE', 2, '', '', NULL, 'Broodmother', 'mother', '09123456798', 'APPROVED', 'Please submit your documents on friday!', '2026-11-03 04:51:29', '2026-11-03 12:51:29.000000', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `staff_accounts`
--

CREATE TABLE `staff_accounts` (
  `id` bigint(20) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'STAFF'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff_accounts`
--

INSERT INTO `staff_accounts` (`id`, `full_name`, `email`, `password_hash`, `created_at`, `role`) VALUES
(1, 'John Rafael Desoloc', 'staff@edubridge.com', '$2a$10$ftelBdFyPXmEaqtn1l53wObNJq8BOmrRbdr5mQIeOetagGfFwCVoG', '2026-11-03 13:02:12.000000', 'ADMIN'),
(2, 'Raf Mercado', 'staff1@edubridge.com', '$2a$10$BP8LDUlSW7Uvt8dJa.H2yOfxAvdqy7ZI0fbg/HGalfIpQ9YmK/z.W', '2026-11-03 14:56:36.000000', 'STAFF'),
(3, 'Unauthorized Test', 'unauthorized@example.com', '$2a$10$VDH6mh1.g6slEqtu85snf.uj/KUk/yHlKUA0qO8t9oGhziTqy0YXi', '2026-11-03 15:04:01.000000', 'STAFF');

-- --------------------------------------------------------

--
-- Table structure for table `student_accounts`
--

CREATE TABLE `student_accounts` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_accounts`
--

INSERT INTO `student_accounts` (`id`, `full_name`, `email`, `password_hash`, `created_at`) VALUES
(2, 'wewewe', 'wewewe@gmail.com', '$2a$10$JQiOf0DxP8s8XiJcb.8Xf.3A63wgkf1gL/DWhVHu/J2tvhu1452gK', '2026-11-02 21:22:14'),
(3, 'hahahah', 'hahahaha@gmail.com', '$2a$10$t6hmCwElUnq3WdNz/xtheORscGu.jtvEmVvikn0swN49zamToZvca', '2026-11-02 21:23:30'),
(4, 'Strgywyr', 'wengweng@gmail.com', '$2a$10$gH/aHyu16thvOWsJIwO0gebY.K3rKKJUvSBXcZiG13erzzBDHo2.q', '2026-11-02 22:44:55'),
(5, 'juvimercado', 'juvsmerc@gmail.com', '$2a$10$/tHEMOje0yDWyt2jw7iIbOtk36mfy3qKyxC8MxpHLyReY1m7CKXYy', '2026-11-03 03:21:44'),
(6, 'brenzoromero', 'brenzoromero@gmail.com', '$2a$10$huw6sz.StNdree2D.r0V0e84JPzfQTE0xXlFW4lNOfbyOqW.WlerS', '2026-11-03 03:57:18');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `enrollment_applications`
--
ALTER TABLE `enrollment_applications`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `reference_no` (`reference_no`),
  ADD KEY `fk_enrollment_student` (`student_account_id`),
  ADD KEY `fk_enrollment_staff` (`reviewed_by`);

--
-- Indexes for table `staff_accounts`
--
ALTER TABLE `staff_accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `student_accounts`
--
ALTER TABLE `student_accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `enrollment_applications`
--
ALTER TABLE `enrollment_applications`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `staff_accounts`
--
ALTER TABLE `staff_accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `student_accounts`
--
ALTER TABLE `student_accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enrollment_applications`
--
ALTER TABLE `enrollment_applications`
  ADD CONSTRAINT `enrollment_applications_ibfk_1` FOREIGN KEY (`student_account_id`) REFERENCES `student_accounts` (`id`),
  ADD CONSTRAINT `fk_enrollment_staff` FOREIGN KEY (`reviewed_by`) REFERENCES `staff_accounts` (`id`),
  ADD CONSTRAINT `fk_enrollment_student` FOREIGN KEY (`student_account_id`) REFERENCES `student_accounts` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
