package tsp;

public class URL_Builder 
{
	Tour tour;
	
	public URL_Builder(Tour t)
	{
		tour = t;
	}
	
	public String getURL()
	{
		String UrlStart = "http://localhost:8989/maps/?";
		String points = "";
		String UrlEnd = "locale=en-GB&vehicle=car&weighting=fastest&elevation=false&use_miles=false&layer=Omniscale";
		
		for(int i = 0; i<tour.tourSize(); i++)
		{
			Vertex vertex = tour.getVertex(i);
			points += "point="+ vertex.getX() + "%2C" + vertex.getY() + "&"; 
		}
		points += "point="+ tour.getVertex(0).getX()+ "%2C" + tour.getVertex(0).getY() + "&";
		
		String link = UrlStart + points + UrlEnd;
		return link;
	}
}
