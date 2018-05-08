package control;

import model.DB_Manager;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;

public class GPIO_Test {

	static GpioController gpio_control;

	public static void main(String[] args) {
		System.out.println("Zustände des Postkastens werden ausgegeben!");
		
		gpio_control = GpioFactory.getInstance();

		GpioPinDigitalInput gpio_Listen = gpio_control
				.provisionDigitalInputPin(RaspiPin.GPIO_12, "gpio_Listen",
						PinPullResistance.PULL_DOWN);

		boolean b = false;
		long zeit = System.currentTimeMillis();

		while (true) {
			if (System.currentTimeMillis() > zeit) {
				if (gpio_Listen.isHigh() && b == true) // Öfnnen
				{
					b = false;
					System.out.println("Postkasten wird ge��ffnet.");
					DB_Manager.insertTable(0);
					try {
						ServerNotification.sendNotification(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (gpio_Listen.isLow() && b == false) // Schließen
				{
					b = true;
					System.out.println("Postkasten wird geschlossen.");
					DB_Manager.insertTable(1);
					try {
						ServerNotification.sendNotification(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				zeit = System.currentTimeMillis() + 100;
			}
		}
	}
}
