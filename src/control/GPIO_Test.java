package control;

import model.DB_Manager;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Hauptprogramm
 * GPIO = General Purpose Input Output
 */
public class GPIO_Test {

	/** Controller zum Ansteuern und Lesen der GPIO Pins des Raspberry Pi's */
	static GpioController gpio_control;
	/** Die Dauer, die das Programm abwartet, bis die nächste PostkastenÃ¤nderung
	 * bzw. Schaltersignal-Ãnderung eingetragen wird
	 * Ohne das Abwarten würde durch das Schalterprellen zu oft
	 * eine Postkastenänderung wahrgenommen werden */
	private static final int SCHALTERPRELLLEN_DAUER = 100;
	/** Der Pin, an dem das Signal des Schalters ankommt */
	private static final Pin SCHALTER_PIN = RaspiPin.GPIO_12;

	/**
	 * Hauptprogramm
	 * @param args Laufzeitparameter (Keine)
	 */
	public static void main(String[] args) {

		// Alles fÃ¼r den Versand von E-Mails vorbereiten
		MailUtility.prepareMail();
		System.out.println("Mail wurde initialisiert");
		
		System.out.println("Zustände des Postkastens werden ausgegeben!");

		// GPIO Controller erstellen
		gpio_control = GpioFactory.getInstance();

		// GPIO Pin einstellen
		GpioPinDigitalInput gpio_Listen = gpio_control
				.provisionDigitalInputPin(SCHALTER_PIN, "gpio_Listen",
						PinPullResistance.PULL_DOWN);

		// Letzter Postkastenzustand (false = geöffnet; true = geschlossen)
		boolean b = false;
		// Aktuelle Zeit abspreichern
		long zeit = System.currentTimeMillis();

		// Durchgehend auf Ãnderungen reagieren
		while (true) {

			/* Wenn die Schalterprellzeit abgewartet wurde
			(wenn die aktuelle Zeit später ist als die Zeit des letzten Öffnens + Schalterprellzeit) */
			if (System.currentTimeMillis() > zeit) {
				if (gpio_Listen.isHigh() && b == true) // Öffnen
				{
					b = false;
					System.out.println("Postkasten wird geöffnet.");
					// Datenbankeintrag
					DB_Manager.insertTable(0);
					try {
						// Smartphone- und E-Mail Notification
						ServerNotification.sendNotification(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (gpio_Listen.isLow() && b == false) // Schließen
				{
					b = true;
					System.out.println("Postkasten wird geschlossen.");
					// Datenbankeintrag
					DB_Manager.insertTable(1);
					try {
						// Smartphone- und E-Mail Notification
						ServerNotification.sendNotification(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				// Zeit bis zur nächsten möglichen Aktivierung festlegen
				// (erst nach einer gewissen Zeit wieder auf Schalteränderungen reagieren,
				// um Fehler durch Schalterprellen vorzubeugen)
				zeit = System.currentTimeMillis() + SCHALTERPRELLLEN_DAUER;
			}
		}
	}
}
