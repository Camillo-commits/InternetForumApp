<?php
$db = "forumfinal";

$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "SELECT * FROM uzytkownik
ORDER BY `nick`";
	$mysqli_result = mysqli_query($conn, $q);
	
		 while($row = mysqli_fetch_assoc($mysqli_result)) {
    			echo $row["nick"]. "_" . $row["imie"]. "_" . $row["nazwisko"]. "_" . $row["mail"]. "_" . $row["poziom"]. "_" . $row["id_uprawnien"]. "_" . $row["haslo"]. "_" ;
  		}
}
mysqli_close($conn);
?>