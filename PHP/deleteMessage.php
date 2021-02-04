<?php
$db = "forumfinal";

$id = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "DELETE FROM wiadomosci
WHERE wiadomosci.id_wiadomosci = '$id';";
	
	if(mysqli_query($conn, $q)){
		echo "Message deleted successfully";
	}
	else{
		echo "Deleting message failed";
	}
}
?>