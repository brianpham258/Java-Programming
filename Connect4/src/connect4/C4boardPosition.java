package connect4;

public class C4boardPosition {
	private static int ILLEGAL_POSITION = -1;
    
	   private int row;
	   private int column;
	    
	   public C4boardPosition() {
	      row = ILLEGAL_POSITION;
	      column = ILLEGAL_POSITION;
	   }
	    
	   public C4boardPosition(int row, int column) {
	      this.row = row;
	      this.column = column;
	   }
	    
	   // Instance methods
	   // Getters
	   public int getRow() {
	        return row;
	   }
	    
	   public int getColumn() {
	      return column;
	   }
	    
	   // Setters
	   public void setRow(int row) {
	      this.row = row;
	   }
	    
	   public void setColumn(int column) {
	      this.column = column;
	   }
	    
	    
	   // Overriders
	   public String toString() {
	      return("Board position: [" + row + ", " + column + "]");
	   }
}
