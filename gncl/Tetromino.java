import java.util.Random;  // import Random class



import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color; // import Color class
import java.awt.Point; // import Point class

// A class for representing tetrominoes with 7 different shapes ('I', 'S', 'Z', 'O', 'T', 'L' and 'J')
public class Tetromino {

	// Private data fields
	private Color color; // color of the tetromino 
	private boolean[][] shapeMatrix;
	private tile[][] tileMatrix ; // shape of the tetromino 
	private Point[][] coordinateMatrix;
	public int[][] numberMatrix;
	// coordinates of the tetromino L w.r.t the game grid
	private int gridWidth, gridHeight; // dimensions of the tetris game grid
	public int number;


	// Constructor
	Tetromino (char type, int gridHeight, int gridWidth) {
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
		// color of the tetromino is determined randomly
		Random random = new Random();

		int red = random.nextInt(256), green = random.nextInt(256), blue = random.nextInt(256);
		color = new Color(red, green, blue);

		// set the shape of the tetromino based on the given type
		if (type == 'I') {
			// shape of the tetromino I in its initial orientation
			boolean [][] shape = {{false, true, false, false}, {false, true, false, false}, {false, true, false, false}, {false, true, false, false}};
			shapeMatrix = shape;
			int [][] number = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
			numberMatrix = number;

		}
		else if (type == 'S') {
			// shape of the tetromino S in its initial orientation
			boolean [][] shape = {{false, true, true}, {true, true, false}, {false, false, false}};
			shapeMatrix = shape;
			int [][] number = {{0,0,0},{0,0,0},{0,0,0}};
			numberMatrix = number;
		}
		else if (type == 'Z') {
			// shape of the tetromino Z in its initial orientation
			boolean [][] shape = {{true, true, false}, {false, true, true}, {false, false, false}};
			shapeMatrix = shape;
			int [][] number = {{0,0,0},{0,0,0},{0,0,0}};
			numberMatrix = number;
		}	
		else if (type == 'O') {
			// shape of the tetromino O in its initial orientation
			boolean [][] shape = {{true, true}, {true, true}};
			shapeMatrix = shape;
			int [][] number = {{0,0},{0,0}};
			numberMatrix = number;
		}			
		else if (type == 'T') {
			// shape of the tetromino T in its initial orientation
			boolean [][] shape = {{false, false, false}, {true, true, true}, {false, true, false}};
			shapeMatrix = shape;
			int [][] number = {{0,0,0},{0,0,0},{0,0,0}};
			numberMatrix = number;
		}
		else if (type == 'L') {
			// shape of the tetromino L in its initial orientation
			boolean [][] shape = {{false, true, false}, {false, true, false}, {false, true, true}};
			shapeMatrix = shape;
			int [][] number = {{0,0,0},{0,0,0},{0,0,0}};
			numberMatrix = number;
		}	
		else {
			// shape of the tetromino J in its initial orientation
			boolean [][] shape = {{false, true, false}, {false, true, false}, {true, true, false}};
			shapeMatrix = shape;
			int [][] number = {{0,0,0},{0,0,0},{0,0,0}};
			numberMatrix = number;
		}	



		// initial coordinates just before the tetromino  enters the game grid from the upper side
		// at a random horizontal position
		int n_rows = shapeMatrix.length, n_cols = shapeMatrix[0].length;

		coordinateMatrix = new Point[n_rows][n_cols];
		tileMatrix = new tile[n_rows][n_cols];
		int i=0;
		int lowerLeftCornerX = random.nextInt(gridWidth - (n_cols - 1)), lowerLeftCornerY = gridHeight;
		coordinateMatrix[n_rows - 1][0] = new Point(lowerLeftCornerX, lowerLeftCornerY);
		for (int row = n_rows - 1; row >= 0; row--)
			for (int col = 0; col < n_cols; col++) {
				if (row == n_rows - 1 && col == 0)
					continue;
				else if (col == 0) { 
					int currentX = coordinateMatrix[row + 1][col].x;
					int currentY = coordinateMatrix[row + 1][col].y + 1;
					coordinateMatrix[row][col] = new Point(currentX, currentY);
					continue;
				}
				int currentX = coordinateMatrix[row][col - 1].x + 1;
				int currentY = coordinateMatrix[row][col - 1].y; 
				coordinateMatrix[row][col] = new Point(currentX, currentY);

			}

		for (int row = 0; row < coordinateMatrix.length ; row++)
			for (int col = 0; col < coordinateMatrix[0].length ; col++) {
				Point point = coordinateMatrix[row][col];
				// considering newly entered tetromino  objects to the game grid that may have squares with point.y >= gridHeight
				if (  shapeMatrix[row][col]) {

					tileMatrix[row][col] = new tile(point);


				}
			}
	}


	public tile[][] getTileMatrix() {
		return tileMatrix;
	}

