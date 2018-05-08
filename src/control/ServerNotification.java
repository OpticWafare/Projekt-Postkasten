package control;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import model.Notification;
import model.Notification_Body;
import model.Notification_Outer;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;

public class ServerNotification {

	public final static String API_URL = "https://fcm.googleapis.com/v1/projects/postkasten-4c221/messages:send";
	
	public final static String[] SCOPES = new String[]
			{"https://www.googleapis.com/auth/firebase.messaging"};
	
	public static void sendNotification(boolean geoeffnet) throws Exception
	{
		// Verbindung aufbauen
		HttpURLConnection con = getConnection();
		
		Notification_Body body;
		// Themen, an die die Benachrichtigung gesendet werden soll
		ArrayList<String> topics = new ArrayList<String>();
		// Alle Benachrichtungen werden an dieses Topic gesendet:
		topics.add(Notification.TOPIC_ALL);
		// Notification Inhalt und Topics aufbauen (je nachdem ob Postkasten geöffnet oder geschlossen wurde)
		if(geoeffnet == true)
		{
			body = new Notification_Body(Notification_Body.DEFAULT_TITLE, Notification_Body.BODY_OPEN);
			topics.add(Notification.TOPIC_OPEN);
		} else{
			body = new Notification_Body(Notification_Body.DEFAULT_TITLE, Notification_Body.BODY_CLOSE);
			topics.add(Notification.TOPIC_CLOSE);
		}
	
		// Gson Objekt zur Umwandlung der Java Objekte in JSON
		Gson gson = new Gson();
		
		// Topics in Condition-String umwandeln
		String topicString = topicsToString(topics);
		// Notification Objekte aufbauen
		Notification notification = new Notification(topicString, body);
		Notification_Outer notificationOuter = new Notification_Outer(notification);
		
		// Gesamte Notification in JSON umwandeln
		String notificationGson = gson.toJson(notificationOuter);
		
		
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		// Notification über die Verbindung an den Server senden
		wr.write(notificationGson);
		System.out.println("JSON: " + notificationGson);
		wr.flush();
		wr.close();
		
		// Antwortcode empfangen
		int responseCode = con.getResponseCode();
		System.out.println("Antwortcode " + responseCode);
	
		// Eventuell Rückmeldungen ausgeben
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();
		// Verbindung schließen
		con.disconnect();
	}
	
	/**
	 * Wandelt mehrere Topic-String in eine Condition um
	 * @param topics Topics als Strings
	 * @return Condition-String
	 */
	private static String topicsToString(ArrayList<String> topics) {
		String str = "";
		for(int i = 0; i < topics.size(); i++) {
			if(i > 0) str += " || ";
			str += "'"+topics.get(i)+"' in topics";
		}
		return str;
	}
	
	/**
	 * Gibt den aktuell gültigen Zugriffs-Token für die Kommunikation mit
	 * dem Google Messaging Server zurück
	 * @return
	 * @throws IOException
	 */
	private static String getAccessToken() throws IOException {
		GoogleCredential googleCredential = GoogleCredential
				.fromStream(new FileInputStream(getAuthFile()))
				.createScoped(Arrays.asList(SCOPES));
		googleCredential.refreshToken();
		System.out.println("Token: " + googleCredential.getAccessToken());
		return googleCredential.getAccessToken();
	}
	
	/**
	 * Baut eine neue Verbindung zum Google Messaging Server auf
	 * Enthält Header-Daten (Authorization, Verbindungsart, ...) 
	 * @return neue Verbindung zum Google Server
	 * @throws Exception
	 */
	private static HttpURLConnection getConnection() throws Exception {
		URL url = new URL(API_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		
		con.setRequestMethod("POST");
		// Authentifizierung mit Zugriffs-Token
		con.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		con.setRequestProperty("Content-Type", "application/json; UTF-8");
		
		return con;
	}
	
	/**
	 * 
	 * @return Pfad zur Authentifizierungs-Datei (JSON)
	 */
	private static String getAuthFile() {
		return ServerNotification.class.getResource("/model/authorization.json").toString().replace("file:", "");
	}
	
}
