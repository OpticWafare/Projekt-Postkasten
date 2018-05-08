package model;

/**
 * Repräsentiert das Message Objekt (innerhalb von Notification_Outer)
 * Enthält das Thema und die Notification selbst
 * @author root
 * @see Notification_Outer
 */
public class Notification {

	/** Welche Bedingungen m�ssen erf�llt sein, damit
	 * ein Gerät diese Benachrichtigung erh�lt? (welche Topics) */
	private String condition;
	private Notification_Body notification;
	
	/** Thema zu dem alle Postkasten�nderungen gesendet werden sollen */
	public final static String TOPIC_ALL = "postkasten";
	/** Thema zu dem Postkasten-�ffnungen gesendet werden sollen */
	public final static String TOPIC_OPEN = "postkasten_offen";
	/** Thema zu dem Postkasten-Schlie�ungen gesendet werden sollen */
	public final static String TOPIC_CLOSE = "postkasten_geschlossen";
	
	public Notification(String condition, Notification_Body data) 
	{
		this.condition = condition;
		this.notification = data;
	}	
}