	//public void setTileMatrix(tile[] tileMatrix) {
		//this.tileMatrix = tileMatrix;
	//}

	// Getter method for getting the color of tetromino 
	public Color getColor() {
		return color;
	}
	// Method for displaying tetromino  on the game grid
	public void display() { 
		int i=0;
		for (int row = 0; row < coordinateMatrix.length ; row++)
			for (int col = 0; col < coordinateMatrix[0].length ; col++) {
				Point point = coordinateMatrix[row][col];
				// considering newly entered tetromino  objects to the game grid that may have squares with point.y >= gridHeight
				if (point.y < gridHeight  && shapeMatrix[row][col]) {



					int redt = 0, greent = 0, bluet = 0;

					StdDraw.setPenColor(tileMatrix[row][col].getBackgroundColor());
					StdDraw.filledSquare(point.x, point.y, 0.5);
					Color textColor = new Color(redt, greent, bluet);
					StdDraw.setPenColor(textColor);
					StdDraw.text(point.x, point.y, Integer.toString(tileMatrix[row][col].getNumber()));



				}
			}
	}
	// Method for moving tetromino  down by 1 in the game grid
	public boolean goDown(Grid gameGrid) {
		// Check whether tetromino  can go down or not
		boolean canGoDown = true;
		// determine the coordinates of the bottommost block for each column of tetromino 
		Point dummyPoint = new Point(-1, -1);
		Point[] bottommostBlock = new Point[shapeMatrix.length];
		for (int i = 0; i < bottommostBlock.length; i++)
			bottommostBlock[i] = dummyPoint;
		for (int col = 0; col < shapeMatrix[0].length; col++) {
			for (int row = shapeMatrix.length - 1; row >= 0; row--) {
				if (shapeMatrix[row][col]) {
					bottommostBlock[col] = coordinateMatrix[row][col];
					if (bottommostBlock[col].y == 0) // tetromino L cannot go down if it is already at y = 0
						canGoDown = false;
					break; // break the inner for loop
				}
			}
			if (!canGoDown)
				break; // break the outer for loop
		}
		// check if the grid square below the bottommost block is occupied for each column of tetromino L
		if (canGoDown) {
			for (int i = 0; i < bottommostBlock.length; i++) {
				// skip each column of tetromino L that does not contain any blocks
				if (bottommostBlock[i].equals(dummyPoint))
					continue;
				// skip each column of tetromino L whose bottommost block is out of the game grid 
				// (newly entered tetromino L objects to the game grid) 
				if (bottommostBlock[i].y > gridHeight)
					continue;
				if (gameGrid.isOccupied(bottommostBlock[i].y - 1, bottommostBlock[i].x)) {
					canGoDown = false;
					break; // break the for loop
				}
			}
		}
		// move tetromino  down by 1 in the game grid if it can go down
		if (canGoDown) {
			for (int row = 0; row < coordinateMatrix.length; row++)
				for (int col = 0; col < coordinateMatrix[0].length; col++)
					coordinateMatrix[row][col].y--;
		}
		// return the result
		return canGoDown;
	}
	// Method for returning the occupied squares w.r.t. the game grid by a placed (stopped) tetromino L 
	public Point[] getOccupiedSquares() {

		// all seven types of tetrominoes have 4 occupied squares
		Point[] occupiedSquares = new Point[4];
		int count = 0;
		for (int row = 0; row < coordinateMatrix.length; row++)
			for (int col = 0; col < coordinateMatrix[0].length; col++)
				if (shapeMatrix[row][col])
					occupiedSquares[count++] = coordinateMatrix[row][col];
		return occupiedSquares;
	}

