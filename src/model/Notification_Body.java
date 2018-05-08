package model;

/**
 * Repräsentiert die Daten, die in der
 * Notification angezeigt werden sollen
 * (Titel und Inhalt)
 * @author root
 *
 */
public class Notification_Body {

	private String title;
	private String body;
	//private String message;
	/** Standard Titel für eine Notification */
	public final static String DEFAULT_TITLE = "Postkasten�nderung";
	/** Notification Text wenn Postkasten geöffnet wurde */
	public final static String BODY_OPEN = "Postkasten wurde ge�ffnet!";
	/** Notification Text wenn Postkasten geschlossen wurde */
	public final static String BODY_CLOSE = "Postkasten wurde geschlossen!";
	
	public Notification_Body(String title, String body)
	{
		//this.message = body;
		this.title = title;
		this.body = body;
	}	
}
