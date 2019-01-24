package ticTacToe;

import java.util.Scanner;
import java.util.Random;

public class TicTacToeTwoPlayer {
	static final int SIZE = 3;
    /*
       Declare a 2-d character array called board with SIZE rows and SIZE columns.
    */
	
    static final char[][] board = new char[SIZE][SIZE];
	
    static final char PLAYER1 = 'X';
    static final char PLAYER2 = 'O';
    static Scanner userInput = new Scanner(System.in);
    
    public static void initializeBoard() {
    /*
       Initialize each position of the board array to a space character.
    */
    	
    	for (int rows = 0; rows < SIZE; rows++) {
    		for (int cols = 0; cols < SIZE; cols++) {
    			board[rows][cols] = ' ';
    		}
    	}
        
    }
    
    public static void displayBoard() {
        /*
            If a board position is available, that is, if it is
            a space, output an asterisk followed by a space ("* ".)
        */
    	System.out.println("Board is: ");
    	
    	for (int rows = 0; rows < SIZE; rows++) {
    		for (int cols = 0; cols < SIZE; cols++) {
    			if (board[rows][cols] == ' ') {
    				System.out.print("* ");
    			}
    			else {
    				System.out.print(board[rows][cols] + " ");
    			}
    		}
    		System.out.println("");
    	}
        
    }
    
    /*
       The method will return true if the game is tied and false otherwise.
    */
    public static boolean isTied() {
        // check for a row win
		boolean tie = false;
    	int isFull = 0;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] != ' ') {
					isFull++;
					if (isFull < 9) {
						tie = false;
					}
					else {
						tie = true;
					}
				}
			} 
		}
	return tie;
   }
 
    
    public static void playerMove(char player) {
    	boolean done = false;
    	while(done == false) {
	        System.out.println("Enter the move for player: " + player);
	        System.out.print("\nRow number: ");
	        int row = userInput.nextInt();
	        System.out.print("\nColumn number: ");
	        int col = userInput.nextInt();
	        if ((row < 1 || row > 3) || (col < 1 || col > 3)) {
	        	System.out.println("Illegal row/column: " + row + ", " + col);
	        }
	        else if (board[row - 1][col - 1] != ' ') {
	        	System.out.println("That position is taken. Try again.");
	        }
	        else {
	        	board[row - 1][col - 1] = player;
	        	done = true;
	        }
    	}
        
    }
    
    public static char getWinner() {
        char whoWon=' ';
        boolean won=false;
        
        	// check for a row win
		  for (int i=0; i < SIZE && !won; i++) {
			if ( (board[i][0] == board[i][1]) && (board[i][1] == board[i][2]) && board[i][0] != ' ') {
				whoWon = board[i][0];
                won = true;
			}
		}
			
		// check for a column win
		  for (int i = 0; i < SIZE; i++) {
	      	   if ( (board[0][i] == board [1][i]) && (board[1][i] == board [2][i]) && board[0][i] != ' ' ) {
	      		 whoWon = board[0][i];
				 won = true;
	      	   }
	         }
        
       		 // check for a diagonal win
		  if( (board[0][0] == board[1][1]) && (board[1][1] == board[2][2]) && board[0][0] != ' ' ) {
	        	whoWon = board[0][0];
				won = true;
	        }
	        if ( (board[0][2] == board[1][1]) && (board[1][1] == board[2][0]) && board[0][2] != ' ' ) {
	        	whoWon = board[0][2];
				won = true;
	        }
	               
        return(whoWon);
    }
	
    public static void main(String args[]) {
      
        char whoWon;
        boolean won, tie;
        String again;
        
        do {
            won = false;
            tie = false;
            initializeBoard();
            System.out.println("Welcome to TicTacToe.");
            displayBoard();
            
            do {
                playerMove(PLAYER1);
                displayBoard();
            
                whoWon = getWinner();
           
                if (!(whoWon == ' '))
                    won = true;
            
                if (!won)
                    tie = isTied();
          
                if (!won && !tie) {
                    playerMove(PLAYER2);
                    displayBoard();
                }
                
                whoWon = getWinner();
                if (!(whoWon == ' '))
                    won = true;
                if (!won)
                    tie = isTied();
            } while (!won && !tie);
            
            if (tie) 
                System.out.println("Tie game.");
            else if (whoWon == PLAYER1)
                System.out.println("Player 1 wins.(" + PLAYER1 + ")");
            else
                System.out.println("Player 2 wins.(" + PLAYER2 + ")");
            
            System.out.print("Care to play again? (y/n) ");
            again = userInput.next();
        } while (again.equalsIgnoreCase("y")); 
    }
}
