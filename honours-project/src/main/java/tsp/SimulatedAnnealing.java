package tsp;

import java.util.ArrayList;

public class SimulatedAnnealing 
{
	//Simulated annealing params
	static String fileName;
	static int iteration = 0;
	static int maxIterations;
	static boolean verbose;
	static double startTemperature;
	static double temperature;
	static double endTemperature;
	static double coolingRate;
	static Tour tour;
	static ArrayList<Vertex> oldTour = new ArrayList<Vertex>();;
	static int numberOfVertices = 0;
	static double distance;
	static double fitness;
	
	public SimulatedAnnealing(String file, int it, boolean ver, double sT, double eT, double cr, int count)
	{
		fileName = file;
		maxIterations = it;
		verbose = ver;
		startTemperature = sT;
		endTemperature = eT;
		coolingRate = cr;
		numberOfVertices = count;
	}
	
	public void runSA()
	{
		System.out.println("Solver is SIMMULATED ANNEALING");
		System.out.println("Filename is " + fileName);
		System.out.println("Number of vertices is " + numberOfVertices);
		System.out.println();
		
		tour = initialise();
		temperature = startTemperature;
		for(int i = 0; i < tour.tourSize(); i++)
		{
			oldTour.add(tour.getVertex(i));
		}
		
		System.out.println("Initial distance: " + tour.getDistance() + "km");
		
		while(iteration < maxIterations && temperature > endTemperature)
		{
			distance = tour.getDistance();
			fitness = tour.getFitness();
			tour = swapOperator(tour);
			double newDist = tour.getDistance();
			if(newDist < distance)
			{
				replaceTour();
				if(verbose)
				{
					System.out.println("Iteration " + iteration + " found a better solution: " + tour.getDistance() +"km");
				}
			}
			else  //accept worse solution with some probability
			{
				double delta = tour.getFitness() - fitness;
				double random = Math.random();
				double prob = Math.exp(-(double)Math.abs(delta)/temperature);
				
				if(prob > random)
				{
					replaceTour();
					if(verbose)
					{
						System.out.println("Iteration " + iteration + " accepted a worse solution: " + tour.getDistance() +"km");
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
			
			temperature *= coolingRate;
			
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
	
	public Tour swapOperator(Tour t)
	{
		//Get tour positions and swap
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
		
		return tour;
	}
	
	public void replaceTour()
	{
		oldTour.clear();
		for(int i = 0; i < tour.tourSize(); i++)
		{
			oldTour.add(tour.getVertex(i));
		}
	}
}
