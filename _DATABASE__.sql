-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 01, 2019 at 12:24 AM
-- Server version: 10.3.14-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id7610416_obook`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `bookID` int(11) NOT NULL,
  `bookName` varchar(48) COLLATE utf8_unicode_ci NOT NULL,
  `price` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `phone` int(20) NOT NULL,
  `intro` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `bookType` varchar(48) COLLATE utf8_unicode_ci NOT NULL,
  `salerName` varchar(48) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`bookID`, `bookName`, `price`, `num`, `phone`, `intro`, `bookType`, `salerName`) VALUES
(1, 'AAA1', 12, 4, 1134747336, '..SSSSSS', 'Others', 'yang'),
(2, 'AAA2', 12, 5, 1134747336, 'SSSSSS', 'Others', 'yang'),
(3, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Others', 'yang'),
(4, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Life', 'yang'),
(5, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Life', 'yang'),
(6, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Study', 'yang'),
(11, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Life', 'yang'),
(12, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Study', 'yang'),
(13, 'AAA', 12, 5, 1134747336, 'SSSSSS', 'Study', 'yang');

-- --------------------------------------------------------

--
-- Table structure for table `card`
--

CREATE TABLE `card` (
  `orderID` int(11) NOT NULL,
  `phone` int(24) NOT NULL,
  `address` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `buyerName` varchar(48) COLLATE utf8_unicode_ci NOT NULL,
  `bookName` varchar(48) COLLATE utf8_unicode_ci NOT NULL,
  `price` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `sellerName` varchar(48) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

--
-- Dumping data for table `card`
--

INSERT INTO `card` (`orderID`, `phone`, `address`, `buyerName`, `bookName`, `price`, `bookID`, `sellerName`) VALUES
(1, 12434, 'asdff', 'alex', 'AAA', 12, 5, 'yang'),
(2, 12344, 'asdfdf', 'alex', 'AAA', 12, 5, 'yang'),
(3, 124323, 'fdrsedw', 'alex', 'AAA', 12, 5, 'yang'),
(4, 12432423, 'trgeerg', 'alex', 'AAA', 12, 5, 'yang'),
(5, 12334, 'rgeer', 'alex', 'AAA', 12, 3, 'yang'),
(6, 123334, 'asdewfw', 'alex', 'AAA', 12, 3, 'yang'),
(7, 123413, 'fsefegwe', 'alex', 'AAA', 12, 3, 'yang'),
(8, 11243, 'adsadf', 'alex', 'AAA', 12, 4, 'yang'),
(9, 12334, 'adsfef', 'alex', 'AAA', 12, 3, 'yang'),
(10, 1234124, 'asdasd', 'alex', 'AAA', 12, 3, 'yang'),
(11, 1232434, 'adadf', 'alex', 'AAA', 12, 3, 'yang'),
(12, 123123, 'wqeweq', 'alex', 'AAA1', 12, 1, 'yang'),
(13, 1231244, 'asdsdf', 'alex', 'AAA1', 12, 1, 'yang'),
(14, 1234234, 'adasdf', 'alex', 'AAA1', 12, 1, 'yang');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(48) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(48) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, '', 'da39a3ee5e6b4b0d3255bfef95601890afd80709'),
(3, 'yang', '4a26aceafd9a7942a96bf12a8148c6ce49486ff4'),
(22, 'alex', '4a26aceafd9a7942a96bf12a8148c6ce49486ff4'),
(23, 'aaa', '6373050ac6f292c7f40103686db60eabe536615a');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`bookID`);

--
-- Indexes for table `card`
--
ALTER TABLE `card`
  ADD PRIMARY KEY (`orderID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `bookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `card`
--
ALTER TABLE `card`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
