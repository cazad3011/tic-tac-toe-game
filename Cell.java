
public class Cell {
private int x;
private int y;
private int minimaxValue;
public Cell (int x ,int y){
	this.x=x;
	this.y=y;
}
public int getX() {
	return this.x;
}
public void setX(int x) {
	this.x = x;
}
public int getY() {
	return this.y;
}
public void setY(int y) {
	this.y = y;
}
public int getMinimaxValue() {
	return this.minimaxValue;
}
public void setMinimaxValue(int minimaxValue) {
	this.minimaxValue = minimaxValue;
}
@Override
	public String toString() {
		
		return "("+this.x+","+this.y+")";
	}
}
