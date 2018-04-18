package control;

public class Notification_Body {

	private String title;
	private String body;
	//private String message;
	public final static String DEFAULT_TITLE = "Postkastenänderung";
	public final static String DEFAULT_BODY1 = "Postkasten wurde geöffnet!";
	public final static String DEFAULT_BODY2 = "Postkasten wurde geschlossen!";
	
	public Notification_Body(String title, String body)
	{
		//this.message = body;
		this.title = title;
		this.body = body;
	}
	
	
}
