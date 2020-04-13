import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class tile {
	private int number; // number on the tile
	private Color backgroundColor;// color used for the tile
	private Point coordinateMatrix;
	private final static int[] nums= {2,4};

	tile(){
		Random r=new Random();
		int index= r.nextInt(2);
		number=nums[index];
		if(number==2) {
			backgroundColor=new Color(255,229,0);

		}
		else if(number==4) {


			backgroundColor=new Color(255,171,0);
		}
		else {
			backgroundColor = new Color(255,171-(2*number),0);
		}

	}


	tile( Point coordinateMatrix) {
		this();
		this.coordinateMatrix=coordinateMatrix;
	}

	public Point getCoordinateMatrix() {
		return coordinateMatrix;
	}
	public void setCoordinateMatrix(Point coordinateMatrix) {
		this.coordinateMatrix = coordinateMatrix;
	}


	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor() {
		if(this.number==2) {
			this.backgroundColor=new Color(255,229,0);

		}
		else if(this.number==4) {


			this.backgroundColor=new Color(255,171,0);
		}
		else {
			this.backgroundColor = new Color(255,171-(2*number),0);
		}

	} 







}
