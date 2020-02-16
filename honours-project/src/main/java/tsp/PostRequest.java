package tsp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostRequest 
{
	public void sendPost(Tour t) throws Exception
	{
		String points = "{ \"points\": [";
		for (int i = 0; i < t.tourSize(); i++)
		{
			if(i == t.tourSize()-1)
			{
				points += "[" + t.getVertex(i).getY() + "," + t.getVertex(i).getX() + "]]";
			}
			else
			{
				points += "[" + t.getVertex(i).getY() + "," + t.getVertex(i).getX() + "],";
			}
		}
		
		URL url = new URL ("http://localhost:8989/");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);
		String jsonInputString = points;
		
		try(OutputStream os = con.getOutputStream()) {
		    byte[] input = jsonInputString.getBytes("utf-8");
		    os.write(input, 0, input.length);           
		}
		
		try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println(response.toString());
				}
		

	}
	
}
