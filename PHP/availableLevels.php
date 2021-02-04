<?php
$db = "forumfinal";
$user = $_GET["nick"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "SELECT DISTINCT poziom FROM uzytkownik ORDER BY poziom;";
	$mysqli_result = mysqli_query($conn, $q);
	
		 while($row = mysqli_fetch_assoc($mysqli_result)) {
    			echo $row["poziom"]. "_";
  		}
}
mysqli_close($conn);
?>