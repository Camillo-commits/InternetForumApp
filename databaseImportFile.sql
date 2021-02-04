-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 04 Lut 2021, 15:40
-- Wersja serwera: 10.4.11-MariaDB
-- Wersja PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `forumfinal`
--

DELIMITER $$
--
-- Procedury
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUser` (IN `N` VARCHAR(20), IN `Im` VARCHAR(20), IN `Naz` VARCHAR(20), IN `mai` TEXT, IN `has` TEXT)  NO SQL
INSERT INTO uzytkownik (`nick`, `imie`, `nazwisko`, `mail`, `poziom`, `id_uprawnien`, `haslo`) 
VALUES (N,Im,Naz,mai,'poczatkujacy','4',has)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `allUserMessages` (IN `t` TEXT, IN `name` VARCHAR(20))  NO SQL
SELECT wiadomosci.nick,wiadomosci.data_wpisu,wiadomosci.tresc
FROM wiadomosci
INNER JOIN tematy
ON tematy.id_tematu = wiadomosci.id_tematu
WHERE wiadomosci.nick = name AND tematy.tytul = t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `allUsersThatResponded` (IN `t` TEXT)  NO SQL
SELECT DISTINCT wiadomosci.nick 
FROM wiadomosci
INNER JOIN tematy
ON tematy.id_tematu = wiadomosci.id_tematu
WHERE tematy.tytul = t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `AnswerMessage` (IN `temID` INT, IN `tresc` TEXT, IN `nik` VARCHAR(20), IN `wiadODP` INT)  NO SQL
INSERT INTO `wiadomosci` (`id_wiadomosci`, `tresc`, `data_wpisu`, `nick`, `id_tematu`, `przypiete`, `id_odpowiedz`) VALUES (NULL, tresc, CURRENT_DATE, nik,temID, '0', wiadODP)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMessage` (IN `wiadID` INT)  NO SQL
DELETE FROM `wiadomosci` WHERE `wiadomosci`.`id_wiadomosci` = wiadID$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteTopic` (IN `temID` INT)  NO SQL
DELETE tematy FROM tematy
WHERE tematy.id_tematu = temID$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteTopicGroup` (IN `grID` INT)  NO SQL
DELETE grupy_tematow,tematy FROM grupy_tematow
INNER JOIN tematy
ON tematy.id_grupy = grupy_tematow.id_grupy
WHERE grupy_tematow.id_grupy = grID$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editTopicFinishDate` (IN `da` DATE, IN `id` INT)  NO SQL
UPDATE `tematy` SET `data_zakonczenia` = da WHERE `tematy`.`id_tematu` = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editTopicGroupModerator` (IN `moder` VARCHAR(20), IN `id` INT)  NO SQL
UPDATE `grupy_tematow` SET `id_moderatora` = moder WHERE `grupy_tematow`.`id_grupy` = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editTopicGroupName` (IN `naz` TEXT, IN `id` INT)  NO SQL
UPDATE `grupy_tematow` SET `nazwa_grupy` = naz WHERE `grupy_tematow`.`id_grupy` = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editTopicModerator` (IN `moder` VARCHAR(20), IN `id` INT)  NO SQL
UPDATE `tematy` SET `id_moderatora` = moder WHERE `tematy`.`id_tematu` = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editTopicSettings` (IN `ust` INT, IN `id` INT)  NO SQL
UPDATE `tematy` SET `id_ustawien` = ust WHERE `tematy`.`id_tematu` = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `editTopicTitle` (IN `tyt` TEXT, IN `id` INT)  NO SQL
UPDATE `tematy` SET `tytul` = tyt WHERE `tematy`.`id_tematu` = id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `makeTopic` (IN `moder` VARCHAR(20), IN `tyt` TEXT, IN `grpa` INT, IN `ust` INT)  NO SQL
INSERT INTO `tematy` (`id_tematu`, `id_moderatora`, `tytul`, `id_grupy`, `data_zalozenia`, `data_zakonczenia`, `id_ustawien`)
VALUES (NULL, moder, tyt, grpa, 'current_timestamp()', NULL, ust)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `makeTopicGroup` (IN `naz` TEXT, IN `moder` VARCHAR(20))  NO SQL
INSERT INTO `grupy_tematow` (`id_grupy`, `nazwa_grupy`, `id_moderatora`) VALUES (NULL, naz, moder)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `messagesForConfirmationFromTopic` (IN `t` TEXT)  NO SQL
SELECT wiadomosci.id_wiadomosci,wiadomosci.tresc
FROM wiadomosci
INNER JOIN wiadomosci_do_potwierdzenia
ON wiadomosci_do_potwierdzenia.id_wiadomosci = wiadomosci.id_wiadomosci
INNER JOIN tematy
ON tematy.id_tematu = wiadomosci.id_tematu
WHERE tematy.tytul = t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `messagesForConfirmationFromTopicGroup` (IN `t` TEXT)  NO SQL
SELECT wiadomosci_do_potwierdzenia.id_wiadomosci,wiadomosci.nick, wiadomosci.tresc
FROM wiadomosci
INNER JOIN wiadomosci_do_potwierdzenia
ON wiadomosci_do_potwierdzenia.id_wiadomosci = wiadomosci.id_wiadomosci
INNER JOIN tematy
ON tematy.id_tematu = wiadomosci.id_tematu
INNER JOIN grupy_tematow
ON grupy_tematow.id_grupy = tematy.id_grupy

WHERE grupy_tematow.nazwa_grupy = t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pinMessage` (IN `wiadID` INT)  NO SQL
UPDATE `wiadomosci` SET `przypiete` = '1' WHERE `wiadomosci`.`id_wiadomosci` = wiadID$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setPoziom` (IN `poz` ENUM('poczatkujacy','aktywny','doswiadczony','weteran'), IN `ni` VARCHAR(20))  NO SQL
UPDATE `uzytkownik` SET `poziom` = poz WHERE `uzytkownik`.`nick` = ni$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setRole` (IN `role` INT, IN `ni` VARCHAR(20))  NO SQL
UPDATE `uzytkownik` SET `id_uprawnien` = role WHERE `uzytkownik`.`nick` = ni$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `showAllAnswersToMessages` ()  NO SQL
SELECT wiadomosci.id_wiadomosci,wiadomosci.nick,wiadomosci.data_wpisu,wiadomosci.tresc
FROM wiadomosci
WHERE wiadomosci.id_odpowiedz != 0$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `showAllMessagesFromTopic` (IN `t` TEXT)  NO SQL
SELECT wiadomosci.id_tematu,wiadomosci.nick, wiadomosci.tresc FROM wiadomosci
INNER JOIN tematy
ON wiadomosci.id_tematu = tematy.id_tematu
WHERE tematy.tytul = t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `showAllTopicGroups` ()  NO SQL
SELECT grupy_tematow.nazwa_grupy, uzytkownik.nick
FROM grupy_tematow
INNER JOIN uzytkownik
ON uzytkownik.nick = grupy_tematow.id_moderatora$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `showAllTopicsFromTopicGroup` (IN `t` TEXT)  NO SQL
SELECT tematy.tytul,tematy.data_zalozenia,tematy.data_zakonczenia,ustawienia_dostepnosci.opis
FROM tematy
INNER JOIN ustawienia_dostepnosci
ON ustawienia_dostepnosci.id_ustawien = tematy.id_ustawien
INNER JOIN grupy_tematow
ON grupy_tematow.id_grupy = tematy.id_grupy
WHERE grupy_tematow.nazwa_grupy = t$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `userData` (INOUT `name` VARCHAR(20))  NO SQL
SELECT * FROM uzytkownik
WHERE nick = name$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `userPrivileges` (IN `name` VARCHAR(20))  NO SQL
SELECT uzytkownik.id_uprawnien, uprawnienia.opis
FROM uzytkownik
INNER JOIN uprawnienia
ON uzytkownik.id_uprawnien = uprawnienia.id_uprawnien
WHERE uzytkownik.nick = name$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `writeMessage` (IN `temID` INT, IN `tresc` TEXT, IN `nik` VARCHAR(20))  NO SQL
INSERT INTO `wiadomosci` (`id_wiadomosci`, `tresc`, `data_wpisu`, `nick`, `id_tematu`, `przypiete`, `id_odpowiedz`) VALUES (NULL, tresc, CURRENT_DATE, nik,temID, '0', '')$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `grupy_tematow`
--

