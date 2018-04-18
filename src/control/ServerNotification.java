package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class ServerNotification {

	public final static String API_URL = "https://fcm.googleapis.com/fcm/send";
	public final static String AUTH_KEY = "AAAAViPef0Y:APA91bHNs4VyrG74FfjS7d9wzSl4WPpMq7uahSdyT3Gne4mtBBhKzdQoRjhb_rPI0no9e9mslr4K_CS7KP0KOpC91X62Keu8RlKnQYXe6I2axeHgbNQ6-WNBa7PNHN7jhYKY-9PDlt_Y";
	public final static String DEFAULTDEVICEKEY = "";
	
	public static void sendNotification(String DeviceIDkey, boolean geoeffnet) throws Exception
	{
		URL url = new URL(API_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "key=" + AUTH_KEY);
		con.setRequestProperty("Content-Type", "application/json");
		
		Notification_Body body;
		
		if(geoeffnet == true)
		{
			body = new Notification_Body(Notification_Body.DEFAULT_TITLE, Notification_Body.DEFAULT_BODY1);
		} else{
			body = new Notification_Body(Notification_Body.DEFAULT_TITLE, Notification_Body.DEFAULT_BODY2);
		}
	
		Gson gson = new Gson();
		String bodyGson = gson.toJson(body);
		
//		Notification notification = new Notification(DeviceIDkey.trim(), bodyGson);
//		Notification notification = new Notification("/topics/Postkasten", bodyGson);
		Notification notification = new Notification("/topics/Postkasten", body);
		
		String notificationGson = gson.toJson(notification);
		
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(notificationGson);
		System.out.println("JSON: " + notificationGson);
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("Antwortcode " + responseCode);
	
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		in.close();
	}
}
