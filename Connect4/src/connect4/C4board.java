package connect4;

import java.util.Scanner;

public class C4board {
	/* Useful for debugging.
	   */
	   public static void tellUser(String prompt) {
	      Scanner sc = new Scanner(System.in);
	      String userInput;
	        
	      System.out.println(prompt);
	      System.out.print("<enter> to continue ...");
	      userInput = sc.nextLine();
	   }
			
	   /* Class Constants
	   */
	   public static final char NO_WINNER = ' ';
	   public static final int COLUMN_FULL = -2;
		private static final char EMPTY = ' ';
	   private static final int NUM_TO_WIN = 4;
	    
	   /* Attributes
		*/
	   // Common sizes: 7 columns, 6 rows, 5x4, 6x5, 8x7, 9x7, 10x7, 8x8.
		private int numRows;
	   private int numCols;
	 
	   private char board[][];
		    
		/* Constructor(s)
		*/
		public C4board(int rows, int cols) {
	      setNumRows(rows);
	      setNumCols(cols);
			board = new char[numRows][numCols];
			for (int i=0; i < numRows; i++)
				for (int j=0; j < numCols; j++)
					setBoard(i,j,EMPTY);
		}
		
	   /* Instance Methods
	   */
		/* Getters
		*/
	   public char getBoard(int row, int col) {
	      return board[row][col];
		}
	    
	   public int getNumRows() {
	      return numRows;
	   }
	    
	   public int getNumCols() {
	      return numCols;
	   }
	    
	   /* Setters
	   */
	   public void setBoard(int row, int col, char gamePiece) {
	      if ((row >= numRows) || (col >=numCols))
	         System.out.println("C4board: row, col " + row + ", " + col + " illegal."); 
	      else
	         board[row][col] = gamePiece;
	   }
	    
	   public void setNumRows(int rows) {
	      numRows = rows;
	   }
	    
	   public void setNumCols(int cols) {
	      numCols = cols;
	   }
	 
	   /* Actions
	   */
		public void displayBoard() {
			System.out.println("Board (" + numRows + " x " + numCols + ") is: ");
			for (int i=numRows-1; i >= 0; i--) {
				for (int j=0; j < numCols; j++)
					if (getBoard(i,j) != EMPTY)
						System.out.print(" " + board[i][j] + " ");
				else
					System.out.print(" _ ");
				System.out.println();
			} 
		}
	    
	   public int getFirstAvailableRow(int column) {
	      char gamePiece;
	      boolean rowFound = false;       
	      int row = numRows-1;
	        
	      while ((row >= 0) && !rowFound) {
	         gamePiece = getBoard(row,column);

	         if (gamePiece == EMPTY)
	               row--;
	         else {
	            row++;
	            if (row < numRows)
	               rowFound = true;
	            else
	               row = COLUMN_FULL;
	         }
	      } 
	      if (row == -1) row = 0;
	      return row;
	   }
		
		public boolean isTied() {
			boolean tie = true;
			for (int i=0; i < numRows && tie; i++)
				for (int j=0; j < numCols && tie; j++) 
					if (getBoard(i,j) == EMPTY)
						tie = false;
			return(tie);
		}
	    
		/* Each one of the following 7 check*() method
	        returns the player piece of the winner
	        or NO_WINNER if no one won.
	   */
	   public char checkSouth(C4boardPosition move) { 
	      /* to be completed
	      */
		   int curRow = move.getRow();
		   int curCol = move.getColumn();
		   if (curRow >= NUM_TO_WIN - 1) {
			   if (getBoard(curRow, curCol) == getBoard(curRow - 1, curCol)
					   && getBoard(curRow - 1, curCol) == getBoard(curRow - 2, curCol)
					   && getBoard(curRow - 2, curCol) == getBoard(curRow - 3, curCol)) {
				   return board[curRow][curCol];
			   }
		   }
	      return NO_WINNER;
	   }
	     
	   public char checkEast(C4boardPosition move) {
	      /* to be completed
	      */
		  int curRow = move.getRow();
		  int curCol = move.getColumn();
		  if (curCol <= NUM_TO_WIN - 2) {
			  if (getBoard(curRow, curCol) == getBoard(curRow, curCol + 1)
					  && getBoard(curRow, curCol + 1) == getBoard(curRow, curCol + 2)
					  && getBoard(curRow, curCol + 2) == getBoard(curRow, curCol + 3)) {
				  return board[curRow][curCol];
			  }
		  }
		  
		  if (curCol == NUM_TO_WIN - 2) {
			  if (getBoard(curRow, curCol) == getBoard(curRow, curCol - 1)
					  && getBoard(curRow, curCol) == getBoard(curRow, curCol + 1)
					  && getBoard(curRow, curCol + 1) == getBoard(curRow, curCol + 2)) {
				  return board[curRow][curCol];
			  }
		  }
		  
	      return NO_WINNER;
	   }
	    
