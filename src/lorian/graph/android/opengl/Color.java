package lorian.graph.android.opengl;

public class Color {
	
	
	private float r, g ,b;
	public Color(float r, float g, float b)
	{
		this.setR(r);
		this.setG(g);
		this.setB(b); 
	}
	public Color(int r, int g, int b)
	{
		this.setR(r / 255f);
		this.setG(g / 255f);
		this.setB(b / 255f); 
	}
	
	public float getR() {
		return r;
	}
	public void setR(float r) {
		this.r = r;
	}
	public float getG() {
		return g;
	}
	public void setG(float g) {
		this.g = g;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	public void setR_255(int r)
	{
		setR(r / 255f);
	}
	public void setG_255(int g)
	{
		setG(b / 255f);
	}
	public void setB_255(int b)
	{
		setB(b / 255f);
	}
	
}
