<?php
$db = "forumfinal";
$user = $_GET["nick"];
$pass = $_GET["haslo"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "select * from uzytkownik where uzytkownik.nick like '$user' and uzytkownik.haslo like '$pass'";
	$result = mysqli_query($conn, $q);
	if(mysqli_num_rows($result) > 0){
		echo "login successfull";
	}
	else{
		echo "login failed";
	}
}
?>