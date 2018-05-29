<?php

// MySQL Verbindung aufbauen
$conn = new mysqli('localhost', 'Martin', 'Post', 'postkasten');

// Wenn Fehler -> Abbrechen
if($conn->connect_error) {
	die("Fehler");
}
// Die letzten 10 Postkastenänderungen abfragen
$ergebnis = $conn->query("SELECT * FROM oeffnenundschliessen ORDER BY ID DESC LIMIT 10");

?>
<html>
<head>
<meta charset="UTF-8">
<title>Postkasten</title>
<link rel='stylesheet' type='text/css' href='style.css'
</head>
<body>
	
	<h1>Postkasten</h1>
	<hr>
<div class='zeile ersteZeile'>
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

// Alle Postkastenänderungen ausgeben
while($temp = $ergebnis->fetch_array()) {
			
	echo "<div class='zeile'>"; // Neue Zeile
		echo "<div class='zeile_id'>"; // ID der Änderung
			echo "<p>";
			echo $temp[0];
			echo "</p>";
		echo "</div>";
		echo "<div class='zeile_datum'>"; // Zeitpunkt der Änderung
			echo "<p>";
			echo $temp[1];
		echo "</div>";
		echo "<div class='zeile_zustand'>"; // Änderungstyp (Öffnen oder Schließen) mit jeweiligen Bild
			echo "<p>";
			if($temp[2] == 0) {
				echo "geöffnet";
				echo "<img src='Pictures/mailbox_opened.png' class='openedClosedPic'>";
			}
			else {
				echo "geschlossen";
				echo "<img src='Pictures/mailbox_closed.png' class='openedClosedPic'>";
			}
			echo "</p>";
		echo "</div>";
	echo "</div>";

}

?>

<!-- Fußleiste -->
<div class=footer>
	<p>© Martin Eller: <a href='mailto:martin.eller@students.htlinn.ac.at'>martin.eller@students.htlinn.ac.at</a></p>
	<p>© Johannes Lindner: <a href='mailto:johannes.lindner@students.htlinn.ac.at'>johannes.lindner@students.htlinn.ac.at</a></p>
</div>
</body>
<!-- JavaScript -->
<script>

    // Alle Zeilen holen
var zeilen = document.getElementsByClassName("zeile");
// Für jede Zeile
for(var i = 0; i < zeilen.length; i++) {
    // Nacheinander die einzelnen Zeilen anzeigen
    // Zwischen dem Anzeigen der Zeilen immer 100 Millisekunden warten
    // Die show Funktion wird an einen Timeout gebunden,
    // damit das Anzeigen der Zeile erst nach einer gewissen Zeitdauer geschieht
	setTimeout(show.bind(null, zeilen[i]), i*100);
}

// Angegebene Zeile (HTML Element) anzeigen
function show(zeile) {
	zeile.style.opacity = "1.0";
}

</script>
</html>