	public int[] getOccupiedNumbers() {

		int[]  occupiedNumbers = new int[4];
		int count = 0;
		for (int row = 0; row < numberMatrix.length; row++)
			for (int col = 0; col < numberMatrix[0].length; col++)
				if (shapeMatrix[row][col])
					occupiedNumbers[count++] = numberMatrix[row][col];
		return occupiedNumbers;
	}
	// Method for moving tetromino L left by 1 in the game grid
	public boolean goLeft(Grid gameGrid) {
		// Check whether tetromino L can go left or not
		boolean canGoLeft = true;
		// determine the coordinates of the leftmost block for each row of tetromino L
		Point dummyPoint = new Point(-1, -1);
		Point[] leftmostBlock = new Point[shapeMatrix.length];
		for (int i = 0; i < leftmostBlock.length; i++)
			leftmostBlock[i] = dummyPoint;
		for (int row = 0; row < shapeMatrix.length; row++) {
			for (int col = 0; col < shapeMatrix[0].length; col++) {
				if (shapeMatrix[row][col]) {
					leftmostBlock[row] = coordinateMatrix[row][col];
					if (leftmostBlock[row].x == 0) // tetromino L cannot go left if it is already at x = 0
						canGoLeft = false;
					break; // break the inner for loop
				}
			}
			if (!canGoLeft)
				break; // break the outer for loop
		}
		// check if the grid square on the left of the leftmost block is occupied for each row of tetromino L
		if (canGoLeft) {
			for (int i = 0; i < leftmostBlock.length; i++) {
				// skip each row of tetromino L that does not contain any blocks
				if (leftmostBlock[i].equals(dummyPoint))
					continue;
				// skip each row of tetromino L whose leftmost block is out of the game grid 
				// (newly entered tetromino L objects to the game grid) 
				if (leftmostBlock[i].y >= gridHeight)
					continue;
				if (gameGrid.isOccupied(leftmostBlock[i].y, leftmostBlock[i].x - 1)) {
					canGoLeft = false;
					break; // break the for loop
				}
			}
		}
		// move tetromino L left by 1 in the game grid if it can go left
		if (canGoLeft) {
			for (int row = 0; row < coordinateMatrix.length; row++)
				for (int col = 0; col < coordinateMatrix[0].length; col++)
					coordinateMatrix[row][col].x--;
		}
		// return the result
		return canGoLeft;
	}
	// Method for moving tetromino L right by 1 in the game grid
	public boolean goRight(Grid gameGrid) {
		// Check whether tetromino L can go right or not
		boolean canGoRight = true;
		// determine the coordinates of the rightmost block for each row of tetromino L
		Point dummyPoint = new Point(-1, -1);
		Point[] rightmostBlock = new Point[shapeMatrix.length];
		for (int i = 0; i < rightmostBlock.length; i++)
			rightmostBlock[i] = dummyPoint;
		for (int row = 0; row < shapeMatrix.length; row++) {
			for (int col = shapeMatrix[0].length - 1; col >= 0; col--) {
				if (shapeMatrix[row][col]) {
					rightmostBlock[row] = coordinateMatrix[row][col];
					if (rightmostBlock[row].x == gridWidth - 1) // tetromino L cannot go right if it is already at x = gridWidth - 1
						canGoRight = false;
					break; // break the inner for loop
				}
			}
			if (!canGoRight)
				break; // break the outer for loop
		}
		// check if the grid square on the right of the rightmost block is occupied for each row of tetromino L
		if (canGoRight) {
			for (int i = 0; i < rightmostBlock.length; i++) {
				// skip each row of tetromino L that does not contain any blocks
				if (rightmostBlock[i].equals(dummyPoint))
					continue;
				// skip each row of tetromino L whose rightmost block is out of the game grid 
				// (newly entered tetromino L objects to the game grid) 
				if (rightmostBlock[i].y >= gridHeight)
					continue;
				if (gameGrid.isOccupied(rightmostBlock[i].y, rightmostBlock[i].x + 1)) {
					canGoRight = false;
					break; // break the for loop
				}
			}
		}
		// move tetromino L right by 1 in the game grid if it can go right
		if (canGoRight) {
			for (int row = 0; row < coordinateMatrix.length; row++)
				for (int col = 0; col < coordinateMatrix[0].length; col++)
					coordinateMatrix[row][col].x++;
		}
		// return the result
		return canGoRight;
	}
	// Method for rotating tetromino clockwise by 90 degrees
	public boolean rotate(Grid gameGrid) {
		// Check whether tetromino can rotate: all the blocks of the tetromino (including 
		// the empty ones) must be inside the game grid and there must be no occupied
		// grid square within the blocks of the tetromino
		int n = shapeMatrix.length; // n = number of rows = number of columns for both shapeMatrix and coordinateMatrix
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				int positionX = coordinateMatrix[row][col].x;
				int positionY = coordinateMatrix[row][col].y;
				if (!gameGrid.isInside(positionY, positionX))
					return false;
				if (gameGrid.isOccupied(positionY, positionX))
					return false;
			}
		}
		// rotate tetromino clockwise by 90 degrees
		boolean[][] rotatedShapeMatrix = new boolean[n][n]; 
		tile[][] rotatedtileMatrix = new tile[n][n];
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				rotatedShapeMatrix[col][n - 1 - row] = shapeMatrix[row][col];
				rotatedtileMatrix[col][n - 1 - row] = tileMatrix[row][col];
			}
		}	
		shapeMatrix = rotatedShapeMatrix;
		tileMatrix= rotatedtileMatrix;
		for (int row = 0; row < coordinateMatrix.length ; row++)
			for (int col = 0; col < coordinateMatrix[0].length ; col++) {
				Point point = coordinateMatrix[row][col];
				// considering newly entered tetromino  objects to the game grid that may have squares with point.y >= gridHeight
				if (  shapeMatrix[row][col]) {

					tileMatrix[row][col].setCoordinateMatrix(coordinateMatrix[row][col]);


				}
			}
		


		return true;
	}




}
