package connect4;

import java.util.Scanner;

public class C4play {
	public static void main(String args[]) {
	     
	      final char RED = 'R';
	      final char BLUE = 'B';
	        
	      char whoWon;
	      boolean won, tie;
	      String again;
			Scanner sc = new Scanner(System.in);
		    
	      do {
	         won = false;
	         tie = false;
	         C4board game = new C4board(7, 6);
				C4player player1 = new C4player('R');
				C4player player2 = new C4player('B');
				
	         System.out.println("Welcome to Connect4.");
	         game.displayBoard();
	            
	         do {
	            C4boardPosition moveIs = player1.makeMove(game, sc);
	            game.displayBoard();
	           
	            whoWon = game.getWinner(moveIs);
	            if (!(whoWon == C4board.NO_WINNER))
	               won = true;
	               if (!won)
	                  tie = game.isTied();
	          
	               if (!won && !tie) {
	                  moveIs = player2.makeMove(game, sc);
	                  game.displayBoard();
	               }
	                
	               whoWon = game.getWinner(moveIs);
	               if (!(whoWon == C4board.NO_WINNER))
	                  won = true;
	                  if (!won)
	                     tie = game.isTied();
	         } while (!won && !tie);
	            
	         if (tie) 
	            System.out.println("TIE GAME");
	         else {
	            System.out.println("WINNER! " + whoWon);
	         }
	            
	         System.out.print("Care to play again? (y/n) ");
	         again = sc.next();
	      } while (again.equalsIgnoreCase("y")); 
	   }
}
