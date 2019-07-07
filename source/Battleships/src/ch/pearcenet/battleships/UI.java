package ch.pearcenet.battleships;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UI {
	
	//Current version
	private static final String VERSION = "1.0.1";
	
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
				
				//Exit the program
				case 3:
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
				cls();
				
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
				cls();
				
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
			
			long timeTaken = endTime - startTime;
			String formattedTime = new SimpleDateFormat("HH:mm:ss").format(new Date(timeTaken));
			
			System.out.println("The game lasted " + round + "rounds and took a total time of: " + formattedTime);
			System.out.println("Press enter to return to the main menu...");
			input.nextLine();
			cls();
		}
	}
	
	//Simply sets up both game boards for any size
	private static void initBoards(int size) {
		
		//Use a custom shipset for different sizes
		int[] shipSet = Board.STNDR_SHIPS;
		switch (size) {
			case 5:
				shipSet = Board.SMALL_SHIPS;
				break;
				
			case 9:
				shipSet = Board.STNDR_SHIPS;
				break;
			
			//Future feature: Make any size possible with automatic ship sets
			default:
				shipSet = Board.STNDR_SHIPS;
				break;
		}
		
		board1 = new Board(size, shipSet);
		board2 = new Board(size, shipSet);
		System.out.println("Randomizing game boards...");
		
		board1.setRandomLayout();
		board2.setRandomLayout();
		System.out.println("Done.");
	}
	
	//Displays the main menu and returns result
	private static int printMenu() {
		
		//ASCII art banner
		System.out.println("                                                            \n" + 
				"_-_ _,,           ,    ,  ,,             ,,                   \n" + 
				"   -/  )    _    ||   ||  ||             ||     '             \n" + 
				"  ~||_<    < \\, =||= =||= ||  _-_   _-_, ||/\\\\ \\\\ -_-_   _-_, \n" + 
				"   || \\\\   /-||  ||   ||  || || \\\\ ||_.  || || || || \\\\ ||_.  \n" + 
				"   ,/--|| (( ||  ||   ||  || ||/    ~ || || || || || ||  ~ || \n" + 
				"  _--_-'   \\/\\\\  \\\\,  \\\\, \\\\ \\\\,/  ,-_-  \\\\ |/ \\\\ ||-'  ,-_-  \n" + 
				" (                                         _/     |/          \n" + 
				"                                                  '           ");
		
		//General info
		System.out.println("\nBattleships v" + VERSION +
							"\n by Samuel Pearce");
		
		//Menu options
		System.out.println("\n"
				+ "\nPlease choose:"
				+ "\n [1] Play 5x5"
				+ "\n [2] Play 9x9"
				+ "\n [3] Exit game"
				+ "\n");
		
		//Get user input
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
				System.out.println("Please only enter numbers from 1-3");
				isValid = false;
				continue;
			}
			
			//Verify it's within range
			if (num < 1 || num > 3) {
				System.out.println("Please only enter numbers from 1-3");
				isValid = false;
				continue;
			}
		}
		
		//Return user's input
		return num;
	}
	
	//Function to "clear" the screen
	private static void cls() {
		for (int i=0; i<500; i++) System.out.println("\n");
	}

}
