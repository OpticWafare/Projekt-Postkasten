<?php

$conn = new mysqli('localhost', 'Martin', 'Post', 'postkasten');

if($conn->connect_error) {
	die("Fehler");
}

$ergebnis = $conn->query("SELECT * FROM oeffnenundschliessen ORDER BY ID DESC LIMIT 10");

?>
<html>
<head>
<meta charset="UTF-8">
<title>Postkasten</title>
<link rel='stylesheet' type='text/css' href='style.css'
</head>
<body>
	
<div class='zeile'>
	<div class='zeile_id'>
		<p>ID</p>
	</div>
	<div class='zeile_datum'>
		<p>Datum und Uhrzeit</p>
	</div>
	<div class='zeile_zustand'>
		<p>Zustand</p>
	</div>
</div>


<?php

while($temp = $ergebnis->fetch_array()) {
			
	echo "<div class='zeile'>";
		echo "<div class='zeile_id'>";
			echo "<p>";
			echo $temp[0];
			echo "</p>";
		echo "</div>";
		echo "<div class='zeile_datum'>";
			echo "<p>";
			echo $temp[1];
		echo "</div>";
		echo "<div class='zeile_zustand'>";
			echo "<p>";
			if($temp[2] == 0) {
				echo "geöffnet";
			}
			else {
				echo "geschlossen";
			}
			echo "</p>";
		echo "</div>";
	echo "</div>";

}

?>



</body>
<script>

var zeilen = document.getElementsByClassName("zeile");
for(var i = 0; i < zeilen.length; i++) {
	setTimeout(show.bind(null, zeilen[i]), i*100);
}

function show(zeile) {
	zeile.style.opacity = "1.0";
}

</script>
</html>