CREATE TABLE `grupy_tematow` (
  `id_grupy` int(11) NOT NULL,
  `nazwa_grupy` text COLLATE utf8mb4_polish_ci NOT NULL,
  `id_moderatora` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `grupy_tematow`
--

INSERT INTO `grupy_tematow` (`id_grupy`, `nazwa_grupy`, `id_moderatora`) VALUES
(1, 'Nasze projekty', 'admin'),
(2, 'Marketplace', 'mirekHandlarz'),
(3, 'pogadanki', 'admin'),
(4, 'zlocik', 'admin'),
(11, 'dddd', 'admin'),
(12, 'Grupa Tematyczna test1', 'admin');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tematy`
--

CREATE TABLE `tematy` (
  `id_tematu` int(11) NOT NULL,
  `id_moderatora` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL,
  `tytul` text COLLATE utf8mb4_polish_ci NOT NULL,
  `id_grupy` int(11) NOT NULL,
  `data_zalozenia` date NOT NULL DEFAULT current_timestamp(),
  `data_zakonczenia` date DEFAULT NULL,
  `id_ustawien` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `tematy`
--

INSERT INTO `tematy` (`id_tematu`, `id_moderatora`, `tytul`, `id_grupy`, `data_zalozenia`, `data_zakonczenia`, `id_ustawien`) VALUES
(1, 'admin', 'Mój projekt', 1, '2020-05-18', NULL, 1),
(2, 'rabarbar', 'Mój projekt', 1, '2020-04-25', NULL, 3),
(3, 'mirekHandlarz', 'Sprzedam Opla', 2, '2020-05-11', '2020-05-21', 2),
(4, 'admin', 'Pomoc w zakupie dobrego sprzętu', 2, '2020-05-01', NULL, 1),
(7, 'admin', 'heheszki', 3, '0000-00-00', NULL, 1),
(15, 'admin', 'sprawy bbiezace', 4, '2020-06-09', '0000-00-00', 1),
(17, 'admin', 'tez-sie-pochwale', 1, '2020-06-09', NULL, 1),
(18, 'rabarbar', 'weryfikacja dzialania spacji', 1, '2020-06-09', NULL, 2),
(25, 'rabarbar', '4544', 11, '2020-06-10', NULL, 1),
(27, 'Adam123', 'test2', 12, '2020-06-11', NULL, 2),
(28, 'rabarbar', 'test3', 12, '2020-06-11', NULL, 3),
(29, 'admin', 'test4', 12, '2020-06-11', '2020-06-17', 1),
(30, 'admin', 'test5', 12, '2020-06-11', NULL, 2),
(31, 'admin', 'test6', 12, '2020-06-11', NULL, 3),
(32, 'admin', 'test 7', 12, '2020-06-11', NULL, 1),
(33, 'rabarbar', 'test8', 12, '2020-06-11', NULL, 2),
(34, 'admin', 'test9', 12, '2020-06-11', NULL, 3),
(35, 'admin', 'test10', 12, '2020-06-11', NULL, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uprawnienia`
--

CREATE TABLE `uprawnienia` (
  `id_uprawnien` int(11) NOT NULL,
  `opis` text COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `uprawnienia`
--

INSERT INTO `uprawnienia` (`id_uprawnien`, `opis`) VALUES
(1, 'Możliwość usuwania użytkownika i nadawania mu praw\r\nMożliwość zakładania grup tematów\r\nMożliwość zakładania tematu\r\nMożliwość dostępu do wiadomości_do_potwierdzenia'),
(2, 'Możliwość zakładania grup tematów\r\nMożliwość zakładania tematu\r\nMożliwość dostępu do wiadomości_do_potwierdzenia'),
(3, 'Możliwość zakładania tematu'),
(4, 'Możliwość czytania tematów');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ustawienia_dostepnosci`
--

CREATE TABLE `ustawienia_dostepnosci` (
  `id_ustawien` int(11) NOT NULL,
  `opis` text COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `ustawienia_dostepnosci`
--

INSERT INTO `ustawienia_dostepnosci` (`id_ustawien`, `opis`) VALUES
(1, 'Dla wszystkich'),
(2, 'Dla aktywnych'),
(3, 'Dla doświadczonych'),
(4, 'Dla weteranów');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `uzytkownik`
--

CREATE TABLE `uzytkownik` (
  `nick` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL,
  `imie` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL,
  `nazwisko` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL,
  `mail` text COLLATE utf8mb4_polish_ci NOT NULL,
  `poziom` enum('poczatkujacy','aktywny','doswiadczony','weteran') COLLATE utf8mb4_polish_ci NOT NULL DEFAULT 'poczatkujacy',
  `id_uprawnien` int(11) NOT NULL,
  `haslo` text COLLATE utf8mb4_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `uzytkownik`
--

INSERT INTO `uzytkownik` (`nick`, `imie`, `nazwisko`, `mail`, `poziom`, `id_uprawnien`, `haslo`) VALUES
('Adam123', 'Adam', 'Nowak', 'adamnowak@gmail.com', 'poczatkujacy', 4, 'adam123'),
('admin', 'admin', 'admin', 'admin.admin@gmail.com', 'weteran', 1, 'admin'),
('gdybacz', 'Jan', 'Kowalski', 'JK@gmail.com', 'poczatkujacy', 2, 'KowalJn'),
('mirekHandlarz', 'Mirosław', 'Truteń', 'sprzedam.honde@gmail.com', 'aktywny', 3, 'januszBiznesu'),
('rabarbar', 'Iwona', 'Rak', 'rak.iwona5@onet.pl', 'doswiadczony', 2, 'qwertyuiop');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wiadomosci`
--

CREATE TABLE `wiadomosci` (
  `id_wiadomosci` int(11) NOT NULL,
  `tresc` text COLLATE utf8mb4_polish_ci NOT NULL,
  `data_wpisu` date NOT NULL DEFAULT current_timestamp(),
  `nick` varchar(20) COLLATE utf8mb4_polish_ci NOT NULL,
  `id_tematu` int(11) NOT NULL,
  `przypiete` tinyint(1) NOT NULL DEFAULT 0,
  `id_odpowiedz` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

--
-- Zrzut danych tabeli `wiadomosci`
--

INSERT INTO `wiadomosci` (`id_wiadomosci`, `tresc`, `data_wpisu`, `nick`, `id_tematu`, `przypiete`, `id_odpowiedz`) VALUES
(1, 'Ładna Omega do oddania w dobre ręce. Niemiec płakał jak sprzedawał.\r\nJedynie 10 tysięcy polskich złociszy.\r\nLink do aukcji :........', '2020-05-17', 'mirekHandlarz', 3, 1, 0),
(2, 'Panie to się przecież nie opłaca', '2020-05-18', 'Adam123', 3, 0, 1),
(3, 'OPEL RDZEWIEJE co robić?!', '2020-05-28', 'rabarbar', 2, 0, 0),
(4, 'Najlepiej na złom XD', '2020-05-28', 'Adam123', 2, 1, 3),
(5, 'proszę o wiecej informacji', '2020-05-22', 'gdybacz', 3, 0, 0),
(6, 'też byłbym zainteresowany', '2020-05-22', 'rabarbar', 3, 0, 0),
(57, 'aaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssddddddddddddddddddddfffffffffffffffffffgggggggggggg', '2020-06-09', 'admin', 1, 1, 0),
(67, 'aaaaaaaaaaaaaaaaaaaaaaaaaaa', '2020-06-10', 'mirekHandlarz', 4, 0, 0),
(68, 'dfgsshhshshshshshasasd', '2020-06-17', 'admin', 35, 0, 0),
(69, 'jdkdkkskskksksksksk', '2020-06-17', 'admin', 35, 0, 68),
(70, 'hhhhhhhhhhhhhhhhhhhhh', '2020-06-17', 'admin', 35, 1, 0);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `grupy_tematow`
--
ALTER TABLE `grupy_tematow`
  ADD PRIMARY KEY (`id_grupy`),
  ADD KEY `grtem_moder` (`id_moderatora`);

--
-- Indeksy dla tabeli `tematy`
--
ALTER TABLE `tematy`
  ADD PRIMARY KEY (`id_tematu`),
  ADD KEY `tematy_nick` (`id_moderatora`),
  ADD KEY `ust_dost_id` (`id_ustawien`),
  ADD KEY `temat_grupa` (`id_grupy`);

--
-- Indeksy dla tabeli `uprawnienia`
--
ALTER TABLE `uprawnienia`
  ADD PRIMARY KEY (`id_uprawnien`);

--
-- Indeksy dla tabeli `ustawienia_dostepnosci`
--
ALTER TABLE `ustawienia_dostepnosci`
  ADD PRIMARY KEY (`id_ustawien`);

--
-- Indeksy dla tabeli `uzytkownik`
--
ALTER TABLE `uzytkownik`
  ADD PRIMARY KEY (`nick`),
  ADD KEY `uzytkownik_uprawn` (`id_uprawnien`);

--
-- Indeksy dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  ADD PRIMARY KEY (`id_wiadomosci`),
  ADD KEY `wiad_nick` (`nick`),
  ADD KEY `wiad_temat` (`id_tematu`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `grupy_tematow`
--
ALTER TABLE `grupy_tematow`
  MODIFY `id_grupy` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `tematy`
--
ALTER TABLE `tematy`
  MODIFY `id_tematu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT dla tabeli `uprawnienia`
--
ALTER TABLE `uprawnienia`
  MODIFY `id_uprawnien` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `ustawienia_dostepnosci`
--
ALTER TABLE `ustawienia_dostepnosci`
  MODIFY `id_ustawien` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  MODIFY `id_wiadomosci` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `grupy_tematow`
--
ALTER TABLE `grupy_tematow`
  ADD CONSTRAINT `grtem_moder` FOREIGN KEY (`id_moderatora`) REFERENCES `uzytkownik` (`nick`);

--
-- Ograniczenia dla tabeli `tematy`
--
ALTER TABLE `tematy`
  ADD CONSTRAINT `temat_grupa` FOREIGN KEY (`id_grupy`) REFERENCES `grupy_tematow` (`id_grupy`),
  ADD CONSTRAINT `tematy_nick` FOREIGN KEY (`id_moderatora`) REFERENCES `uzytkownik` (`nick`),
  ADD CONSTRAINT `ust_dost_id` FOREIGN KEY (`id_ustawien`) REFERENCES `ustawienia_dostepnosci` (`id_ustawien`);

--
-- Ograniczenia dla tabeli `uzytkownik`
--
ALTER TABLE `uzytkownik`
  ADD CONSTRAINT `uzytkownik_uprawn` FOREIGN KEY (`id_uprawnien`) REFERENCES `uprawnienia` (`id_uprawnien`);

--
-- Ograniczenia dla tabeli `wiadomosci`
--
ALTER TABLE `wiadomosci`
  ADD CONSTRAINT `wiad_nick` FOREIGN KEY (`nick`) REFERENCES `uzytkownik` (`nick`),
  ADD CONSTRAINT `wiad_temat` FOREIGN KEY (`id_tematu`) REFERENCES `tematy` (`id_tematu`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
