package lorian.graph.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lorian.graph.android.opengl.Color;

public class Function {
	protected char argumentChar = 'x';
	protected List<Term> terms;  
	protected Color color;
	protected boolean isEmpty = true;
	protected boolean draw = true;
	protected List<Variable> variables;
	protected String RawInputString = "";
	
	public Function()
	{
		terms = new ArrayList<Term>();
		setColor(new Color(0f, 0f, 0f));
		
		variables = new ArrayList<Variable>();
	}
	public Function(char argumentChar) 
	{
		this.argumentChar = ("" + argumentChar).toLowerCase(Locale.ENGLISH).charAt(0);
		terms = new ArrayList<Term>();
		variables = new ArrayList<Variable>();
		setColor(new Color(0f, 0f, 0f));
		
	}
	public Function(char argumentChar, String s)
	{
		this.argumentChar = ("" + argumentChar).toLowerCase(Locale.ENGLISH).charAt(0);
		terms = new ArrayList<Term>();
		variables = new ArrayList<Variable>(); 
		setColor(new Color(0f, 0f, 0f));
		Parse(s);
	}
	public Function(String s)
	{
		terms = new ArrayList<Term>();
		setColor(new Color(1f, 1f, 1f));
		variables = new ArrayList<Variable>();
		Parse(s);
	}
	
	public void addVariable(Variable var)
	{
		if(var.getVarChar() == '?') return;
		int i = 0;
		for(Variable v: variables)
		{
			if(v.getVarChar() == var.getVarChar())
			{
				variables.set(i, var);
				return;
			}
			i++;
		}
		variables.add(var);
		if(!isEmpty)
		{
			terms.clear();
			isEmpty = true;
			this.Parse(RawInputString);
		}
	}
	public void setVariableValue(char varchar, double value)
	{
		int i = 0;
		for(Variable v: variables)
		{
			if(v.getVarChar() == varchar)
			{
				Variable v1 = v;
				v1.setValue(value);
				variables.set(i, v1);
			}
			i++;
		}
		if(!isEmpty)
		{
			terms.clear();
			isEmpty = true;
			this.Parse(RawInputString);
		}
	}
	public double getVariableValue(char varchar)
	{
		int i = 0;
		for(Variable v: variables)
		{
			if(v.getVarChar() == varchar)
			{
				variables.get(i).getValue();
			}
			i++;
		}
		return Double.NaN;
	}
	
	protected String FillInVariables(String s) 
	{
		if(variables == null) return s;
		for(Variable v: variables)
		{
			s = Util.StringReplace(s, v.getVarChar(), "(" + Util.doubleToString(v.getValue()) + ")");
		}
		return s;
	}
	protected String PreProcess(String s) 
	{
		String ss = s;
		
		// Constants
		ss = Util.StringReplace(ss, MathChars.Pi.getCode(), "const(pi)");
		ss = Util.StringReplace(ss, MathChars.e.getCode(), "const(e)");
		
		// Fractions
		ss = Util.StringReplace(ss, MathChars.Frac_OneThird.getCode(), "(1/3)");
		ss = Util.StringReplace(ss, MathChars.Frac_TwoThird.getCode(), "(2/3)");
		ss = Util.StringReplace(ss, MathChars.Frac_OneFifth.getCode(), "(1/5)");
		ss = Util.StringReplace(ss, MathChars.Frac_TwoFifth.getCode(), "(2/5)");
		ss = Util.StringReplace(ss, MathChars.Frac_ThreeFifth.getCode(), "(3/5)");
		ss = Util.StringReplace(ss, MathChars.Frac_FourFifth.getCode(), "(4/5)");
		ss = Util.StringReplace(ss, MathChars.Frac_OneSixth.getCode(), "(1/6)");
		ss = Util.StringReplace(ss, MathChars.Frac_FiveSixth.getCode(), "(5/6)");
		ss = Util.StringReplace(ss, MathChars.Frac_OneEights.getCode(), "(1/8)");
		ss = Util.StringReplace(ss, MathChars.Frac_ThreeEights.getCode(), "(3/8)");
		ss = Util.StringReplace(ss, MathChars.Frac_FiveEights.getCode(), "(5/8)");
		ss = Util.StringReplace(ss, MathChars.Frac_SevenEights.getCode(), "(7/8)");
		ss = Util.StringReplace(ss, MathChars.Frac_OneFourth.getCode(), "(1/4)");
		ss = Util.StringReplace(ss, MathChars.Frac_OneHalf.getCode(), "(1/2)");
		ss = Util.StringReplace(ss, MathChars.Frac_ThreeFourth.getCode(), "(3/4)");
		ss = Util.StringReplace(ss, MathChars.Frac_One.getCode(), "1/");
		
		
		// Exponents
		//TODO Make it possible to use two or more numbers
		ss = Util.StringReplace(ss, MathChars.Sup_0.getCode(), "^0");
		ss = Util.StringReplace(ss, MathChars.Sup_1.getCode(), "^1");
		ss = Util.StringReplace(ss, MathChars.Sup_2.getCode(), "^2");
		ss = Util.StringReplace(ss, MathChars.Sup_3.getCode(), "^3");
		ss = Util.StringReplace(ss, MathChars.Sup_4.getCode(), "^4");
		ss = Util.StringReplace(ss, MathChars.Sup_5.getCode(), "^5");
		ss = Util.StringReplace(ss, MathChars.Sup_6.getCode(), "^6");
		ss = Util.StringReplace(ss, MathChars.Sup_7.getCode(), "^7");
		ss = Util.StringReplace(ss, MathChars.Sup_8.getCode(), "^8");
		ss = Util.StringReplace(ss, MathChars.Sup_9.getCode(), "^9");
		
		// Roots
		ss = Util.StringReplace(ss, MathChars.Root_2.getCode(), "sqrt");
		ss = Util.StringReplace(ss, MathChars.Root_3.getCode(), "cbrt");
		ss = Util.StringReplace(ss, MathChars.Root_4.getCode(), "4rt");
		
		return ss;
	}
	
