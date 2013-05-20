package lorian.graph.function;

public class Variable {
	private final char varchar;
	private double value;
	
	public Variable(char varchar, double value)
	{
		this.varchar = ("" + varchar).toUpperCase().charAt(0);
		if(this.varchar < 'A'|| this.varchar > 'Z')
		{
			varchar = '?';
			value = 0;
		}
		else
			this.value = value;
	}
	
	public char getVarChar()
	{
		return varchar;
	}
	
	public void setValue(double newval)
	{
		this.value = newval;
	}
	public double getValue()
	{
		return value;
	}
}
