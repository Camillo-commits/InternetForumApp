<?php
$db = "forumfinal";

$temat = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "DELETE FROM tematy
WHERE tematy.id_tematu = '$temat';";
	
	if(mysqli_query($conn, $q)){
		echo "Topic deleted successfully";
	}
	else{
		echo "Deleting topic failed";
	}
}
?>