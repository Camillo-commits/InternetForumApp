<?php
$db = "forumfinal";

$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "SELECT grupy_tematow.id_grupy, grupy_tematow.nazwa_grupy, uzytkownik.nick
FROM grupy_tematow
INNER JOIN uzytkownik
ON uzytkownik.nick = grupy_tematow.id_moderatora;";
	$mysqli_result = mysqli_query($conn, $q);
	
		 while($row = mysqli_fetch_assoc($mysqli_result)) {
    			echo $row["id_grupy"]. "_" . $row["nick"]. "_" . $row["nazwa_grupy"]. "_";
  		}
}
mysqli_close($conn);
?>