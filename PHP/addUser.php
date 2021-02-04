<?php
$db = "forumfinal";

$nick = $_GET["nick"];
$imie = $_GET["imie"];
$nazwisko = $_GET["nazwisko"];
$mail = $_GET["mail"];
$poziom = $_GET["poziom"];
$id_uprawnien = $_GET["id_uprawnien"];
$haslo = $_GET["haslo"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "INSERT INTO `uzytkownik` (`nick`, `imie`, `nazwisko`, `mail`, `poziom`, `id_uprawnien`, `haslo`) VALUES ('$nick', '$imie', '$nazwisko', '$mail', '$poziom', '$id_uprawnien', '$haslo');";
	
	if(mysqli_query($conn, $q)){
		echo "User added successfully";
	}
	else{
		echo "Adding user failed";
	}
}
?>