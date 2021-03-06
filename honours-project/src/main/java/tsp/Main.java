package tsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

public class Main 
{
	static String fileLocation = "C:\\Users\\USER\\Documents\\Uni\\";
	static String fileName = "vertices.csv";
	static int EVOLUTIONARY_ALGORITHM = 1;
	static int SIMULATED_ANNEALING = 2;
	static int HILL_CLIMBER = 3;
	static int TABU_SEARCH = 4;
	static int TWO_OPT = 5;
	static int NEAREST_NEIGHBOUR = 6;
	static double startTemp = 500;
	static double endTemp = 0;
	static double coolingRate = 0.8;
	static boolean verbose = false;
	static int iterations = Integer.MAX_VALUE;
	static int tabuSize = 1000;
	static int solver = EVOLUTIONARY_ALGORITHM;
	static int numRuns = 1;
	static int count = 0;
	static String filePath = "test_results.csv";
	Timer timer = new Timer();
	
	
	public static void main(String[] args) throws Exception
	{
		
		final Options options = new Options();
		options.addOption(new Option("h", "help", false, "Print this help message"));
		
		options.addOption("i", "iterations", true, "Number of iterations (default: 1000)");
		options.addOption("s", "startTemp", true, "Start temperature (default: 200)");
		options.addOption("e", "finTemp", true, "Final temperature (default: 0)");
		options.addOption("r", "coolingRate", true, "Cooling rate (default(0.995)");
		options.addOption("f", "file", true,"File name (default: locations.csv)");
		options.addOption("t", "tabuSize", true,"Size of tabu list (default: 10)");
		options.addOption("N", "numRuns", true, "Number of runs (default: 1)");
		
		options.addOption("v", false, "Verbose (detailed output)");
		
		options.addOption("EA", false, "Use evolutionary algorithm");
		options.addOption("SA", false, "Use simmulated annealing");
		options.addOption("HC", false, "Use hill climber");
		options.addOption("TS", false, "Use tabu search");
		options.addOption("TO", false, "Use 2-opt");
		options.addOption("NN", false, "Use Nearest Neighbour");
		
		CommandLineParser parser = new GnuParser();
		try
		{
			CommandLine cmdLine = parser.parse(options, args);
			if(cmdLine.hasOption("h"))
			{
				final HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("TSP Solver", options);
				System.exit(1);
			}
			
			if(cmdLine.hasOption("N"))
				numRuns = Integer.parseInt(cmdLine.getOptionValue("N"));
			if (cmdLine.hasOption("i"))
				iterations = Integer.parseInt(cmdLine.getOptionValue("i"));
			if (cmdLine.hasOption("s"))
				startTemp = Double.parseDouble(cmdLine.getOptionValue("s"));
			if (cmdLine.hasOption("e"))
				endTemp = Double.parseDouble(cmdLine.getOptionValue("e"));
			if (cmdLine.hasOption("r"))
				coolingRate = Double.parseDouble(cmdLine.getOptionValue("r"));
			if (cmdLine.hasOption("t"))
				tabuSize = Integer.parseInt(cmdLine.getOptionValue("t"));
			if (cmdLine.hasOption("v"))
				verbose = true;
			if (cmdLine.hasOption("f"))
				fileName = cmdLine.getOptionValue("f");
			if (cmdLine.hasOption("HC"))
				solver = HILL_CLIMBER;
			if (cmdLine.hasOption("SA"))
				solver = SIMULATED_ANNEALING;
			if (cmdLine.hasOption("TS"))
				solver = TABU_SEARCH;
			if (cmdLine.hasOption("TO"))
				solver = TWO_OPT;
			if (cmdLine.hasOption("NN"))
				solver = NEAREST_NEIGHBOUR;
		}
		catch (final ParseException exp)
		{
			System.out.println("Parseing failed. Reason: " + exp.getMessage());
			System.exit(1);
		}
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileLocation+fileName));
			
			String line;
			String cvsSplitBy = ",";
			
			while ((line = br.readLine()) != null)
			{
				count++;
				
				//use comma as separator
				String[] location = line.split(cvsSplitBy);
				
				//System.out.println("Latitude = " + location[1] + " Longitude = " + location[2]);
				
				Double lat = Double.valueOf(location[1]);
				Double lon = Double.valueOf(location[2]);
				int id = Integer.valueOf(location[0]);
				
				Vertex newVertex = new Vertex();
				newVertex.setVertex(lat, lon, id);
				TourManager.addVertex(newVertex);
			}
			br.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	
		
		if (solver == SIMULATED_ANNEALING)
		{
			SimulatedAnnealing mySA = new SimulatedAnnealing(fileName, iterations, verbose, startTemp, endTemp, coolingRate, count);
			
			for(int i = 0; i < numRuns; i++)
			{
				long startTime = System.nanoTime();
				Tour tour = mySA.runSA();
				long endTime = System.nanoTime();
				
				long timeElapsed = endTime - startTime;
				
				String algorithm = "Simulated_Annealing"+ i;
				
				URL_Builder myURL = new URL_Builder(tour);
				String curl = myURL.getCurl(algorithm);
				
				saveTour(algorithm, tour);
				saveData(algorithm, curl, tour.getDistance(), filePath, timeElapsed, count);
			}
		}
		else if (solver == HILL_CLIMBER)
		{
			HillClimber myHC = new HillClimber(fileName, iterations, verbose, count);
			
			for (int i = 0; i < numRuns; i++)
			{
				long startTime = System.nanoTime();
 				Tour tour = myHC.runHC();
				long endTime = System.nanoTime();
				
				long timeElapsed = endTime - startTime;
				
				String algorithm = "Hill_Climber"+ i;
				
				URL_Builder myURL = new URL_Builder(tour);
				String curl = myURL.getCurl(algorithm);
				
				saveTour(algorithm, tour);
				saveData(algorithm, curl, tour.getDistance(), filePath, timeElapsed, count);
			}
		}
		else if (solver == TABU_SEARCH)
		{
			TabuSearch myTS = new TabuSearch(fileName, iterations, verbose, count,tabuSize);
			
			for (int i = 0; i < numRuns; i++)
			{
				long startTime = System.nanoTime();
				Tour tour = myTS.runTS();
				long endTime = System.nanoTime();
				
				long timeElapsed = endTime - startTime;

				String algorithm = "Tabu_Search"+ i;
				
				URL_Builder myURL = new URL_Builder(tour);
				String curl = myURL.getCurl(algorithm);
				
				saveTour(algorithm, tour);
				saveData(algorithm, curl, tour.getDistance(), filePath, timeElapsed, count);
			}
		}
		else if (solver == TWO_OPT)
		{
			TwoOpt myTO = new TwoOpt(fileName, iterations, verbose, count);
			
			for (int i = 0; i < numRuns; i++)
			{
				long startTime = System.nanoTime();
				Tour tour = myTO.runTO();
				long endTime = System.nanoTime();
				
				long timeElapsed = endTime - startTime;
				
				String algorithm = "2_OPt"+ i;
				
				URL_Builder myURL = new URL_Builder(tour);
				String curl = myURL.getCurl(algorithm);
				
				saveTour(algorithm, tour);
				saveData(algorithm, curl, tour.getDistance(), filePath, timeElapsed, count);
			}
		}
		else if (solver == NEAREST_NEIGHBOUR)
		{
			NearestNeighbour myNN = new NearestNeighbour(fileName, verbose, count);
			
			for (int i = 0; i < numRuns; i++)
			{
				long startTime = System.nanoTime();
				Tour tour = myNN.runNN();
				long endTime = System.nanoTime();
				
				long timeElapsed = endTime - startTime;
				
				String algorithm = "Nearest_Neighbour" + i;
				
				URL_Builder myURL = new URL_Builder(tour);
				String curl = myURL.getCurl(algorithm);
				
				saveTour(algorithm, tour);
				saveData(algorithm, curl, tour.getDistance(), filePath, timeElapsed, count);
			}
		}
		else
		{
			EvolutionaryAlgorithm myEA = new EvolutionaryAlgorithm(fileName,iterations, verbose, count);
			
			for (int i = 0; i < numRuns; i++)
			{
				Tour tour = new Tour();
				
				tour = myEA.runEA();
					
				
				String algorithm = "Evolutionary_Algorithm" + i;
				
				URL_Builder myURL = new URL_Builder(tour);
				String curl = myURL.getCurl(algorithm);
				
				saveTour(algorithm, tour);
				saveData(algorithm, curl, tour.getDistance(), filePath, iterations, count);
				
			}
		}
	}
	
	public static void saveData(String algorithm, String curl, double dist, String filepath, long time, Integer count)
	{
		try
		{
			FileWriter fw = new FileWriter(filepath,true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(dist+","+ time +"," + count + ","+ curl );
			pw.flush();
			pw.close();
		}
		catch(Exception E)
		{
			System.out.println(E.getLocalizedMessage());
		}
	}
	
	public static void saveTour(String algorithm, Tour tour)
	{
		String filepath = algorithm + "_tour.json";
		try
		{
			FileWriter fw = new FileWriter(filepath,false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println("{"
					+ "\n\t\"elevation\": false,"
					+"\n\t\"vehicle\": \"car\","
					+"\n\t\"points\": [");
			for(int i = 0; i <= tour.tourSize();i++)
			{
				if(i == tour.tourSize())
				{
					pw.println("\t\t[" + tour.getVertex(0).getY() + "," + tour.getVertex(0).getX() + "]");

				}
				else
				{
				pw.println("\t\t[" + tour.getVertex(i).getY() + "," + tour.getVertex(i).getX() + "],");
				}
			}
			pw.println("\t]" + "\n}");
			pw.flush();
			pw.close();
		}
		catch(Exception E)
		{
			System.out.println(E.getLocalizedMessage());
		}
	}
	
}