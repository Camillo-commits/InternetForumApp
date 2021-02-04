<?php
$db = "forumfinal";

$id = $_GET["id"];
$pin = $_GET["przypiete"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "UPDATE `wiadomosci` SET `przypiete` = '$pin' WHERE `wiadomosci`.`id_wiadomosci` = '$id'";
	
	if(mysqli_query($conn, $q)){
		echo "Pinning successfull";
	}
	else{
		echo "Pinning failed";
	}
}
?>