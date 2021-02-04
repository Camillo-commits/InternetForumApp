<?php
$db = "forumfinal";

$nick = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "DELETE FROM uzytkownik
WHERE uzytkownik.nick= '$nick';";
	
	if(mysqli_query($conn, $q)){
		echo "User deleted successfully";
	}
	else{
		echo "Deleting user failed";
	}
}
?>