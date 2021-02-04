<?php
$db = "forumfinal";

$id_wiadomosci = $_GET["id_wiadomosci"];

$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "INSERT INTO `wiadomosci_do_potwierdzenia` (`id_wiadomosci`) VALUES ('$id_wiadomosci');";
	
	if(mysqli_query($conn, $q)){
		echo "Message added successfully";
	}
	else{
		echo "Adding message failed";
	}
}
?>