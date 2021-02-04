<?php
$db = "forumfinal";
$user = $_GET["nick"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){

	$q = "SELECT uzytkownik.id_uprawnien, uprawnienia.opis
		FROM uzytkownik
		INNER JOIN uprawnienia
		ON uzytkownik.id_uprawnien = uprawnienia.id_uprawnien
		WHERE uzytkownik.nick = '$user';";
	$mysqli_result = mysqli_query($conn,$q);
	
		while($row = mysqli_fetch_assoc($mysqli_result)){
			echo $row["id_uprawnien"]. "-" . $row["opis"]; 
		}
	
	
}
mysqli_close($conn);
?>



