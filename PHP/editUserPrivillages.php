<?php
$db = "forumfinal";

$id = $_GET["id"];
$poziom = $_GET["level"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "UPDATE `uzytkownik` SET `id_uprawnien` = '$poziom' WHERE `uzytkownik`.`nick` = '$id'";
	
	if(mysqli_query($conn, $q)){
		echo "User edited successfully";
	}
	else{
		echo "Editing user failed";
	}
}
?>