package control;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Werkzeuge für E-Mail Versand
 * Es wird statisch auf die Methoden zugegriffen
 */
public class MailUtility {

	// Sender Daten
	/** E-Mail Adresse des Senders */
	private static final String SENDER_MAIL = "jodli0609@gmail.com";
	/** Anzeigename des Senders */
	private static final String SENDER_NAME = "Johannes Lindner";
	/** Benutzername des Senders (zur Authentifizierung beim E-Mail Dienst) */
	private static final String USER_NAME = "jodli0609";
	/** Passwort des Senders (zur Authentifizierung beim E-Mail Dienst) */
	private static final String PASSWORD = "jojo06091";

	/** E-Mail Adresse des Empfängers */
	private static final String RECIPIENT = "eller.martin29@gmail.com";

	/** Adresse des E-Mail Servers */
	private static final String HOST = "smtp.gmail.com";
	
	// Mail Betreff und Inhalt
	/** Betreff der E-Mail wenn Postkasten geöffnet wurde */
	private static final String MAIL_SUBJECT_OPEN = "Postkasten wurde geÃ¶ffnet";
	/** Betreff der E-Mail wenn Postkasten geschlossen wurde */
	private static final String MAIL_SUBJECT_CLOSE = "Postkasten wurde geschlossen";
	/** Inhalt der E-Mail wenn Postkasten geÃ¶ffnet wurde */
	private static final String MAIL_BODY_OPEN = "Postkasten wurde geÃ¶ffnet am %s um %s";
	/** Inhalt der E-Mail wenn Postkasten geschlossen wurde */
	private static final String MAIL_BODY_CLOSE = "Postkasten wurde geschlossen am %s um %s";

	/** E-Mail Server Session */
	private static Session session;

	/**
	 * Sendet eine E-Mail
	 * @param session E-Mail Server Session
	 * @param host Adresse des E-Mail Servers
	 * @param username Benutzername des Senders
	 * @param password Passwort des Senders
	 * @param toEmail E-Mail Adresse des EmpfÃ¤ngers
	 * @param subject Betreff
	 * @param body Inhalt
	 * @param filename Pfad zur Datei, die angehängt werden soll
	 * @return true = erfolgreich ; false = nicht erfolgreich
	 */
	public static boolean sendEmail(Session session,String host, String username, String password, String toEmail, String subject, String body, String filename){
		try{

			MimeMessage msg = new MimeMessage(session);
			// Content Header setzen
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			// Sender einstellen
			msg.setFrom(new InternetAddress(SENDER_MAIL, SENDER_NAME));
			msg.setReplyTo(InternetAddress.parse(SENDER_MAIL, false));
			// Betreff einstellen
			msg.setSubject(subject, "UTF-8");
			// Datum des E-Mail Versands
			msg.setSentDate(new Date());
			// Empfänger
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			// Message Body Part erstellen
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);

			// Multipart Nachricht erstellen (für Anhang)
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Anhang
			if(filename != null) { // Wenn Anhang vorhanden
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);	
			}

			// Alle Message Parts zusammenfügen
			msg.setContent(multipart);

			// Nachricht senden
			Transport trans = session.getTransport();
			trans.connect(host,username,password);
			trans.sendMessage(msg,msg.getAllRecipients());
			// Nachricht erfolgreich gesendet
			return true;
		} catch (Exception e) { // Wenn Fehler aufgetreten
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Alles für den E-Mail Versand vorbereiten
	 */
	public static void prepareMail() {

		Properties props = System.getProperties();

		// Properties setzen
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.user", USER_NAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		// Session erstellen
		session = Session.getDefaultInstance(props);
	}

	/**
	 * Wenn Postkasten geöffnet/geschlossen wurde
	 * eine Benachrichtigungs E-Mail senden
	 * @param geoeffnet true = geöffnet ; false = geschlossen
	 */
	public static void sendNotificationMail(boolean geoeffnet) {
	
		// Aktuelles Datum
		Date date = new Date();
		// Formatierung fÃ¼r Datum
		SimpleDateFormat dateFormatDatum = new SimpleDateFormat("d.M.y");
		// Formatierung fÃ¼r Uhrzeit
		SimpleDateFormat dateFormatUhrzeit = new SimpleDateFormat("H:m:s");
		
		// Datum formatieren
		String datum = dateFormatDatum.format(date);
		// Uhrzeit formatieren
		String uhrzeit = dateFormatUhrzeit.format(date);
		
		String inhalt;
		String betreff;
		// Mail Inhalt und Betreff abhängig ob geöffnet/geschlossen
		if(geoeffnet == true) {
			inhalt = MAIL_BODY_OPEN;
			betreff = MAIL_SUBJECT_OPEN;
		}
		else {
			inhalt = MAIL_BODY_CLOSE;
			betreff = MAIL_SUBJECT_CLOSE;
		}
		// Datum und Uhrzeit in Platzhalter im Inhalt der E-Mail einsetzen
		String inhalt_neu = String.format(inhalt, datum, uhrzeit);
		
        //Bei google --> Weniger Sichere Anwendungen zulassen!
        boolean x = MailUtility.sendEmail(session,HOST,USER_NAME,PASSWORD,RECIPIENT,
                betreff,inhalt_neu,
                null);

        if(x){
            System.out.println("Senden der E-Mail war erfolgreich");
        }else{
            System.out.println("Senden der E-Mail ist fehlgeschlagen");
        }
	}
}