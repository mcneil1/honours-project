package tsp;

import java.util.ArrayList;

public class TwoOpt 
{
	static String fileName;
	static int iteration = 0;
	static int maxIterations;
	static boolean verbose;
	static int numberOfVertices = 0;
	static Tour tour;
	
	public TwoOpt(String file, int it, boolean ver, int count)
	{
		fileName = file;
		maxIterations = it;
		verbose = ver;
		numberOfVertices = count;
	}
	
	public Tour runTO()
	{
		System.out.println("Solver is 2-OPT");
		System.out.println("Filename is " + fileName);
		System.out.println("Number of vertices is " + numberOfVertices);
		System.out.println();
		
		tour = initialise();
		
		System.out.println("Initial distance: " + tour.getDistance() + "km");
		
		long t= System.currentTimeMillis();

		long end = t+ 300000;

		while(System.currentTimeMillis() < end) {
			//copy the tour
			Tour newTour = new Tour();
			for(int tourSize = 0; tourSize<tour.tourSize(); tourSize++)
			{
				newTour.setVertex(tourSize, tour.getVertex(tourSize));
			}
			
			int position1 = (int)(Math.random()*tour.tourSize());
			int position2 = (int)(Math.random()*tour.tourSize());
			
			//make sure pos1 and pos2 are different
			while (position1 == position2)
			{
				position2 = (int)(Math.random()*tour.tourSize());
			}
			//make sure pos2 is greater than pos1
			if(position2<position1)
			{
				int swap = position1;
				position1=position2;
				position2=swap;
			}
			
			//temp will hold the vertices between pos1 and pos2
			int size = position2-position1+1;
			ArrayList<Vertex> temp = new ArrayList<Vertex>();
			
			for(int j = 0; j<size; j++)
			{
				temp.add(tour.getVertex(position1+j));
			}
			
			//replace the vertices in newTour with those is temp but in reverse order
			int start=0;
			for(int j = position2; j>=position1; j--)
			{
				newTour.setVertex(j,temp.get(start));
				start++;
			}
			
			if(newTour.getFitness() > tour.getFitness())
			{
				for(int j = 0; j<tour.tourSize(); j++)
				{
					tour.setVertex(j, newTour.getVertex(j));
				}
				if(verbose)
					System.out.println("iteration " + iteration + " found better solution: " + tour.getDistance()+"km");
			}
			iteration++;
		}
		iteration = 0;
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
		
		return tour;
		
	}
	
	public Tour initialise()
	{
		Tour t = new Tour();
		t.generateIndividual();
		return t;
	}
	
	
}
