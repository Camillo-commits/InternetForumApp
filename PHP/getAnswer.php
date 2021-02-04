<?php
$db = "forumfinal";
$id = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "SELECT * FROM wiadomosci
WHERE wiadomosci.id_wiadomosci = '$id'";
	$mysqli_result = mysqli_query($conn, $q);
	
		 while($row = mysqli_fetch_assoc($mysqli_result)) {
    			echo $row["id_wiadomosci"]. "_" . $row["tresc"]. "_" . $row["data_wpisu"]. "_" . $row["nick"]. "_" . $row["id_tematu"]. "_" . $row["przypiete"]. "_" . $row["id_odpowiedz"]. "_" ;
  		}
}
mysqli_close($conn);
?>