package tsp;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionaryAlgorithm 
{
	//Evolutionary algorithm parameters 
	private static double mutationRate = 0.05;
	static int popSize = 100; 
	private static final int tournamentSize = 10;
	static String fileName;
	static int iteration = 0;
	static int maxIterations;
	static boolean verbose;
	static Tour bestTour;
	static int numberOfVertices = 0;

	public EvolutionaryAlgorithm(String f, int it, boolean v, int c)
	{
		fileName = f;
		maxIterations = it;
		verbose = v;
		numberOfVertices = c;
	}

	//Evolves a population over one generation
	public static Population evolvePopulation(Population pop)
	{
		//Select parents 
		Tour parent1 = tournamentSelection(pop);
		Tour parent2 = tournamentSelection(pop);
		while (parent2 == parent1)
		{
			parent2 = tournamentSelection(pop);
		}
		//Crossover parents
		Tour child = crossover(parent1, parent2);

		//mutate a random tour

		mutate(child);

		if(verbose)
		{
			if((bestTour != null) && (bestTour.getFitness() < pop.getFittest().getFitness()))
			{
				System.out.println("Iteration " + iteration + " found a better solution: " + pop.getFittest().getDistance() +"km");
			}
		}

		if(bestTour == null)
		{
			bestTour = pop.getFittest();
		}
		else if(pop.getFittest().getFitness() > bestTour.getFitness())
		{
			bestTour = pop.getFittest();
		}


		//replace worst tour
		int index = pop.getWorst();
		pop.saveTour(index, child);


		return pop;
	}

	//Applies crossover to parents to create a child
	public static Tour crossover(Tour parent1, Tour parent2)
	{
		//Create a new child tour
		Tour child = new Tour();

		//get start and end positions for parent1's tour
		int startPos = (int) (Math.random() * parent1.tourSize());
		int endPos = (int)	(Math.random() * parent1.tourSize());

		//Loop and add the sub tour from parent1 to the child
		for (int i = 0; i < child.tourSize(); i++)
		{
			//If our start pos is less than end pos
			if (startPos < endPos && i > startPos && i < endPos)
			{
				child.setVertex(i, parent1.getVertex(i));
			} //else if our start pos is greater than end pos
			else if (startPos > endPos)
			{
				if (!(i < startPos && i > endPos))
				{
					child.setVertex(i, parent1.getVertex(i));
				}
			}
		}	

		//Loop through parent2's tour 
		for (int i = 0; i < parent2.tourSize(); i++)
		{
			// If child doesn't have the city add it
			if(!child.containsVertex(parent2.getVertex(i)))
			{  //Loop to find a spare position in the child's tour
				for(int j = 0; j < child.tourSize(); j++)
				{
					//Spare position found, add city
					if(child.getVertex(j) == null)
					{
						child.setVertex(j, parent2.getVertex(i));
						break;
					}
				} 
			}
		}
		return child;
	}

	//Mutation through swap operator 
	private static void mutate(Tour tour)
	{
		if(Math.random() < mutationRate)
		{
			int tourPos1 = (int) (tour.tourSize() * Math.random());
			int tourPos2 = (int) (tour.tourSize() * Math.random());
			while(tourPos1 == tourPos2)
			{
				tourPos2 = (int) (tour.tourSize() * Math.random());
			}
			if(tourPos1 > tourPos2)
			{
				int swap = tourPos1;
				tourPos1 = tourPos2;
				tourPos2 = swap;
			}
			int j = tourPos2;

			Tour newTour = new Tour();
			for(int i = 0; i < tourPos1; i++)
			{
				newTour.setVertex(i, tour.getVertex(i));
			}
			for(int i = tourPos1; i <= tourPos2; i++)
			{
				newTour.setVertex(i, tour.getVertex(j));
				j--;
			}
			for(int i = tourPos2+1; i < tour.tourSize(); i++)
			{
				newTour.setVertex(i, tour.getVertex(i));
			}
			
			for(int i = 0; i < tourPos1; i++)
			{
				
			}
			
			tour = newTour;

		}

	}
	

	//Selects a candidate tour for crossover
	private static Tour tournamentSelection(Population pop)
	{
		//Create a tournament population
		Population tournament = new Population(tournamentSize, false);
		ArrayList<Integer> indivs = new ArrayList<Integer>();
		//add 'tournamentSize' random tours to the tournament
		for(int i = 0; i < tournamentSize; i++)
		{
			int randomId = (int) (Math.random()*pop.populationSize());
			while(indivs.contains(randomId))
			{
				randomId = (int) (Math.random()*pop.populationSize());
			}
			indivs.add(randomId);
			tournament.saveTour(i, pop.getTour(randomId));
		}
		//get fittest
		Tour fittest = tournament.getFittest();
		return fittest;
	}

	public Tour runEA()
	{

		//initialise population
		bestTour = null;
		Population pop = new Population(popSize, true);

		System.out.println("Solver is EVOLUTIONARY ALGORITHM");
		System.out.println("Filename is " + fileName);
		System.out.println("Number of vertices is " + numberOfVertices);
		System.out.println();

		System.out.println("Initial distance: " + pop.getFittest().getDistance()+ "km");

		//Evolve population for n maxIterations 
		
		long t= System.currentTimeMillis();

		long end = t+ 300000;

		while(System.currentTimeMillis() < end) {
			pop = EvolutionaryAlgorithm.evolvePopulation(pop);
			iteration++;
		}
		
		iteration = 0;
		pop = null;
		
		System.out.println("Finished");
		System.out.println("Final distance: " + bestTour.getDistance() + "km");
		System.out.println("Solution: ");
		System.out.println(bestTour);

		if(verbose)
		{
			ArrayList<Integer> verboseTour = new ArrayList<Integer>();
			for(int i = 0; i < bestTour.tourSize(); i++)
			{
				verboseTour.add(bestTour.getVertex(i).getId());
			}
			System.out.println(verboseTour);
			System.out.println();
			verboseTour.clear();
		}
		
		return bestTour;
		
		
	}
}
