package tsp;
import java.util.ArrayList;

public class TourManager 
{

	//Holds the cities
	public static ArrayList<Vertex> destinationVertices = new ArrayList<Vertex>();

	//Adds a vertex to the list
	public static void addVertex(Vertex vertex)
	{
		destinationVertices.add(vertex);
	}
	
	//Returns a vertex of a given index
	public static Vertex getVertex(int index)
	{
		return (Vertex)destinationVertices.get(index);
	}
	
	public static int numberOfVertices()
	{
		return destinationVertices.size();
	}

}
