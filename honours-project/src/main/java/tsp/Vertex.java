package tsp;
public class Vertex 
{
	double x; 
	double y;
	int id;
	String name;
	
	public void setVertex(double x, double y, int id, String name)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.name = name;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public double getY()
	{
		return this.y;
	}
	
	public double getDistance(Vertex vertex)
	{
		
		double earthRadius = 6371;
		double lon1 = this.getX() * Math.PI / 180;
		double lat1 = this.getY()* Math.PI / 180;
		double lon2 = vertex.getX()* Math.PI / 180;
		double lat2 = vertex.getY()* Math.PI / 180;
		double latDif = (lat2-lat1);
		double lonDif = (lon2-lon1);
		
		//Haversine formula
		double a = (Math.sin(latDif/2) * Math.sin(latDif/2)) + 
				Math.cos(lat1) * Math.cos(lat2) *
				(Math.sin(lonDif/2) * Math.sin(lonDif/2));
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


		
		//distance is returned in km
		double distance = (earthRadius * c);
		return distance;
		
	}
	
	@Override
	public String toString()
	{
		return(getX() + ", " + getY());
	}
}
