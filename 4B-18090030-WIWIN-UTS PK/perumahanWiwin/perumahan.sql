-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 22 Apr 2020 pada 19.27
-- Versi Server: 10.1.28-MariaDB
-- PHP Version: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perumahan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_rumah`
--

CREATE TABLE `data_rumah` (
  `id` int(11) NOT NULL,
  `nama_perumahan` varchar(80) NOT NULL,
  `alamat` text NOT NULL,
  `no_telepon` varchar(20) NOT NULL,
  `email` varchar(32) NOT NULL,
  `pengembang` varchar(32) NOT NULL,
  `tipe_perumahan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `data_rumah`
--

INSERT INTO `data_rumah` (`id`, `nama_perumahan`, `alamat`, `no_telepon`, `email`, `pengembang`, `tipe_perumahan`) VALUES
(6, 'wiwin sejahtera', 'tegal', '080808080', 'sejahtera @gmail.com', 'cv maju mundur', 'Tipe 21'),
(7, 'cv wiwin maju', 'tegal', '080803564', 'majumundur@gmail.com', 'majumundurjaya', 'Tipe 36'),
(8, 'griya chiko marchiko', 'tegal', '08584888', 'griyachiko@gamil.com', 'cv maju mundur', 'Tipe 21');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_admin`
--

CREATE TABLE `tb_admin` (
  `admin_id` int(11) NOT NULL,
  `admin_fullname` varchar(50) NOT NULL,
  `admin_username` varchar(25) NOT NULL,
  `admin_password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_admin`
--

INSERT INTO `tb_admin` (`admin_id`, `admin_fullname`, `admin_username`, `admin_password`) VALUES
(1, 'Administrator', 'admin', '21232f297a57a5a743894a0e4a801fc3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_rumah`
--
ALTER TABLE `data_rumah`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `un_user` (`admin_username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_rumah`
--
ALTER TABLE `data_rumah`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tb_admin`
--
ALTER TABLE `tb_admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
