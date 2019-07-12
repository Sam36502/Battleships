package ch.pearcenet.shipbattles;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UI {
	
	//Current version
	private static final String VERSION = "1.1";
	
	//Input scanner
	protected static Scanner input = new Scanner(System.in);
	
	//Player boards
	private static Board board1;
	private static Board board2;
	
	//Main
	public static void main(String[] args) {
		
		//Main menu
		boolean isRunning = true;
		while (isRunning) {
			switch(printMenu()) {
				//Start the game with 5x5 boards
				case 1:
					initBoards(5);
					break;
				
				//Start the game with 9x9 boards
				case 2:
					initBoards(9);
					break;
				
				//Set up a custom board
				case 3:
					System.out.println("What size board would you like? (1-99)");
					initBoards(getInt());
					break;
				
				//Exit the program
				case 4:
					System.out.println("Thank you for playing!");
					isRunning = false;
					System.exit(0);
					break;
			}
			
			//Start of the actual game logic
			cls();
			int winner = 0;
			int round = 0;
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			
			while (winner == 0) {
				round++;
				
				//Player 1's turn
				System.out.println("It is Player 1's turn.\n"
						+ "Player 1, press enter when ready...");
				input.nextLine();
				cls();
				
				board1.attack(board2);
				
				//Check for winners
				if (board2.getNumShips() <= 0) {
					endTime = System.currentTimeMillis();
					winner = 1;
					continue;
				}
				
				//Player 2's turn
				System.out.println("It is Player 2's turn.\n"
						+ "Player 2, press enter when ready...");
				input.nextLine();
				cls();
				
				board2.attack(board1);
				
				//Check for winners
				if (board1.getNumShips() <= 0) {
					endTime = System.currentTimeMillis();
					winner = 2;
					continue;
				}
				
			}
			
			//Announce winner and display stats
			cls();
			System.out.println("                                                                                        \n" + 
					"  ,- _~.                                 ,        ,,         ,                       /\\ \n" + 
					" (' /|                 _           _    ||        ||   _    ||   '                   \\/ \n" + 
					"((  ||    /'\\\\ \\\\/\\\\  / \\\\ ,._-_  < \\, =||= \\\\ \\\\ ||  < \\, =||= \\\\  /'\\\\ \\\\/\\\\  _-_, }{ \n" + 
					"((  ||   || || || || || ||  ||    /-||  ||  || || ||  /-||  ||  || || || || || ||_.  \\/ \n" + 
					" ( / |   || || || || || ||  ||   (( ||  ||  || || || (( ||  ||  || || || || ||  ~ ||    \n" + 
					"  -____- \\\\,/  \\\\ \\\\ \\\\_-|  \\\\,   \\/\\\\  \\\\, \\\\/\\\\ \\\\  \\/\\\\  \\\\, \\\\ \\\\,/  \\\\ \\\\ ,-_-  <> \n" + 
					"                      /  \\                                                              \n" + 
					"                     '----`                                                             ");
			System.out.println("Player " + winner + ", you win!\n");
			
			long timeTaken = endTime - startTime - 3600000L;
			String formattedTime = new SimpleDateFormat("HH:mm:ss").format(new Date(timeTaken));
			
			System.out.println("The game lasted " + round + " rounds and took a total time of: " + formattedTime);
			System.out.println("Press enter to return to the main menu...");
			input.nextLine();
			cls();
		}
	}
	
	//Simply sets up both game boards for any size
	private static void initBoards(int size) {
		
		//Use a custom shipset for different sizes
		int[] shipSet = new int[5];
		switch (size) {
			case 5:
				shipSet = Board.SMALL_SHIPS;
				break;
				
			case 9:
				shipSet = Board.STNDR_SHIPS;
				break;
			
			//Let the user pick the Ship types
			default:
				System.out.println("How many Aircraft Carriers would you like? (ACs are 5 long)");
				shipSet[0] = getInt(1, 5);
				
				System.out.println("How many Battleships would you like? (Battleships are 4 long)");
				shipSet[1] = getInt(1, 5);
				
				System.out.println("How many Cruisers would you like? (Cruisers are 3 long)");
				shipSet[2] = getInt(1, 5);
				
				System.out.println("How many Destroyers would you like? (Destroyers are 2 long)");
				shipSet[3] = getInt(1, 5);
				
				System.out.println("How many Submarines would you like? (Submarines are 1 long)");
				shipSet[4] = getInt(1, 5);
				
				break;
		}
		
		board1 = new Board(size, shipSet);
		board2 = new Board(size, shipSet);
		System.out.println("Randomizing game boards...");
		System.out.println("If you have many ships on a small board this might take a while...");
		
		board1.setRandomLayout();
		board2.setRandomLayout();
		System.out.println("Done.");
	}
	
	//Displays the main menu and returns result
	private static int printMenu() {
		
		//ASCII art banner
		System.out.println("\n" + 
				"\n" + 
				"                                                                      \n" + 
				"  -_-/  ,,                   _-_ _,,           ,    ,  ,,             \n" + 
				" (_ /   ||     '                -/  )    _    ||   ||  ||             \n" + 
				"(_ --_  ||/\\\\ \\\\ -_-_          ~||_<    < \\, =||= =||= ||  _-_   _-_, \n" + 
				"  --_ ) || || || || \\\\          || \\\\   /-||  ||   ||  || || \\\\ ||_.  \n" + 
				" _/  )) || || || || ||          ,/--|| (( ||  ||   ||  || ||/    ~ || \n" + 
				"(_-_-   \\\\ |/ \\\\ ||-'          _--_-'   \\/\\\\  \\\\,  \\\\, \\\\ \\\\,/  ,-_-  \n" + 
				"          _/     |/           (                                       \n" + 
				"                 '                                                    \n" + 
				"\n" + 
				"");
		
		//General info
		System.out.println("\nShip Battles v" + VERSION +
							"\n by Samuel Pearce");
		
		//Menu options
		System.out.println("\n"
				+ "\nPlease choose:"
				+ "\n [1] Play 5x5"
				+ "\n [2] Play 9x9"
				+ "\n [3] Play Custom Game"
				+ "\n [4] Exit game"
				+ "\n");
		
		//Return user's input
		return getInt(1, 4);
	}
	
	//Gets validated numbers without any range
	protected static int getInt() {
		return getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	//Gets a number while validating for only numbers in a range
	protected static int getInt(int min, int max) {
		boolean isValid = false;
		int num = 0;
	
		while (!isValid) {
			isValid = true;
			System.out.print("> ");
			String in = input.nextLine();
			
			//Verify it's a number
			try {
				num = Integer.parseInt(in);
			} catch (NumberFormatException e) {
				System.out.println("Please only enter numbers");
				isValid = false;
				continue;
			}
			
			//Verify it's within range
			if (num < min || num > max) {
				System.out.println("Please only enter numbers from "+min+" - "+max+".");
				isValid = false;
				continue;
			}
		}
		
		return num;
	}
	
	//Function to "clear" the screen
	private static void cls() {
		for (int i=0; i<500; i++) System.out.println("\b");
		for (int i=0; i<500; i++) System.out.println(" ");
		for (int i=0; i<500; i++) System.out.println("\b");
	}

}
