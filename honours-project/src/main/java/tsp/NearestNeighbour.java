package tsp;

import java.util.ArrayList;
import java.util.HashMap;

public class NearestNeighbour
{
	static String fileName;
	static boolean verbose;
	static Tour tour;
	static int numberOfVertices = 0;
	static HashMap<Integer, Double> distances = new HashMap<Integer,Double>();
	static ArrayList<Vertex> Vertices = new ArrayList<Vertex>();
	double bestDist = 0;
	Vertex nextVertex;
	int index = 0;
	Vertex CurrentVertex;
	
	public NearestNeighbour(String file, boolean ver, int count)
	{
		fileName = file;
		verbose = ver;
		numberOfVertices = count;
	}
	
	public void runNN()
	{
		System.out.println("Solver is NEAREST NEIGHBOUR");
		System.out.println("Filename is " + fileName);
		System.out.println("Number of vertices is " + numberOfVertices);
		System.out.println();
		
		for(Vertex v : TourManager.destinationVertices)
		{
			Vertices.add(v);
		}
		
		tour = initialise();
		System.out.println("Initial distance: " + tour.getDistance() + "km");
		System.out.println();

		CurrentVertex = tour.getVertex(0);
		Vertices.remove(CurrentVertex);
		
		for(int i = 0; i < numberOfVertices-1; i++)
		{
			//Get the distances for all neighbours
			for(Vertex v: Vertices)
			{
				double dist = CurrentVertex.getDistance(v);
				//make the first distance = best distance
				if(bestDist == 0)
				{
					bestDist = dist;
					nextVertex = v;
				}
				//if a distance is shorter than the current best update bestDist
				else if(dist < bestDist)
				{
					bestDist = dist;
					nextVertex = v;
				}
			}
			//increment index and reset bestDist
			index++;
			bestDist = 0;
			
			//set the next vertex as the nearest unvisited neighbour
			tour.setVertex(index, nextVertex);
			if(verbose)
			{
				System.out.println("The nearest unvistied neighbour to " + CurrentVertex.getName() + " is " + nextVertex.getName());
			}
			
			//remove the visited neighbour from Vertices and distance list and make nextVertex = CurrentVertex
			Vertices.remove(nextVertex);
			distances.remove(nextVertex.getId());
			
			CurrentVertex = nextVertex;
			
			
		}

		
		

		System.out.println();
		System.out.println("Finished");
		System.out.println("Final distance: " + tour.getDistance() + "km");
		System.out.println("Solution: ");
		System.out.println(tour);
		
		System.out.println();
		URL_Builder myURL = new URL_Builder(tour);
		myURL.getURL();
	}
	
	public Tour initialise()
	{
		Tour t = new Tour();
		t.generateIndividual();
		return t;
	}
}
