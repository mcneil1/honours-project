package tsp;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collections;
import java.util.Random;
=======
>>>>>>> 668bbb958ad3ea8cc4a8a69959e9c9f3d36826c3

public class TabuSearch {
	static String fileName;
	static int iteration = 0;
	static int maxIterations;
	static boolean verbose;
	static Tour tour;
	static int numberOfVertices = 0;
	static double distance;
<<<<<<< HEAD
	static int tabuSize = 1000; 
	static int neighbourhoodSize = 1000;
=======
	static int tabuSize; 
>>>>>>> 668bbb958ad3ea8cc4a8a69959e9c9f3d36826c3
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
			
<<<<<<< HEAD
			double dist = tour.getDistance();
			tour = generateNeighbourhood(tour);
			double newDist = tour.getDistance();
			
			if(verbose)
				if(newDist<dist)
				{
					System.out.println("Iteration " + iteration + " found a better solution: " + tour.getDistance() +"km");
				}
				else
				{
					System.out.println("Iteration " + iteration + " accepted a worse solution: " + tour.getDistance() +"km");
				}
			
			iteration++;
			
			
=======
			tour = generateNeighbourhood(tour);	
			for(int ii = 0; ii < tour.tourSize(); ii++)
			{
				oldTour.add(tour.getVertex(ii));
			}
			iteration++;
>>>>>>> 668bbb958ad3ea8cc4a8a69959e9c9f3d36826c3
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
<<<<<<< HEAD
		
		URL_Builder myURL = new URL_Builder(tour);
		myURL.getURL();
		
=======
>>>>>>> 668bbb958ad3ea8cc4a8a69959e9c9f3d36826c3
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
<<<<<<< HEAD
		ArrayList<Double> distances = new ArrayList<Double>();
		
		while(neighbourhood.size() < neighbourhoodSize)
		{

				Tour newTour = new Tour();
				for(int oldTour = 0; oldTour < t.tourSize(); oldTour++)
				{
					newTour.setVertex(oldTour, t.getVertex(oldTour));
				}
				
				Random random = new Random();
				int index1 = random.nextInt(numberOfVertices);
				int index2 = random.nextInt(numberOfVertices);
				
				Vertex v1 = newTour.getVertex(index1);
				Vertex v2 = newTour.getVertex(index2);

				newTour.setVertex(index1,v2);
				newTour.setVertex(index2,v1);

				
				distances.add(newTour.getDistance());
				neighbourhood.add(newTour);
			}
		
		
		
		Collections.sort(distances);
		int distIndex = 0;
		for(int i = 0; i<neighbourhood.size(); i++)
		{
			Tour tour = neighbourhood.get(i);
			if (tour.getDistance() == distances.get(distIndex) && !tabuList.contains(tour.toString()))
			{
				t = tour;
				break;
			}
			else if(tour.getDistance() == distances.get(distIndex) && tabuList.contains(tour.toString()))
			{
				distIndex++;
				i = 0;
			}
		}
		/*
		for(Tour tour : neighbourhood)
		{
			if(tour.getDistance() < bestDist && !tabuList.contains(tour.toString()))
			{
				bestDist = tour.getDistance();
				t = tour;
			}
			
		}
		*/
		return t;
		
=======
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

>>>>>>> 668bbb958ad3ea8cc4a8a69959e9c9f3d36826c3
	}

}
