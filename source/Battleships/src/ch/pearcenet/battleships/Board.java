package ch.pearcenet.battleships;

public class Board {
	
	//Standard battleships ship set
	public static final int[] STNDR_SHIPS = {5, 4, 3, 3, 2};
	public static final int[] SMALL_SHIPS = {4, 3, 2, 2};
	
	//Board data
	private char[][] ships;
	private char[][] guess;
	private int numShips;
	private int[] shipSet;
	
	//Constructor
	public Board(int size, int[] shipSet) {
		ships = new char[size][size];
		guess = new char[size][size];
		this.shipSet = shipSet.clone();
		
		//Initialize grid as "empty"
		for (int y=0; y<size; y++) {
			for (int x=0; x<size; x++) {
				ships[y][x] = ' ';
				guess[y][x] = ' ';
			}
		}
		
		//Set numShips to the total number of ship squares
		numShips = 0;
		for (int shipSize: shipSet) {
			numShips += shipSize;
		}
	}
	
	//Returns the number of ship-squares this player has
	public int getNumShips() {
		return numShips;
	}
	
	//Performs a full attack from this board to a victim
	public void attack(Board victim) {
		
		//display the attacker's board and ask for the target
		legend();
		this.drawBoard();
		System.out.println("Enter your guess: (e.g. A1)");
		
		//Verify input format
		boolean isValid = false;
		String guess = "";
		int x = 0;
		int y = 0;
		
		while (!isValid) {
			isValid = true;
			System.out.print("> ");
			guess = UI.input.nextLine();
			
			//verification
			if (guess.length() != 2 ||
				guess.toUpperCase().charAt(0) < 'A' ||
				guess.toUpperCase().charAt(0) > 'Z' ||
				guess.charAt(1) < '1' ||
				guess.charAt(1) > '9'){
					
				System.out.println("Please only enter valid guesses. (e.g. A1)");
				isValid = false;
			} else {
				x = guess.toUpperCase().charAt(0) - 'A';
				y = guess.charAt(1) - '1';
				
				//Verify the coordinates are within the board
				if (x < 0 || y < 0 ||
					x >= ships.length ||
					y >= ships.length) {
					
					System.out.println("Please only enter valid guesses. " + guess + " is outside of the board.");
					isValid = false;
				}
			}
		}
		
		//Check that position on the victims board
		if (victim.ships[y][x] == 'B') {
			System.out.println("Hit!");
			victim.ships[y][x] = 'X';
			this.guess[y][x] = 'X';
			victim.numShips--;
		} else {
			System.out.println("Miss.");
			victim.ships[y][x] = 'O';
			this.guess[y][x] = 'O';
		}
		
	}
	
	//Fills the ships grid with the set of ships in random positions
	public void setRandomLayout() {
		for (int shipSize: shipSet) {
			
			//keep trying positions to add the ship until it fits
			boolean fits = false;
			int direction = 0;
			int x = 0;
			int y = 0;
			
			while (!fits) {
				fits = true;
				direction = randInt(4);
				x = randInt(ships.length);
				y = randInt(ships.length);
				
				//Check if the ship would fit here
				for (int i=0; i<shipSize; i++) {
					int tempY = y;
					int tempX = x;
					switch (direction) {
						case 1:
							tempY = y - i;
							break;
							
						case 2:
							tempX = x + i;
							break;
							
						case 3:
							tempY = y + i;
							break;
						
						case 4:
							tempX = x - i;
							break;
					}
					
					if (tempX < 1 || tempY < 1 || tempX >= ships.length || tempY >= ships.length) {
						fits = false;
					} else if (ships[tempY][tempX] != ' ') {
						fits = false;
					}
					
				}
			}
			
			//When the ship fits add it to the grid
			for (int i=0; i<shipSize; i++) {
				int tempX = x;
				int tempY = y;
				//Get next coord
				switch (direction) {
					case 1:
						tempY = y - i;
						break;
						
					case 2:
						tempX = x + i;
						break;
						
					case 3:
						tempY = y + i;
						break;
					
					case 4:
						tempX = x - i;
						break;
				}
				
				//Add to grid
				ships[tempY][tempX] = 'B';
			}
			
		}
	}
	
	//Method to display the legend
	public static void legend() {
		System.out.println("\n"
				+ " ~ Legend: ~\n"
				+ "------------\n"
				+ " BB = Battleship\n"
				+ " XX = Hit\n"
				+ " OO = Miss\n"
				+ "\n");
	}
	
	//Method to display current board on the screen
	public void drawBoard() {
		
		//Display our ships
		System.out.println("Your ships:");
		drawMatrix(ships);
		System.out.println("\nYour guesses:");
		drawMatrix(guess);
		
	}
	
	//Method to draw a single char matrix to the screen
	private static void drawMatrix(char[][] mat) {
		//printing horizontal axis
		System.out.print("  ");
		char letter = 'A';
		for (int i=0; i<mat.length; i++) {
			System.out.print(letter++ + " ");
		}
		
		//Top border
		System.out.print("\n +");
		for (int i=0; i<mat.length; i++) {
			System.out.print("--");
		}
		System.out.println("+");
		
		//Main matrix data
		for (int y=0; y<mat.length; y++) {
			System.out.print((y + 1) + "|");
			for (int x=0; x<mat.length; x++) {
				System.out.print("" + mat[y][x] + mat[y][x]);
			}
			System.out.println("|");
		}
		
		//Bottom border
		System.out.print(" +");
		for (int i=0; i<mat.length; i++) {
			System.out.print("--");
		}
		System.out.println("+");
	}
	
	//Generates a random integer within a range
	private static int randInt(int max) {
		double rand = Math.random();
		rand = rand * max + 1;
		return (int) rand;
	}
	
}
