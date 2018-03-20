# Projekt-Postkasten

Die beteiligten des Projektes und deren Kompetenzverteilungen sind:
Martin Eller --> Zuständig für das Erstellen der Datenbank und dem Schreiben der Daten in die Datenbank
Johannes Lindner --> Zuständig für die Visualisierung der aus der Datenbank entnommenen Daten.

Hauptziel:
Unser Ziel ist es mit der Hilfe eines Raspberry Pi's Model 3 B und einem Mikrotaser herauszufinden, wann 
ein Postkasten geöffnet bzw. geschlossen wird.

Mögliche Ziele: 
Erstellung von Push-Mitteilungen, welche via E-Mail und SMS übermittelt werden sollen.

Projektbeschreibung:
Es wird ein Programm geschrieben, welches dauerhaft (in einer Dauerschleife) durchlaufen wird und welches 
ständig auf eine Änderung des derzeitigen Zustandes wartet (öffnen/schließen des Postkastens). 
Falls der Postkasten geöffnet bzw. geschlossen wird sendet der Raspberry PI, welcher dauerhaft mit dem Internet verbunden sein muss,
den Zeitpunkt und das Datum in die Datenbank. Ebenfalls wird dabei automatisch hochgezählt, wie oft der Postkasten 
geöffnet bzw. geschlossen wurde. Dabei wird der Zustand des Öffnen und Schließens mit einem TinyInt (Zustand 0 oder 1) 
realisiert. Nachdem die Daten in korrekt in die Datenbank geschrieben wurden, werden diese Daten über eine noch unbestimmte Art 
der Visualisierung ausgegeben. Später, falls es der Zeitplan noch zulässt werden nach dem Öffnen und Schließens des Postkastens 
Push-Mitteilungen via E-Mail bzw. SMS erstellt.

Schnittstelle:
Die Schnittstelle in diesem Projekt stellt die Datenbank dar, in welche die Daten von Martin Eller hineingespeichert werden
und von Johannes Lindner visualisiert werden.

Abgrenzungskriterien:
Die Abgrenzungen zwischen den beiden Projektmitarbeitern sind klar definiert. Martin Eller kümmert sich alleine um die Hardware des 
Raspberry Model 3 B und dem Erstellen und Verfügungstellen der Daten in Datenbank. Ebenfalls ist er alleine für die Erstellung des 
Datenbankschemas sowie der Erstellung der Datenbank.

Johannes Lindner schreibt dann ein Programm, welches die Daten aus der Datenbakn herausnimmt und diese dann auf noch nicht defineierter 
Weise visualisiert. Ein weiterer Zuständigkeitspunkt von Johannes Lindner ist die Erstellung eines funktionierenden Prototypen

Die noch ausstehende Erstellung von Push-Mitteilungen würde von beiden umgesetzt werden.

Produktfunktionalität:
Das Produkt soll nach der Fertigstellung auch in der Praxis angewandt werden können. Dabei muss der Raspberry jedoch laufend mit Strom
und einer funktionierenden Internetverbindung versorgt werden. Für die ständige Stromversorgung würde in der Praxis eine Powerbank mit
hoher Stromkapazizät verwendet werden. Es gibt auch die Möglichkeit den Raspberry Pi mittels einem Verlängerungskabel mit Strom zu 
versorgen, jedoch ist dies sehr unpraktisch, da man ständig aufpassen müsste, dass man nicht über das Verlängerungskabel stolpert.
Trotz des Nachteils, dass die Powerbank nach einer gewissen Betriebszeit wieder aufgeladen werden muss, würden wir dennoch diese
Methode vorziehen. Man könnte sich dabei 2 Powerbanks gleichzeitig zulegen, um ständig eine zur Messung verwenden zu könne, während die
andere auflädt.
Ebenfalls müsste der Raspberry sowie die Powerbank irgendwie vor den äußeren Umwelteinflüssen geschütz werden.
Dabei könnte man den Raspberry in eine fest verschließbare Wasserschutzfolie geben. Der Vorteil dabei ist, dass die Folie schnell 
wieder geöffnet werden kann und daher der Austausch der Powerbanks leicht von der Hand geht.
Das Problem mit der Internetverbindung würde auch ohne Kable betrieben werden, da sonst das bereits oben beschriebene Problem
erneut auftauchen würde. Die beste Anwendung würde die Verwendung eines Wlans mit Verwendung eines Wlan Repeaters, welcher das 
Signal so gut verstärken sollte dass das Wlan Signal auch bei Regen stark genug sein kann um dem Raspberry zu ermöglichen den 
akteullen Zeitpunkt des Öffenen bzw. Schließens an die Datenbank zu übermitteln. 

Anmerkung: Das Projekt wird laufend in kurzen Wochenberichten dokumentiert. Ebenfalls werden dort Probleme und Schwierigkeiten, welche 
bei der Realisierung des Projektes auftreten laufend beschrieben.