	public boolean Parse(String s)
	{
		try
		{
		RawInputString = s;
		s = Util.removeWhiteSpace(s);
		s = FillInVariables(s);
		s = s.toLowerCase(Locale.ENGLISH);
		
		s = PreProcess(s);
		String termstr = "";
		
		int start=0,index=0;
		
		if(s.charAt(0)=='-' || s.charAt(0)=='+')
			index++;
		
		while(index < s.length())
		{
			Term term = new Term(this.argumentChar);
			int funcdepth=0;
			boolean ignoreminplus = true;
			while(true)
			{
				if(index == s.length())
				{
					break;
				}
				else if(s.charAt(index)=='-' || s.charAt(index)=='+')
				{
					if(funcdepth==0 && !ignoreminplus) break;
				}
				else if(s.charAt(index)=='*' || s.charAt(index)=='^' || s.charAt(index) == ')')
				{
					ignoreminplus = true;
				}
				else ignoreminplus = false;
				
				if(s.charAt(index)=='(')
				{
					funcdepth++;
				}
				else if(s.charAt(index)==')')
				{
					if(funcdepth==0)
					{
						return false;
					}
					else {
						funcdepth--;
						ignoreminplus = false;
					}
				}
				index++;
			}
			
			
			termstr = s.substring(start, index);
			//System.out.println(termstr);
			
			if(!term.Parse(termstr)) 
			{
				clear();
				return false;
			}
			terms.add(term);
			
			//System.out.printf("Term: %s\n", term.toString());
			
			start = index;
			index++;
		}
		isEmpty = false;
		return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public Color getColor() {
		return color;
	}
	public Function setColor(Color newcolor) {
		this.color = newcolor;
		return this;
	}
	public List<Term> getTerms()
	{
		return this.terms;
	}
	public boolean isEmpty()
	{
		return this.isEmpty;
	}
	public void clear()
	{
		setColor(new Color(1f, 1f, 1f));
		terms.clear();
		isEmpty = true;
	}
	public double Calc(double arg)
	{
		if(isEmpty) return 0;
		try
		{
			double sum = 0;
			for(int i = 0; i < terms.size(); i++)
			{
				sum += terms.get(i).Calc(arg);
			}
			return sum;
		}
		catch (Exception e)
		{
			return 0;
		}
	}
	
	public boolean drawOn() {
		return draw;
	}
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	@Override
	public String toString()
	{
		return this.RawInputString;
	}

}
