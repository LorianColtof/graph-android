package lorian.graph.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Term {
	
	protected boolean parsed = false;
	
	protected int index = 0;
	protected char argumentChar = 'x';
	protected List<Factor> factors;
	
	public Term()
	{
		factors = new ArrayList<Factor>();
	}
	public Term(char argumentChar)
	{
		this.argumentChar = argumentChar;
		factors = new ArrayList<Factor>();
	}
	
	
	
	protected String GetEverythingBetweenParentheses(String s)
	{
		char ch;
		int funcdepth = 0;
		String result = "(";
		index++;
		while(index < s.length())
		{
			ch = s.charAt(index);
			if(ch == '(')
			{
				funcdepth++;
			}
			else if(ch == ')')
			{
				if(funcdepth > 0 ) funcdepth--;
				else if(funcdepth==0)
				{
					result += ch;
					return result;
				}
			}
			result += ch;
			index++;
		}
		return result;
	}
	
	protected String exponentTimesMinusOne(String s)
	{
		if(s.trim().length() == 0) return "";
		return String.format("(%s)^-1", s);
	}
	protected List<String> SplitIntoFactors(String s)
	{
		List<String> factors = new ArrayList<String>();
		char ch;
		String newfactor = "";
		boolean ignoreminplus = true;
		boolean inexponent = false;
		boolean division = false;
		while(index < s.length())
		{
			ch = s.charAt(index); 

				if(!((ch >= '0' && ch <= '9') || ch == '.'))
				{
					if(ch=='*' || ch == '/')
					{ 
						if(division) newfactor = exponentTimesMinusOne(newfactor);
						if(newfactor.length() > 0) factors.add(newfactor);
						newfactor = "";
						ignoreminplus = true;
						if(ch == '/') division = true;
						else division = false;
					}
					
					else if(ch==argumentChar)
					{
						if(inexponent)
						{
							newfactor += ch;
						}
						else if(newfactor.length() > 0)
						{
							if(!ignoreminplus)
							{
								if(division) newfactor = exponentTimesMinusOne(newfactor);
								if(newfactor.length() > 0) factors.add(newfactor);
								newfactor = "";
								division = false;
								ignoreminplus = true;
								inexponent = false;
								continue;
							}
							else
							{
								boolean neg = false;
								int i=0;
								String tmp = "";
								while(i < newfactor.length())
								{
									if(newfactor.charAt(i) == '-') neg = !neg;
									i++;
								}
								if(neg) tmp += "-";
								tmp += "1";
								if(division) tmp = exponentTimesMinusOne(tmp);
								factors.add(tmp);
								newfactor = "";
								division = false;
								ignoreminplus = true;
								continue;
							}
						}
						
						else
						{
							newfactor += ch;
							if(index+1 < s.length())
							{
								if(s.charAt(index+1) != '^')
								{
									if(division) newfactor = exponentTimesMinusOne(newfactor);
									if(newfactor.length() > 0) factors.add(newfactor);
									division = false;
									newfactor = "";
									ignoreminplus = true;
								}
								
							}
							
							
							
						}
					}
					
					
					else if(ch=='+' || ch=='-')
					{
						if(!ignoreminplus)
						{
							if(division) newfactor = exponentTimesMinusOne(newfactor);
							if(newfactor.length() > 0) factors.add(newfactor);
							division = false;
							newfactor = "";
							ignoreminplus = true;
							inexponent = false;
							continue;
						}
						else newfactor += ch;
					}
					else if(ch == '(')
					{
						if(!Util.StringContains(newfactor, Util.LowercaseAlphabethWithout(argumentChar)) && !Util.StringContains(newfactor, "^"))
						{
							if(newfactor.length() > 0) 
							{
								if(division) newfactor = exponentTimesMinusOne(newfactor);
								factors.add(newfactor);
								division = false;
								newfactor = "";
							}
						}
						newfactor += GetEverythingBetweenParentheses(s);
						if(index+1 < s.length())
						{
							if(s.charAt(index+1) == '^')
							{
								index++;
								continue;
							}
						}
						if(division) newfactor = exponentTimesMinusOne(newfactor);
						if(newfactor.length() > 0) factors.add(newfactor);
						division = false;
						newfactor = "";
						ignoreminplus = true;
						inexponent = false;
						
					}
					else if(ch == '^')
					{
						ignoreminplus = true;
						inexponent = true;
						newfactor += ch;
						index++;
						continue;
					}

					else if(Util.StringContains("" + ch, Util.LowercaseAlphabethWithout(argumentChar) + "()+-^"))
					{
						if(division) newfactor = exponentTimesMinusOne(newfactor);
						if(newfactor.length() > 0) factors.add(newfactor);
						int tmpindex = s.indexOf('(', index);
						newfactor = s.substring(index, tmpindex);
						index = tmpindex;
						newfactor += GetEverythingBetweenParentheses(s);
						if(index+1 < s.length())
						{
							if(s.charAt(index+1) == '^')
							{
								index++;
								continue;
							}
						}
						if(division) newfactor = exponentTimesMinusOne(newfactor);
						if(newfactor.length() > 0) factors.add(newfactor);
						division = false;
						newfactor = "";
						ignoreminplus = true;
						inexponent = false;
						
					}
					
					else
						newfactor += ch;
				}
				else
				{
					newfactor += ch;
				}
				if(ignoreminplus && newfactor.length() > 0 && !(ch == '+' || ch == '-')) ignoreminplus = false;
			
			index++;
		}
		if(division) newfactor = exponentTimesMinusOne(newfactor);
		if(newfactor.length() > 0) factors.add(newfactor);
		return factors;
	}
	
	public boolean Parse(String s)
	{
		s = Util.removeWhiteSpace(s).toLowerCase(Locale.ENGLISH);
		boolean result = true;
		List<String> factorstrs = SplitIntoFactors(s);
		//System.out.println(factorstrs);
		for(String factorstr: factorstrs)
		{
			Factor factor = new Factor(this.argumentChar);
			if(!factor.Parse(factorstr)) { 
				result = false;
				factors.add(null);
			}
			else
				factors.add(factor);
		}
		parsed = result;
		return result;
	}
	

	public double Calc(double arg)
	{
		if(!parsed) return 0;
		double product = 1;
		for(Factor fac: factors)
		{
			product *= fac.Calc(arg);
		}
		return product;
		
	}
	
	public List<Factor> getFactors()
	{
		return this.factors;
	}
}


