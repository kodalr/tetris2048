import java.awt.Color; // import Color class
import java.awt.Point; // import Point class
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

// A program demonstrating the following tetris basics: 
// 1. drawing the game environment as a grid 
// 2. modeling the L shaped tetromino using 2d arrays 
// 3. tetromino entering the game environment from a random horizontal position
// 4. tetromino going down by one grid automatically in each iteration
// 5. using keyboard keys (a and d) for moving the tetromino left/right by one in each iteration
//    (checking for collisions with side boundaries and occupied squares in the grid)
// 6. detecting when the active tetromino stops due to reaching the bottom of the game environment 
//    or colliding with occupied squares in the grid
// 7. updating the game grid with each placed (stopped) tetromino
public class TetrisBasics {
	public static void main(String[] args) {
		// set the size of the drawing canvas
		StdDraw.setCanvasSize(500, 750);
		// set the scale of the coordinate system
		StdDraw.setXscale(-0.5, 7.5);
		StdDraw.setYscale(-0.5, 11.5);
		// double buffering is used for speeding up drawing needed to enable computer animations 
		// check https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html for details
		StdDraw.enableDoubleBuffering();
		
		// create a grid as the tetris game environment
		Grid gameGrid = new Grid(12, 8);
		// create the first tetromino to enter the game grid
		Tetromino t = createIncomingTetromino(12, 8);
		boolean createANewTetrominoL = false;
		
		// main animation loop
		while (true)  { 
			// keyboard interaction for moving the active tetromino left or right
			boolean success = false;
			if (StdDraw.hasNextKeyTyped()) {
                char ch = StdDraw.nextKeyTyped();            
                if (ch == 'a') // move the active tetromino left by one
                    success = t.goLeft(gameGrid);
                else if (ch == 'd') // move the active tetromino right by one
                	success = t.goRight(gameGrid);
                else if (ch == 's') // rotate the active tetromino clockwise by 90 degrees 
					success = t.rotate(gameGrid);
			
			}
			// move the active tetromino down by one if a successful move left/right is not performed
			if (!success)
				success = t.goDown(gameGrid);
			// place (stop) the active tetromino on the game grid if it cannot go down anymore
			createANewTetrominoL = !success;
			if (createANewTetrominoL) {
				// update the game grid by adding the placed tetromino
				Point[] occupiedSquares = t.getOccupiedSquares();
				Color color = t.getColor();
				int[] numbers = t.getOccupiedNumbers();
		        tile[][] tileMatrix;
				tileMatrix = t.getTileMatrix();
				System.out.println(Arrays.deepToString(tileMatrix));
				gameGrid.updateGrid(occupiedSquares,color,t.getTileMatrix());
				// create the next tetromino to enter the game grid 
				t = createIncomingTetromino(12, 8);
			}
			
			// clear the background (double buffering)
			StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
			// draw the game grid
			gameGrid.display();
			// draw the active tetromino
			t.display();
			// copy offscreen buffer to onscreen (double buffering)
			StdDraw.show();
			// pause for 200 ms (double buffering) 
			StdDraw.pause(200);
		}
		
	}
	// A method for creating the incoming tetromino to enter the game grid
		public static Tetromino createIncomingTetromino(int gridHeight, int gridWidth) {
			// shape of the tetromino is determined randomly
			char[] tetrominoNames = {'I', 'S', 'Z', 'O', 'T', 'L', 'J'};
			Random random = new Random();
			int randomIndex = random.nextInt(7);
			char randomName = tetrominoNames[randomIndex];
			// create and return the tetromino
			Tetromino tet = new Tetromino(randomName, gridHeight, gridWidth);
			return tet;
		}
		
}