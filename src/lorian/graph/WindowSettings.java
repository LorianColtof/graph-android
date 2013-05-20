package lorian.graph;

public class WindowSettings {

	private long Xmin, Xmax, Ymin, Ymax;
	private boolean grid;
	
	public WindowSettings()
	{
		setXmin(-10);
		setXmax(10);
		setYmin(-10);
		setYmax(10);
		setGrid(false);
	}
	public WindowSettings(long Xmin, long Xmax, long Ymin, long Ymax, boolean grid)
	{
		setXmin(Xmin);
		setXmax(Xmax);
		setYmin(Ymin);
		setYmax(Ymax);
		setGrid(grid);
	}
	
	public long getYmin() {
		return Ymin;
	}
	public void setYmin(long ymin) {
		Ymin = ymin;
	}
	public long getXmin() {
		return Xmin;
	}
	public void setXmin(long xmin) {
		Xmin = xmin;
	}
	public long getXmax() {
		return Xmax;
	}
	public void setXmax(long xmax) {
		Xmax = xmax;
	}
	public long getYmax() {
		return Ymax;
	}
	public void setYmax(long ymax) {
		Ymax = ymax;
	}
	public boolean gridOn() {
		return grid;
	}
	public void setGrid(boolean grid) {
		this.grid = grid;
	}
	
}
