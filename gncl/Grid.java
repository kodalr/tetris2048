import java.awt.Color; // import Color class
import java.awt.Point; // import Point class
import java.util.Arrays;

import edu.princeton.cs.introcs.StdDraw;

// A class representing the tetris game grid
public class Grid {
	// Private data fields
	private Color[][] colorMatrix;
	private Color emptySquare; // color used for empty squares 
	private tile[][] tileMatrix; // a matrix storing colors of all squares
	public int[][] numberMatrix;


	// Constructor
	Grid (int n_rows, int n_cols) {
		// assigning color used for empty squares
		emptySquare = StdDraw.BOOK_LIGHT_BLUE;
		// creating colorMatrix with given dimensions
		colorMatrix= new Color[n_rows][n_cols];
		tileMatrix = new tile[n_rows][n_cols];
		// initializing colorMatrix with color emptySquare for all its elements 
		// using initMatrix method defined below
		numberMatrix = new int[n_rows][n_cols];
		initMatrix();	
	}

	// Method used for initializing colorMatrix 
	public void initMatrix() {
		for (int row = 0; row < tileMatrix.length; row++) {
			for (int col = 0; col < tileMatrix[0].length; col++) {
				colorMatrix[row][col] = emptySquare;
				numberMatrix[row][col]=0;

			}
		}

	}
	// Method used for checking whether the square with given indices is inside the grid or not
	public boolean isInside(int row, int col) {
		if (row < 0 || row >= colorMatrix.length)
			return false;
		if (col < 0 || col >= colorMatrix[0].length)
			return false;
		return true;
	}
	// Getter method for getting color of the square with given indices
	public Color getColor(int row, int col) {
		if (isInside(row, col))
			return colorMatrix[row][col];
		else
			return null;
	}
	// Setter method for setting color of the square with given indices
	public void setColor(Color color, int row, int col) {
		if (isInside(row, col))
			colorMatrix[row][col] = color;
	}
	// Method used for checking whether the square with given indices is occupied or empty
	public boolean isOccupied(int row, int col) {
		return colorMatrix[row][col] != emptySquare;
	}
	public boolean checkRowComplete(int row){
		for (int c = 0; c < colorMatrix[0].length; c++){
			if (!isOccupied(row, c)){
				return false;
			}
		}
		return true;
	}
	public boolean checkRowClear(int row){
		for (int c = 0; c < colorMatrix[0].length; c++){
			if (isOccupied(row, c)){
				return false;
			}
		}
		return true;
	}
	public void deleteRow(int rowToDelete, int firstClearRowAbove){
		// move rows (above rowToDelete) that contain occupied squares down by 1
		for (int row = rowToDelete; row < firstClearRowAbove - 1; row++){
			for (int col = 0; col < colorMatrix[0].length; col++){

				colorMatrix[row][col] = colorMatrix[row + 1][col];
				numberMatrix[row][col] = numberMatrix[row+1][col];


			}
		}
		// delete the row below firstClearRowAbove
		for (int col = 0; col < colorMatrix[0].length; col++){
			colorMatrix[firstClearRowAbove - 1][col] = emptySquare;
			numberMatrix[firstClearRowAbove - 1][col] = 0;
		}
	}
	public void updater() {
		int dustu=0;
		
		for(int i=numberMatrix.length-1;i>0;i--) {
			for(int j=numberMatrix[0].length-1;j>=0;j--) {
				if((numberMatrix[i-1][j]==0) && (numberMatrix[i][j]!=0) ) {
					
					numberMatrix[i-1][j]=numberMatrix[i][j];
					//colorMatrix[i-1][j]=new Color(255,171-(2*numberMatrix[i][j]),0);
					colorMatrix[i-1][j] = colorMatrix[i][j];
					deleteRowCol(i,j);
					dustu=1;
					if(dustu==1) {
						updater();
						
					}
					
				}
				
			}
			//checkRowComplete(i);
			
			}
		
		
		
	}
	public void eliminateFullRows() {
		int topmostRow = colorMatrix.length - 1;
		int rowToDelete, firstClearRowAbove = topmostRow;
		int colToDelete;
		int row = topmostRow;
		for(int i=numberMatrix.length-2;i>=0;i--) {
			
			


				for(int j=0;j<numberMatrix[0].length;j++) {
					if(numberMatrix[i][j]==numberMatrix[i+1][j]) {
						numberMatrix[i][j]+=numberMatrix[i+1][j];
						
						//colorMatrix[i][j]=new Color(255,171-(2*numberMatrix[i][j]),0);
						deleteRowCol(i+1,j);

					
				}	
			}
		}
		while (row >= 0){
			if (checkRowClear(row))
				firstClearRowAbove = row;
			else if (checkRowComplete(row)) { 
				rowToDelete = row;
				deleteRow(rowToDelete, firstClearRowAbove);
			}
			row--;
		}
	}
	public void deleteRowCol(int rowToDelete, int colToDelete){
		// move rows (above rowToDelete) that contain occupied squares down by 1



		// delete the row below firstClearRowAbove

		colorMatrix[rowToDelete][colToDelete] = emptySquare;
		numberMatrix[rowToDelete][colToDelete] = 0;

	}







	// Method for updating the game grid with a placed (stopped) tetromino L
	public void updateGrid(Point[] occupiedSquaresByTetrominoL, Color colorOfTetrominoL, tile[][] xtile) {
		int n=0;
		int m=0;
		tileMatrix= xtile;
		//System.out.println(tileMatrix[0][0].getCoordinateMatrix());
		for (Point point: occupiedSquaresByTetrominoL)
			for(n=0;n<tileMatrix.length;n++) {
				for(m=0;m<tileMatrix[0].length;m++) {

					if (isInside(point.y, point.x)) {
						if(tileMatrix[n][m]!=null) {
							if(tileMatrix[n][m].getCoordinateMatrix().equals(point)) {
								numberMatrix[point.y][point.x] = tileMatrix[n][m].getNumber();
								System.out.println((tileMatrix[n][m].getNumber()));
								colorMatrix[point.y][point.x] = tileMatrix[n][m].getBackgroundColor();
							}
						}





					}

				}
			}
		// clear rows that are full of occupied squares
		eliminateFullRows();
		updater();
	}
	// Method used for displaying the grid
	public void display() {
		// drawing squares
		for (int row = 0; row < colorMatrix.length; row++)
			for (int col = 0; col < colorMatrix[0].length; col++) {
				//colorMatrix[row][col]=
				StdDraw.setPenColor(colorMatrix[row][col]);
				StdDraw.filledSquare(col, row, 0.5);
				if(numberMatrix[row][col]!=0) {
					int redt = 0, greent = 0, bluet = 0;
					Color textColor = new Color(redt, greent, bluet);
					StdDraw.setPenColor(textColor);
					StdDraw.text(col,row, Integer.toString(numberMatrix[row][col]));
				}
			}
		// drawing the grid
		StdDraw.setPenColor(StdDraw.BOOK_BLUE);
		for (double x = -0.5; x < colorMatrix[0].length; x++) // vertical lines
			StdDraw.line(x, -0.5, x, colorMatrix.length - 0.5);
		for (double y = -0.5; y < colorMatrix.length; y++) // horizontal lines
			StdDraw.line(-0.5, y, colorMatrix[0].length - 0.5, y);
	}
}