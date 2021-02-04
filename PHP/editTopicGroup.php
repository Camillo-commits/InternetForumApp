<?php
$db = "forumfinal";

$id = $_GET["id"];
$nazwa = $_GET["nazwa"];
$moder = $_GET["moder"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "UPDATE `grupy_tematow` SET `id_moderatora` = '$moder', `nazwa_grupy` = '$nazwa' WHERE `grupy_tematow`.`id_grupy` = '$id'";
	
	if(mysqli_query($conn, $q)){
		echo "Edit successful";
	}
	else{
		echo "Edit failed";
	}
}
?>