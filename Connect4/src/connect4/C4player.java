package connect4;

import java.util.Scanner;

public class C4player {
	/* Attributes
	*/
	private char gamePiece;
	private boolean isHuman;
	
	/* Constructor(s)
	*/
	public C4player(char who, boolean human) {
	   gamePiece = who;
		if (human) isHuman = true;
		else isHuman = false;
	}
	
	public C4player(char who) {
		gamePiece = who;
		isHuman = true;
	}
	
	/* Instance Methods
	*/
	/* Getters
	*/
	public char getGamePiece() {
		return gamePiece;
	}
	
	public boolean isHuman() {
		return isHuman;
	}
	
	/* Setters
	*/
   public void setGamePiece(char gamePiece) {
      this.gamePiece = gamePiece;
   }
    
   public void setIsHuman(boolean value) {
      isHuman = value;
   }
    
   /* Action Methods
   */
   public C4boardPosition makeMove(C4board board, Scanner userInput) {
      boolean moveMade=false;
      int availableRow;
      int column;
      boolean illegal;
        
      do {
         illegal = false;
         do {
            System.out.println("Enter the column number where player: " + gamePiece + " moves.");
            System.out.print("\nColumn number: ");
            column = userInput.nextInt();
            if (column <= 0 || column > board.getNumCols()) {
               illegal = true;
               System.out.println("Illegal column: " + column);
            }
            else
               illegal = false;
         } while (illegal);
            
         availableRow = board.getFirstAvailableRow(column-1);
         if (availableRow != C4board.COLUMN_FULL) {
            board.setBoard(availableRow, column-1, gamePiece);
            moveMade = true;
         }
         else
            System.out.println("That column is full. Try again.");
      } while (!moveMade);
        
      C4boardPosition boardPos = new C4boardPosition(availableRow, column-1);
      return (boardPos);
   }
}
