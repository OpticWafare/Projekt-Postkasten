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
	/** Die Dauer, die das Programm abwartet, bis die n�chste Postkastenänderung
	 * bzw. Schaltersignal-�nderung eingetragen wird
	 * Ohne das Abwarten w�rde durch das Schalterprellen zu oft
	 * eine Postkasten�nderung wahrgenommen werden */
	private static final int SCHALTERPRELLLEN_DAUER = 100;
	/** Der Pin, an dem das Signal des Schalters ankommt */
	private static final Pin SCHALTER_PIN = RaspiPin.GPIO_12;

	/**
	 * Hauptprogramm
	 * @param args Laufzeitparameter (Keine)
	 */
	public static void main(String[] args) {

		// Alles für den Versand von E-Mails vorbereiten
		MailUtility.prepareMail();
		System.out.println("Mail wurde initialisiert");
		
		System.out.println("Zust�nde des Postkastens werden ausgegeben!");

		// GPIO Controller erstellen
		gpio_control = GpioFactory.getInstance();

		// GPIO Pin einstellen
		GpioPinDigitalInput gpio_Listen = gpio_control
				.provisionDigitalInputPin(SCHALTER_PIN, "gpio_Listen",
						PinPullResistance.PULL_DOWN);

		// Letzter Postkastenzustand (false = ge�ffnet; true = geschlossen)
		boolean b = false;
		// Aktuelle Zeit abspreichern
		long zeit = System.currentTimeMillis();

		// Durchgehend auf �nderungen reagieren
		while (true) {

			/* Wenn die Schalterprellzeit abgewartet wurde
			(wenn die aktuelle Zeit sp�ter ist als die Zeit des letzten �ffnens + Schalterprellzeit) */
			if (System.currentTimeMillis() > zeit) {
				if (gpio_Listen.isHigh() && b == true) // �ffnen
				{
					b = false;
					System.out.println("Postkasten wird ge�ffnet.");
					// Datenbankeintrag
					DB_Manager.insertTable(0);
					try {
						// Smartphone- und E-Mail Notification
						ServerNotification.sendNotification(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (gpio_Listen.isLow() && b == false) // Schlie�en
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
				
				// Zeit bis zur n�chsten m�glichen Aktivierung festlegen
				// (erst nach einer gewissen Zeit wieder auf Schalter�nderungen reagieren,
				// um Fehler durch Schalterprellen vorzubeugen)
				zeit = System.currentTimeMillis() + SCHALTERPRELLLEN_DAUER;
			}
		}
	}
}
