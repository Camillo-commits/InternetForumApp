<?php
$db = "forumfinal";

$id_moderatora = $_GET["nick"];
$id_grupy = $_GET["id_grupy"];
$tytul = $_GET["tytul"];
$ustawienia = $_GET["ustawienia"];
$data_zalozenia = $_GET["data_zalozenia"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "INSERT INTO `tematy` (`id_moderatora`, `tytul`, `id_grupy`, `data_zalozenia`, `id_ustawien`) VALUES ('$id_moderatora', '$tytul', '$id_grupy', '$data_zalozenia', '$ustawienia');";
	
	if(mysqli_query($conn, $q)){
		echo "Topic added successfully";
	}
	else{
		echo "Topic message failed";
	}
}
?>