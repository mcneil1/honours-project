package tsp;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionaryAlgorithm 
{
	//Evolutionary algorithm parameters 
	private static double mutationRate = 0.01;
	private static final int tournamentSize = 50;
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
		Random random = new Random();
		int mutation = random.nextInt(pop.populationSize());
		mutate(pop.getTour(mutation));

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


		//replace random tour
		Random r = new Random();
		int index = r.nextInt(pop.populationSize()-1);
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
		//Loop through tour vertices
		for (int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++)
		{
			//Apply mutation rate
			if(Math.random() < mutationRate)
			{
				int tourPos2 = (int) (tour.tourSize() * Math.random());
				Vertex vertex1 = tour.getVertex(tourPos1);
				Vertex vertex2 = tour.getVertex(tourPos2);

				//swap positions
				tour.setVertex(tourPos2, vertex1);
				tour.setVertex(tourPos1, vertex2);
			}
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

	public void runEA()
	{
		//initialise population
		Population pop = new Population(250, true);

		System.out.println("Solver is EVOLUTIONARY ALGORITHM");
		System.out.println("Filename is " + fileName);
		System.out.println("Number of vertices is " + numberOfVertices);
		System.out.println();

		System.out.println("Initial distance: " + pop.getFittest().getDistance()+ "km");

		//Evolve population for n maxIterations 
		pop = EvolutionaryAlgorithm.evolvePopulation(pop);
		for (int i = 0; i < maxIterations; i++)
		{
			pop = EvolutionaryAlgorithm.evolvePopulation(pop);
			iteration++;
		}

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
		
		URL_Builder myURL = new URL_Builder(bestTour);
		myURL.getURL();
		
	}
}
