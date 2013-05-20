package lorian.graph.function;

public class PointXY {
	private double X, Y;

	public PointXY()
	{
		setX(0);
		setY(0);
	}
	public PointXY(double x,double y)
	{
		setX(x);
		setY(y);
	}
	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s[x=%f,y=%f]", this.getClass().getName(), X, Y);
	}
	
}
