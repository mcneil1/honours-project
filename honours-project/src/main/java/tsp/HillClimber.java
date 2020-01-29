package tsp;

import java.util.ArrayList;

public class HillClimber {
	//Hill Climber parameters
	static String fileName;
	static int iteration = 0;
	static int maxIterations;
	static boolean verbose;
	static Tour tour;
	static ArrayList<Vertex> oldTour = new ArrayList<Vertex>();
	static int numberOfVertices = 0;
	static double distance;
	
	public HillClimber(String file, int it, boolean ver, int count)
	{
		fileName = file;
		maxIterations = it;
		verbose = ver;
		numberOfVertices = count;
	}
	
	public void runHC()
	{
		System.out.println("Solver is HILL CLIMBER");
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
			distance = tour.getDistance();
			swapOperator(tour);
			iteration++;
			
		}
		
		System.out.println("Finished");
		System.out.println("Final distance: " + tour.getDistance() + "km");
		System.out.println("Solution: ");
		System.out.println(tour);

		if(verbose)
		{
			ArrayList<Integer> verboseTour = new ArrayList<Integer>();
<<<<<<< HEAD
			//ArrayList<String> nameTour = new ArrayList<String>();
			for(int i = 0; i < tour.tourSize(); i++)
			{
				verboseTour.add(tour.getVertex(i).getId());
				//nameTour.add(tour.getVertex(i).getName());
			}
			System.out.println(verboseTour);
			//System.out.println(nameTour);
			System.out.println();
			verboseTour.clear();
			//nameTour.clear();
		}
		
		URL_Builder myURL = new URL_Builder(tour);
		myURL.getURL();
		
=======
			for(int i = 0; i < tour.tourSize(); i++)
			{
				verboseTour.add(tour.getVertex(i).getId());
			}
			System.out.println(verboseTour);
			System.out.println();
			verboseTour.clear();
		}
>>>>>>> 668bbb958ad3ea8cc4a8a69959e9c9f3d36826c3
	}
	
	public Tour initialise()
	{
		Tour t = new Tour();
		t.generateIndividual();
		return t;
	}
	
	public void swapOperator(Tour tour)
	{

		//Get tour positions to swap
		int tourPos1 = (int) (tour.tourSize() * Math.random());
		int tourPos2 = (int) (tour.tourSize() * Math.random());
		while(tourPos1 == tourPos2)
		{
			tourPos2 = (int) (tour.tourSize() * Math.random());
		}
		Vertex vertex1 = tour.getVertex(tourPos1);
		Vertex vertex2 = tour.getVertex(tourPos2);
		
		//swap positions
		tour.setVertex(tourPos2, vertex1);
		tour.setVertex(tourPos1, vertex2);
		double newDistance = tour.getDistance();
		
		if(newDistance < distance)
		{
			oldTour.clear();
			for(int i = 0; i < tour.tourSize(); i++)
			{
				oldTour.add(tour.getVertex(i));
			}
			if(verbose)
			{
				System.out.println("Iteration " + iteration + " found a better solution: " + tour.getDistance() +"km");
			}
		}
		else
		{
			for(int i = 0; i < tour.tourSize(); i++)
			{
				tour.setVertex(i, oldTour.get(i));
			}
		}
		
	}
}
