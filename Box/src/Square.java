import java.awt.Color;

public class Square {
	
	private int squareNum;
	private boolean isHit;
	private Color background;
	
	public Square(int num) {
		isHit = false;
		squareNum = num;
		background = null;
	}
	
	public int whatNum() {
		return squareNum;
	}
	
	public int value() {
		return squareNum + 1;
	}
	
	public boolean isSquareHit() {
		return isHit;
	}
	
	public Color getColor() {
		return background;
	}
	
	public void setNum(int num) {
		squareNum = num;
	}
	
	public void hit() {
		isHit = true;
		background = new Color(240,240,130);
	}
	
	public void setSquareHit(boolean hit) {
		isHit = hit;
	}
	
	public void setBackground(Color background) {
		this.background = background;
	}
	
}
