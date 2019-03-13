-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 24, 2018 at 06:06 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.5.37

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bloodbank`
--

-- --------------------------------------------------------

--
-- Table structure for table `adddonor`
--

CREATE TABLE `adddonor` (
  `ID` int(255) NOT NULL,
  `Abloodgroup` varchar(255) NOT NULL,
  `Agender` varchar(255) NOT NULL,
  `Aavailability` varchar(255) NOT NULL,
  `Adate` varchar(255) NOT NULL,
  `Aage` varchar(255) NOT NULL,
  `Aemail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adddonor`
--

INSERT INTO `adddonor` (`ID`, `Abloodgroup`, `Agender`, `Aavailability`, `Adate`, `Aage`, `Aemail`) VALUES
(2, 'A(+)ve', 'male', '', '05/12/2017', '3', 'jk.com'),
(3, 'A(+)ve', 'female', 'Available', '26/12/2017', '77', 'abc@gmail.com'),
(4, 'A(+)ve', 'male', 'Available', '21/12/2017', '33', 'gmail.com'),
(5, 'A(+)ve', 'male', 'Available', '27/12/2017', '32', 'chinmay.cse@gmail.com'),
(6, 'A(+)ve', 'Male', 'Available', '29/12/2017', '222', 'jouk.com');

-- --------------------------------------------------------

--
-- Table structure for table `bloodcenter`
--

CREATE TABLE `bloodcenter` (
  `ID` int(11) NOT NULL,
  `Cname` varchar(255) DEFAULT NULL,
  `Clocation` varchar(255) DEFAULT NULL,
  `Cofficehour` varchar(255) DEFAULT NULL,
  `Ccontact` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bloodcenter`
--

INSERT INTO `bloodcenter` (`ID`, `Cname`, `Clocation`, `Cofficehour`, `Ccontact`) VALUES
(1, 'Badhon', 'PSTU academic building', '9am - 4pm', '098098'),
(2, 'Samdhani', 'Barisal University campus', '11am - 3pm', '876876'),
(3, 'Badhon', 'PSTU academic building', '9am - 4pm', '098098'),
(4, 'Badhon', 'PSTU academic building', '9am - 4pm', '098098');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `ID` int(255) NOT NULL,
  `Rname` varchar(255) NOT NULL,
  `Rblood` varchar(255) NOT NULL,
  `Runit` varchar(255) NOT NULL,
  `Rdate` varchar(255) NOT NULL,
  `Rhospital` varchar(255) NOT NULL,
  `Rcity` varchar(255) NOT NULL,
  `Rcontact` varchar(255) NOT NULL,
  `Remail` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`ID`, `Rname`, `Rblood`, `Runit`, `Rdate`, `Rhospital`, `Rcity`, `Rcontact`, `Remail`) VALUES
