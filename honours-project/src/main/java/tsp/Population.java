package tsp;

public class Population 
{
	//Hold population of tours
	Tour[] tours;
	
	//construct a population
	public Population(int populationSize, boolean initialise)
	{
		tours = new Tour[populationSize];
		//if we need the initialise a population of tours then do so
		if (initialise)
		{
			//loop and create inidividuals
			for(int i = 0; i < populationSize; i++)
			{
				Tour newTour = new Tour();
				newTour.generateIndividual();
				saveTour(i, newTour);
			}
		}
	}
	
	//Saves a tour
	public void saveTour(int index, Tour tour)
	{
		tours[index] = tour;
	}
	
	public void swapTours(Population pop, int index1, int index2)
	{
		Tour tour1 = pop.getTour(index1);
		Tour tour2 = pop.getTour(index2);
		pop.saveTour(index1, tour2);
		pop.saveTour(index2, tour1);
	}
	
	//Get a tour from the population
	public Tour getTour(int index)
	{
		return tours[index];
	}
	
	public Tour getFittest() 
	{
		Tour fittest = tours[0];
		//Loop through to find individuals
		for(int i = 1; i < populationSize(); i++)
		{
			if(fittest.getFitness() <= getTour(i).getFitness())
			{
				fittest = getTour(i);
			}
		}
		return fittest;
	}
	
	public int getWorst()
	{
		Tour worst = tours[1];
		int worstIndex = 1;
		//loop through
		for(int i = 1; i < populationSize(); i++)
		{
			if(worst.getFitness() > getTour(i).getFitness())
			{
				worst = getTour(i);
				worstIndex = i;
			}
		}
		return worstIndex;
	}
	
	//gets population size 
	public int populationSize()
	{
		return tours.length;
	}
}
