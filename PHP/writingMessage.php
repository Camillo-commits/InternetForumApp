<?php
$db = "forumfinal";

$nick = $_GET["nick"];
$id_tematu = $_GET["id_tematu"];
$tresc = $_GET["tresc"];
$przypiete = $_GET["przypiete"];
$id_odpowiedz = $_GET["id_odpowiedz"];
$data_wpisu = $_GET["data"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "INSERT INTO `wiadomosci` (`id_wiadomosci`, `tresc`, `data_wpisu`, `nick`, `id_tematu`, `przypiete`, `id_odpowiedz`) VALUES (NULL, '$tresc', '$data_wpisu', '$nick', '$id_tematu', '$przypiete', '$id_odpowiedz');";
	
	if(mysqli_query($conn, $q)){
		echo "Message added successfully";
	}
	else{
		echo "Adding message failed";
	}
}
?>