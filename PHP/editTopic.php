<?php
$db = "forumfinal";

$id = $_GET["id"];
$tytul = $_GET["tytul"];
$nick = $_GET["nick"];
$data_zakonczenia = $_GET["data"];
$id_ustawien = $_GET["id_ustawien"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "UPDATE `tematy` SET `id_moderatora` = '$nick', `tytul` = '$tytul', `data_zakonczenia` = '$data_zakonczenia', `id_ustawien` = '$id_ustawien' WHERE `tematy`.`id_tematu` = '$id'";
	
	if(mysqli_query($conn, $q)){
		echo "Topic edited successfully";
	}
	else{
		echo "Editing Topic failed";
	}
}
?>