<?php
$db = "forumfinal";
$id = $_GET["id"];
$host = "192.168.43.167";

$conn = mysqli_connect($host,"ForumUser","Zaq12wsx",$db);
if($conn){
	$q = "SELECT tematy.id_tematu,tematy.tytul,tematy.id_moderatora,tematy.id_grupy,tematy.data_zalozenia,tematy.data_zakonczenia,ustawienia_dostepnosci.opis
FROM tematy
INNER JOIN ustawienia_dostepnosci
ON ustawienia_dostepnosci.id_ustawien = tematy.id_ustawien
WHERE tematy.id_grupy = '$id'
ORDER BY tematy.tytul";
	$mysqli_result = mysqli_query($conn, $q);
	
		 while($row = mysqli_fetch_assoc($mysqli_result)) {
    			echo $row["id_tematu"]. "_" . $row["id_moderatora"]. "_" . $row["tytul"]. "_" . $row["id_grupy"]. "_" . $row["data_zalozenia"]. "_" . $row["data_zakonczenia"]. "_" . $row["opis"]. "_" ;
  		}
}
mysqli_close($conn);
?>