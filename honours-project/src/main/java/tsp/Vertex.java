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
		//phi = lat, lamda = lon
		double earthRadius = 6371;
		double lon1 = this.getX();
		double lat1 = this.getY();
		double lon2 = vertex.getX();
		double lat2 = vertex.getY();
		
		//central subtended angle formula
		double a = Math.sin(lat1);
		double b = Math.sin(lat2);
		double c = Math.cos(lat1);
		double d = Math.cos(lat2);
		double e = Math.cos(Math.abs(lon1 - lon2));
		
		double alpha = Math.acos((a*b) + (c*d*e));
		
		//distance is returned in kl
		double distance = (2 * Math.PI * earthRadius * (alpha/360));
		return distance;
		
	}
	
	@Override
	public String toString()
	{
		return(getX() + ", " + getY());
	}
}
