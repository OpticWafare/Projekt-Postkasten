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
	gpio_control = GpioFactory.getInstance();
	
	
	GpioPinDigitalInput gpio_Listen = gpio_control.provisionDigitalInputPin(RaspiPin.GPIO_12,           
            "gpio_Listen",                   
            PinPullResistance.PULL_DOWN);
	
	boolean b = false;
	DB_Manager.createDatabase();
	DB_Manager.createTable();
	while(true)
	{
		if(gpio_Listen.isLow() && b == true) 
		{
			System.out.println("Postkasten wird geöffnet.");
			b = false;
			DB_Manager.insertTable(0); 
		} else if(gpio_Listen.isHigh() && b == false) 
		{
			System.out.println("Postkasten wird geschlossen.");
			b = true;
			DB_Manager.insertTable(1); 
		}
		
	}
}
}