	   public char checkWest(C4boardPosition move) {
	      /* to be completed
	      */
		   int curRow = move.getRow();
		   int curCol = move.getColumn();
			  if (curCol >= NUM_TO_WIN - 1) {
				  if (getBoard(curRow, curCol) == getBoard(curRow, curCol - 1)
						  && getBoard(curRow, curCol - 1) == getBoard(curRow, curCol - 2)
						  && getBoard(curRow, curCol - 2) == getBoard(curRow, curCol - 3)) {
					  return board[curRow][curCol];
				  }
			  }
			  
			  if (curCol == NUM_TO_WIN - 2) {
				  if (getBoard(curRow, curCol) == getBoard(curRow, curCol + 1)
						  && getBoard(curRow, curCol) == getBoard(curRow, curCol - 1)
						  && getBoard(curRow, curCol - 1) == getBoard(curRow, curCol - 2)) {
					  return board[curRow][curCol];
				  }
			  }
		   
	      return NO_WINNER;
	   }
	    
	   public char checkNorthWest(C4boardPosition move) {
	      /* to be completed
	      */
		   int curRow = move.getRow();
		   int curCol = move.getColumn();
		   if (curRow <= NUM_TO_WIN - 1 && curCol <= NUM_TO_WIN - 2) {
			   if (getBoard(curRow, curCol) == getBoard(curRow + 1, curCol + 1)
					   && getBoard(curRow + 1, curCol + 1) == getBoard(curRow + 2, curCol + 2)
					   && getBoard(curRow + 2, curCol + 2) == getBoard(curRow + 3, curCol + 3)) {
				   return board[curRow][curCol];
			   }
		   }
		   
	      return NO_WINNER;
	   }
	    
	   public char checkNorthEast(C4boardPosition move) {
	      /* to be completed
	      */
		   int curRow = move.getRow();
		   int curCol = move.getColumn();
		   if (curRow >= NUM_TO_WIN - 1 && curCol <= NUM_TO_WIN - 2) {
			   if (getBoard(curRow, curCol) == getBoard(curRow - 1, curCol + 1)
					   && getBoard(curRow - 1, curCol + 1) == getBoard(curRow - 2, curCol + 2)
					   && getBoard(curRow - 2, curCol + 2) == getBoard(curRow - 3, curCol + 3)) {
				   return board[curRow][curCol];
			   }
		   }
		   
	      return NO_WINNER;
	   }
	    
	   public char checkSouthWest(C4boardPosition move) {
	      /* to be completed
	      */
		  int curRow = move.getRow();
		  int curCol = move.getColumn();
		  if (curRow >= NUM_TO_WIN - 1 && curCol >= NUM_TO_WIN - 1) {
			  if (getBoard(curRow, curCol) == getBoard(curRow - 1, curCol - 1)
					  && getBoard(curRow - 1, curCol - 1) == getBoard(curRow - 2, curCol - 2)
					  && getBoard(curRow - 2, curCol - 2) == getBoard(curRow - 3, curCol - 3)) {
				  return board[curRow][curCol];
			  }
		  }
		  if (curRow >= NUM_TO_WIN - 3 && curRow <= NUM_TO_WIN + 1 && curCol <= NUM_TO_WIN && curCol >= NUM_TO_WIN - 3) {
			  if (getBoard(curRow, curCol) == getBoard(curRow + 1, curCol - 1)
				  && getBoard(curRow, curCol) == getBoard(curRow - 1, curCol + 1)
					&& getBoard(curRow - 1, curCol + 1) == getBoard(curRow - 2, curCol + 2)) {
				  return board[curRow][curCol];
			  }
		  }
		   
	      return NO_WINNER;
	   }
	    
	   public char checkSouthEast(C4boardPosition move) {
	      /* to be completed
	      */
		   int curRow = move.getRow();
		   int curCol = move.getColumn();
		   if (curRow <= NUM_TO_WIN - 1 && curCol >= NUM_TO_WIN - 1) {
			   if (getBoard(curRow, curCol) == getBoard(curRow + 1, curCol - 1)
					   && getBoard(curRow + 1, curCol - 1) == getBoard(curRow + 2, curCol - 2)
					   && getBoard(curRow + 2, curCol - 2) == getBoard(curRow + 3, curCol - 3)) {
				   return board[curRow][curCol];
			   }
		   }
		  
	      return NO_WINNER;
	   }
	        
		public char getWinner(C4boardPosition move) {
	      /* Check for a winner in 7 directions
	         only check the next direction if a winner was not seen
	         Note: no need to check north!! (Why not?)
	           
	         Things to note:
	            - (0,0) is the bottom left element in the board
	            - Consider the following board where * is the game piece
	               just played.
	             
	             NW   NE
	              W * E
	             SW S SE
	             
	            We must check 7 directions to see if there are 4 in a row:
	            NW - north west
	            W - west
	            SW - south west
	            S - south
	            NE - north east
	            E - east
	            SE - south east
	      */
	       
	      char whoWon = checkSouth(move);
	      if (whoWon == EMPTY)
	         whoWon = checkEast(move);
	      if (whoWon == EMPTY)
	         whoWon = checkWest(move);
	      if (whoWon == EMPTY)
	         whoWon = checkNorthWest(move);
	      if (whoWon == EMPTY)
	         whoWon = checkNorthEast(move);  
	      if (whoWon == EMPTY)
	         whoWon = checkSouthEast(move);
	      if (whoWon == EMPTY)
	         whoWon = checkSouthWest(move);
	                            
			return(whoWon);
		}
}
