<?php
$db = "forumfinal";
$user = $_GET["nick"];
$pass = $_GET["haslo"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "UPDATE `uzytkownik` SET `haslo` = '$pass' WHERE `uzytkownik`.`nick` = '$user';";
	
	if(mysqli_query($conn, $q)){
		echo "Password changed";
	}
	else{
		echo "Password change failed";
	}
}
?>