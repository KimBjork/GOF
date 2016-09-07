import java.util.Arrays;
import java.util.Random;

public class Game {
	/*Sick days:
	 *2014: [4,8]
	 *2015: [3,9]
	 *2016: [1,12]*/

	//final population will will be sqrt(population)^2
	final static double POPULATION = 9;
	final static int MAXSICKDAYS = 4;
	final static int MINSICKDAYS = 8;
	final static int SICKPROBABILITY = 100;
	final static int DEATHPROBABILITY = 30;
	final static int IMMUNEPROBABILITY = 10;
	static int year = 2014;
	static int totalSick = 0;
	static int newSick = 0;
	static int totalDead = 0;
	static int newDead = 0;
	static int totalImmune = 0;
	static int newImmune = 0;
	
	static int n = (int) Math.sqrt(POPULATION);
	static Human [][] humanity = new Human[n][n];
	
	public static void main(String[] args) {
		for (int i = 0; i < humanity.length; i++) {
			for (int j = 0; j < humanity.length; j++) {
				humanity[i][j] = new Human();
			}
		}

		printResults(humanity);

		//Goes on for 3 years
		for (int i = 0; i < 3; i++) {
			System.out.println("The year is " + year++);
			for (int j = 0; j < 365; j++) {
				System.out.println("####The day is " + (j+1) + "####");
				//Goes thought every individual to possibly contaminate neighbors
				//possibly dying, possibly getting immune
				for (int row = 0; row < humanity.length; row++) {
					for (int column = 0; column < humanity.length; column++) {
						evaluate(row, column);
					}
				}
				//Possibly contaminates neighbors
				
				
				// NEEED TO ADD SICKDAYS 
				

				//Ending day with printing status and reseting daily values.
				
				printDayStatus();
				resetDay();
			}
		}


	}
	/**
	 * Evaluates a human, assigns immunity, sickness and death. 
	 * 
	 * @param row: the row used to identify individual
	 * @param column: the column used to identify individual
	 */
	private static void evaluate(int row, int column) {
		//TODO: evaluate this function, not done, what is happening?
		Human human = humanity[row][column];
		//If individual is sick and not dead
		if(human.sickdays > 0 && human.sick && !human.dead){
			//Is the individual going to die?
			if(human.sickdays == MAXSICKDAYS){
				human.dead = true;
			}
			//Is the individual going to be healthy now?
			if(human.sickdays >= MINSICKDAYS & gettingHealthy()){
				human.immun = true;
				human.sick = false;
			}
			contaminateNeighbors(row, column);

		}

		
	}
	/**
	 * Goes though neighbors and possibly contaminates them.
	 * 
	 * @param row: the row used to identify individual
	 * @param column: the column used to identify individual
	 */
	private static void contaminateNeighbors(int row, int column) {
		
		if(row > 0)
			humanity[row-1][column].sick = gettingSick();
		if(row < humanity.length-1)
			humanity[row+1][column].sick = gettingSick();
		if(column > 0)
			humanity[row][column-1].sick = gettingSick();
		if(column < humanity.length-1)
			humanity[row][column+1].sick = gettingSick();
		if(column > 0 && row > 0)
			humanity[row-1][column-1].sick = gettingSick();
		if(column < humanity.length-1 && row < humanity.length-1)
			humanity[row+1][column+1].sick = gettingSick();
		if(column < humanity.length-1 && row > 0)
			humanity[row-1][column+1].sick = gettingSick();
		if(column > 0 && row < humanity.length-1)
			humanity[row+1][column-1].sick = gettingSick();
	}


	/**
	 * Resets values for the day.
	 */
	private static void resetDay() {
		newDead = 0;
		newImmune = 0;
		newSick = 0;
	}

	/**
	 * Prints the daily status of the population.
	 */
	private static void printDayStatus() {
		System.out.println("Sick");
		System.out.println("    Got sick today: " + newSick);
		System.out.println("    Total sick: " + totalSick);
		
		System.out.println("Dead");
		System.out.println("    Died today: " + newDead);
		System.out.println("    Total dead: " + totalDead);
		
		System.out.println("Immune");
		System.out.println("    Immune today: " + newImmune);
		System.out.println("    Total immune: " + totalImmune);
	}
	
	/** Generates a random number between 1-100, then checks if the number is smaller
	 * than the SICKPROBABILITY value. If so the individual evaluated will get sick.
	 * 
	 * @return true if individual got sick, false if it stayed healthy
	 */
	public static boolean gettingSick() {
	    boolean sick = false;
		int prob = new Random().nextInt(100) + 1;
	    if(prob <= SICKPROBABILITY){
	    	totalSick++;
	    	newSick++;
	    	sick = true;
	    }else{
	    }
	    return sick;
	}
	/** Generates a random number between 1-100, then checks if the number is smaller
	 * than the IMMUNEPROBABILITY value. If so the individual evaluated will get immune.
	 * 
	 * @return true if individual got immune, false if it stayed sick
	 */
	public static boolean gettingHealthy() {
	    boolean sick = false;
		int prob = new Random().nextInt(100) + 1;
	    if(prob <= IMMUNEPROBABILITY){
	    	totalImmune++;
	    	newImmune++;
	    	sick = true;
	    }else{
	    }
	    return sick;
	}
	/** Generates a random number between 1-100, then checks if the number is smaller
	 * than the DEATHPROBABILITY value. If so the individual evaluated will get immune.
	 * 
	 * @return true if individual got immune, false if it stayed sick
	 */
	public static boolean dying() {
	    boolean sick = false;
		int prob = new Random().nextInt(100) + 1;
	    if(prob <= DEATHPROBABILITY){
	    	totalDead++;
	    	newDead++;
	    	sick = true;
	    }else{
	    }
	    return sick;
	}
	
	public static void printResults(Human[][] humanity){
		System.out.println("Total population is " + humanity.length*humanity.length + " people.");
		for (int i = 0; i < humanity.length; i++) {
			for (int j = 0; j < humanity.length; j++) {
				if(humanity[i][j].sick){
					System.out.print("*");
				} else{
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}

}
