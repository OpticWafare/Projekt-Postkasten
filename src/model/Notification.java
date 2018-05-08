package model;

/**
 * ReprÃ¤sentiert das Message Objekt (innerhalb von Notification_Outer)
 * EnthÃ¤lt das Thema und die Notification selbst
 * @author root
 * @see Notification_Outer
 */
public class Notification {

	/** Welche Bedingungen müssen erfüllt sein, damit
	 * ein GerÃ¤t diese Benachrichtigung erhält? (welche Topics) */
	private String condition;
	private Notification_Body notification;
	
	/** Thema zu dem alle Postkastenänderungen gesendet werden sollen */
	public final static String TOPIC_ALL = "postkasten";
	/** Thema zu dem Postkasten-Öffnungen gesendet werden sollen */
	public final static String TOPIC_OPEN = "postkasten_offen";
	/** Thema zu dem Postkasten-Schließungen gesendet werden sollen */
	public final static String TOPIC_CLOSE = "postkasten_geschlossen";
	
	public Notification(String condition, Notification_Body data) 
	{
		this.condition = condition;
		this.notification = data;
	}	
}