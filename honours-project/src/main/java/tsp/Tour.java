package tsp;
import java.util.ArrayList;
import java.util.Collections;

public class Tour 
{
	//Holds our tour
	private ArrayList<Vertex> tour = new ArrayList<Vertex>();
	//Cache
	private double fitness = 0;
	private double distance = 0;
	
	//Construct a blank tour
	public Tour()
	{
		for(int i = 0; i < TourManager.numberOfVertices(); i++)
		{
			tour.add(null);
		}	
	}
	
	public Tour(ArrayList<Vertex> tour)
	{
		this.tour = tour;
	}
	
	public void generateIndividual() 
	{
		//Loop through all our vertices and add them to our tour
		for(int vertexIndex = 0; vertexIndex < TourManager.numberOfVertices(); vertexIndex++)
		{
			setVertex(vertexIndex, TourManager.getVertex(vertexIndex));
		}
		//Randomly reorder the tour
		Collections.shuffle(tour);
	}
	
	//Gets a vertex from the tour
	public Vertex getVertex(int tourPosition)
	{
		return (Vertex)tour.get(tourPosition);
	}
	
	//Sets a city in a certain position
	public void setVertex(int tourPosition, Vertex vertex)
	{
		tour.set(tourPosition, vertex);
		//If the tours has been altered we need to reset the fitness and distance 
		fitness = 0;
		distance = 0;
	}
	
	public double getFitness()
	{
		if(fitness == 0) 
		{
			fitness = 1/(double)getDistance();
		}
		return fitness;
	}
	
	public double getDistance()
	{
		if(distance == 0)
		{
			double tourDistance = 0;
			//Loop through the tour
			for(int vertexIndex = 0; vertexIndex < tourSize(); vertexIndex++)
			{
				//Get the vertex we're currently at
				Vertex fromVertex = getVertex(vertexIndex);
				//Get the vertex we're travelling to next
				Vertex nextVertex;
				//Check we're not at the last index, if we are set next vertex to first vertex
				if(vertexIndex+1 < tourSize())
				{
					nextVertex = getVertex(vertexIndex+1);
				}
				else
				{
					nextVertex = getVertex(0);
				}
				//Get the distance between the two cities
				tourDistance += fromVertex.getDistance(nextVertex);
			}
			distance = tourDistance;
		}
		return distance;
	}
	
	//Get number of vertices in tour
	public int tourSize()
	{
		return tour.size();
	}
	
	//Check if the tour contains a city
	public boolean containsVertex(Vertex vertex)
	{
		return tour.contains(vertex);
	}
	
	@Override 
	public String toString()
	{
		String geneString = "|";
		for(int i= 0; i < tourSize(); i++)
		{
			geneString += getVertex(i) + "|";
		}
		return geneString;
	}
}
