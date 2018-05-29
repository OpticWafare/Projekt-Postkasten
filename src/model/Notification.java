package model;

/**
 * F�r Android Push Notifications
 * Repr�sentiert das Message Objekt (innerhalb von Notification_Outer)
 * Enth�lt das Thema und die Notification selbst
 * @see Notification_Outer
 */
public class Notification {

	/** Welche Bedingungen m�ssen erf�llt sein, damit
	 * ein Ger�tt diese Benachrichtigung erh�llt? (welche Topics) */
	private String condition;
	/** Inhalt der Notification */
	private Notification_Body notification;
	
	/** Thema zu dem alle Postkastenänderungen gesendet werden sollen */
	public final static String TOPIC_ALL = "postkasten";
	/** Thema zu dem Postkasten-�ffnungen gesendet werden sollen */
	public final static String TOPIC_OPEN = "postkasten_offen";
	/** Thema zu dem Postkasten-Schlie�ungen gesendet werden sollen */
	public final static String TOPIC_CLOSE = "postkasten_geschlossen";

	/**
	 * Konstruktor
	 * @param condition Abfrage an welche Ger�te die Notification gesendet werden soll (Topics)
	 * @param data Notification Inhalt
	 */
	public Notification(String condition, Notification_Body data) 
	{
		this.condition = condition;
		this.notification = data;
	}	
}