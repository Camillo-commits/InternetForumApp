<?php
$db = "forumfinal";

$nick = $_GET["nick"];
$temat = $_GET["tytul"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "INSERT INTO `grupy_tematow` (`nazwa_grupy`,`id_moderatora`) VALUES ('$temat', '$nick');";
	
	if(mysqli_query($conn, $q)){
		echo "Topic Group added successfully";
	}
	else{
		echo "Adding Topic Group failed";
	}
}
?>