(1, 'name', 'rt', 'uy', '2018/12/28', 'hos', 'city', 'contact', 'abc@gmail.com'),
(2, 'manish', 'A+', '5', '2018/12/20', 'hospital', 'dumki', '123456', 'gmail.com'),
(3, 'joy', 'A+', '5', '2017/12/18', 'hospital', 'dumki', '123456', 'joy@'),
(4, 'manish', 'A+', '5', '2017/03/01', 'hospital', 'dumki', '123456', 'joy@gmail'),
(5, 'manish', 'A+', '5', '2017/05/06', 'hospital', 'dumki', '123456', 'abc'),
(7, 'ch', 'A+', '2', '2017/12/27', 'yyggh', 'Dhaka', '01922361666', 'gfgfg'),
(8, 'tyrieyy', 'B(+)ve', '2', '2017/12/29', 'joybangla', 'bdhddghfdd', '01778602754', 'rfasdf'),
(11, 'Anis mahmu jony', 'A(+)ve', '1', '2018/01/15', 'Swarupkati govt hospital', 'Swarupkati', '01680463061', 'jkjkjk'),
(12, 'mnish', 'A(+)ve', '5', '2018/12/20', 'hospital', 'dumki', '123456', 'gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `ID` int(255) NOT NULL,
  `Uname` varchar(255) NOT NULL,
  `Ucity` varchar(255) NOT NULL,
  `Uphone` varchar(255) NOT NULL,
  `Uemail` varchar(255) NOT NULL,
  `Upassword` varchar(255) NOT NULL,
  `Ulatitude` varchar(255) NOT NULL,
  `Ulongitude` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `signup`
--

INSERT INTO `signup` (`ID`, `Uname`, `Ucity`, `Uphone`, `Uemail`, `Upassword`, `Ulatitude`, `Ulongitude`) VALUES
(11, 'joy', 'barisal', '12345', 'joy@', '1234', '', ''),
(12, 'Joy karmaker', 'Barisal', '017786027', 'abc@gmail.com', '124555', '22.4655254', '90.3892577'),
(13, 'joysggsgs', 'shhshshs', '1111', 'gsgsgshsh', '124555', '', ''),
(14, 'joysggsgs', 'shhshshs', '67879', 'gsgsgshsh', '124555', '', ''),
(15, 'joysggsgs', 'shhshshs', '3333', 'gsgsgshsh', '124555', '', ''),
(16, 'hsgssggddg', 'shysysyssy', '54545454', 'sgsgsg', 'sgsggsgsgshs', '', ''),
(17, 'joy ghfh', 'barisal', '01778', 'gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '22.4652231', '90.3826783'),
(18, 'joy', 'barisal', '01778', 'gmafhgffgh', 'ab56b4d92b40713acc5af89985d4b786', '22.4654625', '90.3892100'),
(19, 'c', 'b', '01922361666', 'chinmay.cse@gmail.com', 'e807f1fcf82d132f9bb018ca6738a19f', '22.4654625', '95.3892900'),
(20, 'name', 'city', '5123', 'abc', '900150983cd24fb0d6963f7d28e17f72', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `totalblood`
--

CREATE TABLE `totalblood` (
  `ID` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `contact` varchar(255) NOT NULL,
  `bloodgroup` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `center` varchar(255) NOT NULL,
  `currentstatus` varchar(255) NOT NULL,
  `sms` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `totalblood`
--

INSERT INTO `totalblood` (`ID`, `name`, `email`, `contact`, `bloodgroup`, `amount`, `date`, `status`, `center`, `currentstatus`, `sms`) VALUES
(1, 'a', 'gmail.com', 'b', 'A(+)ve', '1', '2018/01/25', 'Notdonated', 'Badhon', 'Used', 'Notsent'),
(2, 'ab', 'abc', 'bc', 'B(+)ve', '1', '2018-01-31', 'Donated', 'Badhon', 'Used', 'Sent'),
(3, 'ok', 'ok@gmail.com', '12345', 'A(+)ve', '1', '2018/01/29', 'Donated', 'Badhon', 'Used', 'Notsent'),
(4, 'lenovo', 'lenovo@gmail.com', '12345', 'O(+)ve', '1', '2018/01/29', 'Donated', 'Samdhani', 'Notused', 'Notsent'),
(5, 'dell', 'dell@gmail.com', '12345', 'A(+)ve', '1', '2018/01/29', 'Donated', 'Samdhani', 'Notused', 'Notsent'),
(6, 'hp', 'hp@gmail.com', '12345', 'A(+)ve', '1', '2018/01/29', 'Donated', 'Badhon', 'Used', 'Notsent'),
(7, 'joy', 'gmail.com', '01778602754', 'B(+)ve', '1', '2018-01-27', 'Notdonated', 'Badhon', 'Notused', 'Notsent'),
(8, 'anish', 'abc', '123', 'AB(+)ve', '1', '2018-01-31', 'Donated', 'Badhon', 'Used', 'Sent');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adddonor`
--
ALTER TABLE `adddonor`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `bloodcenter`
--
ALTER TABLE `bloodcenter`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `totalblood`
--
ALTER TABLE `totalblood`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adddonor`
--
ALTER TABLE `adddonor`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `bloodcenter`
--
ALTER TABLE `bloodcenter`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `signup`
--
ALTER TABLE `signup`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `totalblood`
--
ALTER TABLE `totalblood`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
