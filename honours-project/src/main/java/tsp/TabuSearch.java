package tsp;

import java.util.ArrayList;

public class TabuSearch {
	static String fileName;
	static int iteration = 0;
	static int maxIterations;
	static boolean verbose;
	static Tour tour;
	static int numberOfVertices = 0;
	static double distance;
	static int tabuSize; 
	static ArrayList<String> tabuList = new ArrayList<String>();
	static ArrayList<Vertex> oldTour = new ArrayList<Vertex>();
	
	public TabuSearch(String file, int it, boolean ver, int count, int size)
	{
		fileName = file;
		maxIterations = it;
		verbose = ver;
		numberOfVertices = count;
		tabuSize = size;
	}
	
	public void runTS()
	{
		System.out.println("Solver is TABU SEARCH");
		System.out.println("Filename is " + fileName);
		System.out.println("Number of vertices is " + numberOfVertices);
		System.out.println();
		
		tour = initialise();
		for(int i = 0; i < tour.tourSize(); i++)
		{
			oldTour.add(tour.getVertex(i));
		}
		System.out.println("Initial distance: " + tour.getDistance() + "km");	
		
		for(int i = 0; i < maxIterations; i++)
		{

			if(tabuList.size() < tabuSize)
			{
				tabuList.add(tour.toString());
			}
			else if(tabuList.size() == tabuSize)
			{
				tabuList.remove(0);
				tabuList.add(tour.toString());
			}
			
			tour = generateNeighbourhood(tour);	
			for(int ii = 0; ii < tour.tourSize(); ii++)
			{
				oldTour.add(tour.getVertex(ii));
			}
			iteration++;
		}
		System.out.println("Finished");
		System.out.println("Final distance: " + tour.getDistance() + "km");
		System.out.println("Solution: ");
		System.out.println(tour);
		
		if(verbose)
		{
			ArrayList<Integer> verboseTour = new ArrayList<Integer>();
			for(int i = 0; i < tour.tourSize(); i++)
			{
				verboseTour.add(tour.getVertex(i).getId());
			}
			System.out.println(verboseTour);
			System.out.println();
			verboseTour.clear();
		}
	}
	
	public Tour initialise()
	{
		Tour t = new Tour();
		t.generateIndividual();
		return t;
	}
	
	public Tour generateNeighbourhood(Tour t)
	{
		ArrayList<Tour> neighbourhood = new ArrayList<Tour>();
		double bestDist = 0;
		int index = 0;
		for(int i = 1; i < t.tourSize(); i++)
		{
			Tour newTour = new Tour();
			for(int ii = 0; ii < newTour.tourSize(); ii++)
			{
				newTour.setVertex(ii, oldTour.get(ii));
			}
			
			Vertex v0 = newTour.getVertex(0);
			Vertex v = newTour.getVertex(i);
			
			newTour.setVertex(0, v);
			newTour.setVertex(i, v0);
			
			neighbourhood.add(newTour);	
		}
		
		for(Tour tour : neighbourhood)
		{
			if(bestDist == 0 && !tabuList.contains(tour.toString()))
			{
				bestDist = tour.getDistance();
				index = neighbourhood.indexOf(tour);
			}
			else if(tour.getDistance() < bestDist && !tabuList.contains(tour.toString()))
			{
				bestDist = tour.getDistance();
				index = neighbourhood.indexOf(tour);
			}
		}
		
		return neighbourhood.get(index);

	}

}
