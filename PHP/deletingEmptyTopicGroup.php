<?php
$db = "forumfinal";

$temat = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "DELETE FROM grupy_tematow
WHERE grupy_tematow.id_grupy = '$temat';";
	
	if(mysqli_query($conn, $q)){
		echo "Topic Group deleted successfully";
	}
	else{
		echo "Deleting topic group failed";
	}
}
?>