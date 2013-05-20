package lorian.graph.function;

public enum MathChars 
{
	Frac_OneThird ('\u2153'),
	Frac_TwoThird ('\u2154'),
	Frac_OneFifth ('\u2155'),
	Frac_TwoFifth ('\u2156'),
	Frac_ThreeFifth ('\u2157'),
	Frac_FourFifth ('\u2158'),
	Frac_OneSixth ('\u2159'),
	Frac_FiveSixth ('\u215A'),
	Frac_OneEights ('\u215B'),
	Frac_ThreeEights ('\u215C'),
	Frac_FiveEights ('\u215D'),
	Frac_SevenEights ('\u215E'),
	Frac_OneFourth ('\u00BC'),
	Frac_OneHalf ('\u00BD'),
	Frac_ThreeFourth ('\u00BE'),
	Frac_One ('\u215F'),
	
	Sup_0 ('\u2070'),
	Sup_1 ('\u00B9'),
	Sup_2 ('\u00B2'),
	Sup_3 ('\u00B3'),
	Sup_4 ('\u2074'),
	Sup_5 ('\u2075'),
	Sup_6 ('\u2076'),
	Sup_7 ('\u2077'),
	Sup_8 ('\u2078'),
	Sup_9 ('\u2079'),
	Sup_Plus ('\u207A'),
	Sup_Minus ('\u207B'),
	Sup_ParenthesisOpen ('\u207D'),
	Sup_ParenthesisClose ('\u207E'),
	
	Root_2 ('\u221A'),
	Root_3 ('\u221B'),
	Root_4 ('\u221C'),
	
	Pi ('\u03C0'),
	e ('\u0435'),

	Integral ('\u222B'),
	Infinity ('\u221E');
	
	private char code;
	
	private MathChars(char code)
	{
		this.code = code;
	}
	public char getCode()
	{
		return this.code;
	}
}
