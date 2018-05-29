package model;

/**
 * F�r Android Push Notifications
 * Repräsentiert die Daten, die in der
 * Notification angezeigt werden sollen
 * (Titel und Inhalt)
 *
 */
public class Notification_Body {

	/** Titeltext der Notification */
	private String title;
	/** Inhalt der Notification */
	private String body;
	/** Standard Titel für eine Notification */
	public final static String DEFAULT_TITLE = "Postkasten�nderung";
	/** Notification Text wenn Postkasten geöffnet wurde */
	public final static String BODY_OPEN = "Postkasten wurde ge�ffnet!";
	/** Notification Text wenn Postkasten geschlossen wurde */
	public final static String BODY_CLOSE = "Postkasten wurde geschlossen!";

	/**
	 * Konstruktor
	 * @param title Titeltext der Notification
	 * @param body Inhalt der Notification
	 */
	public Notification_Body(String title, String body)
	{
		this.title = title;
		this.body = body;
	}	
}
