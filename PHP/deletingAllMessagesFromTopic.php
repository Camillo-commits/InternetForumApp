<?php
$db = "forumfinal";

$temat = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "DELETE FROM wiadomosci
WHERE wiadomosci.id_tematu = '$temat';";
	
	if(mysqli_query($conn, $q)){
		echo "Messages deleted successfully";
	}
	else{
		echo "Deleting messages failed";
	}
}
?>