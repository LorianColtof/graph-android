package lorian.graph.function;

import java.util.Locale;
import java.util.Random;

public class Calculate {

	
	private static double returnRounded(double d)
	{
		double r = Util.round(d, 0);
		if(Math.abs(r - d) < 0.0001)
			return r;
		else 
			return d;
	}
	
	public static PointXY Zero(Function f, double LowX, double UpX)
	{
		if(UpX <= LowX) return new PointXY(Double.NaN, Double.NaN);
		double step = 10 / 10000000.0;
		double closest = 1, closestX = LowX - 1;
		for(double x = LowX + 0.001; x < UpX; x += step)
		{
			if(f.Calc(x)==0) return new PointXY(x, 0);
			else if(Math.abs(f.Calc(x)) < Math.abs(closest))
			{
				closest = Math.abs(f.Calc(x));
				closestX = x;
			}
		}
		if(closestX == LowX-1)
			return new PointXY(Double.NaN, Double.NaN);
		else
			return new PointXY(returnRounded(Util.round(closestX, 6)), 0);
	}
	public static PointXY Minimum(Function f, double LowX, double UpX)
	{
		double dx = 0.000001;
		double lowestX = LowX - 1;
		double lowest = 9999999;
		double val;
		for(double x = LowX + dx; x < UpX; x += dx)
		{
			val = f.Calc(x);
			if(Double.isInfinite(-val)) return new PointXY(returnRounded(Util.round(x, 6)), val);
			if(val < lowest)
			{
				lowest = val;
				lowestX = x;
			}
		}
		return new PointXY(returnRounded(Util.round(lowestX, 6)), returnRounded(f.Calc(lowestX)));
	}
	public static PointXY Maximum(Function f, double LowX, double UpX)
	{
		double dx = 0.000001;
		double highestX = LowX - 1;
		double highest = -9999999;
		double val;
		for(double x = LowX; x < UpX; x += dx)
		{
			val = f.Calc(x);
			if(Double.isInfinite(val)) return new PointXY(returnRounded(Util.round(x, 6)), val);
			if(val > highest)
			{
				highest = val;
				highestX = x;
			}
		}
		return new PointXY(returnRounded(Util.round(highestX, 6)), returnRounded(f.Calc(highestX)));
	}
	public static PointXY Intersect(Function f, Function g, double LowX, double UpX)
	{
		double smallestDelta = 1000000000;
		double currentDelta;
		double step = 10 / 10000000.0;
		double x = 0, x2 = 0;
		 
	
		for(x = LowX + 0.001; x < UpX;x+=0.1)
		{
			currentDelta = Math.abs(g.Calc(x) - f.Calc(x));
			if(currentDelta < 1)
			{
				x2 = x;
				break;
			}
		}
		
		for(x = x2; x < x2 + 1 && x < UpX; x+= 0.001)
		{
			currentDelta = Math.abs(g.Calc(x) - f.Calc(x));
			if(currentDelta < 0.01)
			{
				x2 = x;
				break;
			}
		}
		
		for(x = x2; x < x2 + 1 && x < UpX; x+= step)
		{
			currentDelta = Math.abs(g.Calc(x) - f.Calc(x));
			if(smallestDelta > currentDelta)
			{
				smallestDelta = currentDelta;
				x2 = x;
			}
			
		}
		if(smallestDelta < 0.1)
			return new PointXY(Util.round(x2, 6), Util.round(f.Calc(x2), 6));
		else 
			return new PointXY(Double.NaN, Double.NaN);
	}	

	
	public static double DyDx(Function f, double x)
	{
		if(Double.isNaN(x)) return Double.NaN;
		//double dx = 0.000000001;
		double dx   = 0.000001;
		
		double dydx;
		double dy = (f.Calc(x+dx) - f.Calc(x));
		
		dydx = dy / dx;
		
		return returnRounded(Util.round(dydx, 6));
		//return Util.round(dydx, 6);
	}	
	public static double DyDx(Function f, double x, double dx)
	{
		if(Double.isNaN(x) || Double.isNaN(dx)) return Double.NaN;
		double dydx;
		double dy = (f.Calc(x+dx) - f.Calc(x));
		
		dydx = dy / dx;
		return dydx;
	
	}
	
	public static double Integral(Function f, double LowX, double UpX)
	{
		double dx = 0.00001;
		//double dx =   0.000001;
		double lowersum=0, uppersum=0;
		double val;
		for(double x = LowX; x < UpX; x += dx)
		{
			val = f.Calc(x);
			if(Double.isNaN(val)) return Double.NaN;
			if(x != LowX)
			{
				uppersum += val * dx;
			}
			if(x != (UpX - dx))
			{
				lowersum += val * dx;
			}
		}
		double average = (lowersum + uppersum) / 2;
		average = Util.round(average, 4);
		return returnRounded(average);
	}
	public static double FindLastXBeforeNaN(Function f, double Xstart)
	{
		double dx = 0.0001;
		if(Double.isNaN(f.Calc(Xstart))) return Double.NaN;
		
		double Xtmp = 0;
		for(double x = Xstart; x < Xstart + 50 ; x += 0.001)
		{
			if(Double.isNaN(f.Calc(x))) {
				Xtmp = x - 0.001;
				break;
			}
		}

		for(double x = Xtmp; x < Xtmp + 1; x += dx)
		{
			if(Double.isNaN(f.Calc(x)))
			{
				return (x - dx);
			}
		}
		
		return Double.NaN;
	}
	public static double FindFirstXAfterNaN(Function f, double Xstart)
	{
		double dx = 0.0001;
		if(!Double.isNaN(f.Calc(Xstart))) return Xstart;
		
		double Xtmp = 0;
		for(double x = Xstart; x < Xstart + 50 ; x += 0.001)
		{
			if(!Double.isNaN(f.Calc(x))) {
				Xtmp = x - 0.001;
				break;
			}
		}

		for(double x = Xtmp; x < Xtmp + 1; x += dx)
		{
			if(!Double.isNaN(f.Calc(x)))
			{
				return x;
			}
		}
		
		return Double.NaN;
	}
	
	public static double Factorial(double n)
	{
		/*
		int result = 1;
		if(n == 0) return 1;
		if(n < 0) return -1;
		
		while(n > 0)
		{
			result *= n;
			n--;
		}
		return result;
		*/
		//return Product('i', 1, (int) n, "i");
		return Gamma(n+1);
	}
	
	public static int BinomialCoefficient(int n, int k)
	{
		if(k <0 || k > n) return 0;
		
		double result =  (Factorial(n) / (Factorial(k) * Factorial(n-k) ));
		return (int) Util.round(result, 0);
	}
	
	public static double Summation(char index, int start, int stop, String body)
	{
		body = body.toLowerCase(Locale.ENGLISH);
		index = ("" + index).toLowerCase(Locale.ENGLISH).charAt(0);
		Function f = new Function(index);
		if(!f.Parse(body))
		{
			System.out.println("Error parsing '" + body + "'");
			return Double.NaN;
		}
		double sum = 0;
		for(int i=start; i <= stop; i++)
		{
			if(!Double.isNaN(f.Calc(i)))
					sum += f.Calc(i);

		}
		return sum;
	}
	/*
	 Calculates PI:
	 Summation('k', 0, 1000, "1/(16^k)(4/(8k+1)-2/(8k+4)-1/(8k+5)-1/(8k+6))") 
		
	 Calculates E:
	 Summation('k', 0, 1000, "1/fac(k)")
	
	 Binomial of Newton:
	 //(4+5)^6
	 int x = 4;
	 int y = 5;
	 int n = 6;
	 String bodystr = String.format("bin(%d, k) * %d^(%d-k) * %d^k", n, x, n, y);
	 System.out.println(Summation('k', 0, n, bodystr));
	 
	 Sinus and cosinus:
	 double x = 1;
	 int k = 500;
	 Locale.setDefault(new Locale("en", "US"));
	 String sin = String.format("((-1)^n*(%f)^(2n+1))/(fac(2n+1))", x);
	 String cos = String.format("((-1)^n)/(fac(2n))*(%f)^(2n)", x);
	
	 System.out.println("Sin: " + Summation('n', 0, k, sin));
	 System.out.println("Cos: " + Summation('n', 0, k, cos));
	*/
	public static double Product(char index, int start, int stop, String body)
	{
		body = body.toLowerCase(Locale.ENGLISH);
		index = ("" + index).toLowerCase(Locale.ENGLISH).charAt(0); 
		Function f = new Function(index);
		if(!f.Parse(body))
		{
			System.out.println("Error parsing '" + body + "'");
			return Double.NaN;
		}
		double product = 1;
		for(int i=start; i <= stop; i++)
		{
			product *= f.Calc(i);
		}
		return product;
	}
	
	// This shit does not work at all!!
	
	public static double FresnelS(double arg)
	{
		//String argstr = Util.doubleToString(arg);
		//String bodystr = String.format("(-1)^n*(((%s)^(4n+3))/(fac(2n+1)*(4n+3)))", argstr);
		//return Calculate.Summation('n', 0, 100, bodystr);
		 
		double sum = 0;
		double arg0 = arg * Math.sqrt(Math.PI / 2.0);
		for(int n=0;n<100;n++)
		{
			sum += (Math.pow(-1, (double) n) * ((Math.pow(arg0, 4*n+3))/((double) Calculate.Factorial(2*n+1) * (4*n+3))));
		}
		return sum * Math.sqrt(2.0 / Math.PI);
		

		
	}
	public static double FresnelC(double arg)
	{
		/*
		String argstr = Util.doubleToString(arg);
		String bodystr = String.format("(-1)^n*(((%s)^(4n+1))/(fac(2n)*(4n+1)))", argstr);
		return Calculate.Summation('n', 0, 100, bodystr);
		*/
		return 1;
	}

	private static final double g = 7.0;
	private static final double p[] = {
		0.99999999999980993, 676.5203681218851, -1259.1392167224028,
	     771.32342877765313, -176.61502916214059, 12.507343278686905,
	     //-0.13857109526572012, .0000099843695780195716, .00000015056327351493116
	     -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7
	};
	public static double Gamma(double z)
	{
		//return Integral(new Function('t', String.format("t^(%s)*const(e)^(-t)", Util.doubleToString(z-1))), 0, 10);
		//String zstr = Util.doubleToString(z);
		//return (1.0/z) * Calculate.Product('n', 1, 100, String.format("(1+(1/n)^%s)/(1+(%s/n))", zstr, zstr));
		if(z < 0.5)
			return Math.PI / (Math.sin(Math.PI * z) * Gamma(1-z));
		else
		{
			z -= 1;
			double x = p[0];
			for(int i=1; i<(g+2);i++)
			{
				x += p[i]/(z+(double)i);
			}
			double t = z + g + 0.5;
			return Math.sqrt(2 * Math.PI) * Math.pow(t, (z+0.5)) * Math.exp(-t) * x;
		}
	}
	public static void main(String[] args)
	{
		 //System.out.println(Summation('k', 0, 10000, "1/(16^k)(4/(8k+1)-2/(8k+4)-1/(8k+5)-1/(8k+6))"));
		int player_wins=0;
		int dealer_wins=0;
		int draw=0;
		int rounds = 100000;
		Random r = new Random();
		
		//0=black,1=red
		for(int i=0;i<rounds;i++)
		{
			int player_order[] = new int[3];
			int dealer_order[] = new int[3];
			int player_points=0,dealer_points=0;
			int cards[] = new int[52];
			
			r.setSeed(System.currentTimeMillis());
			
			int amount_black = 0, amount_red = 0;
			for(int j=0;j<52;j++)
			{
				if(amount_black < 26 && amount_red < 26)
				{
					int color = Math.abs(r.nextInt() % 2);
					if(color==0) amount_black++;
					else amount_red++;
				    cards[j] = color; 
				}
				else if(amount_black == 26)
				{
					amount_red++;
					cards[j] = 1;
				}
				else if(amount_red == 26)
				{
					amount_black++;
					cards[j] = 0;
				}
			}

			boolean d = false;
			while(player_order == dealer_order || !d)
			{
				player_order[0] = Math.abs(r.nextInt() % 2);
				player_order[1] = Math.abs(r.nextInt() % 2);
				player_order[2] = Math.abs(r.nextInt() % 2);
				
				
				dealer_order[0] = Math.abs(r.nextInt() % 2);
				dealer_order[1] = player_order[0];
				dealer_order[2] = player_order[1];
				
				
				/*
				dealer_order[0] = player_order[0];
				dealer_order[1] = player_order[1];
				dealer_order[2] = (player_order[2]==1?0:1);
				*/
				
				/*
				dealer_order[0] = player_order[2];
				dealer_order[1] = player_order[1];
				dealer_order[2] = player_order[0];
				*/

				d = true;
			}
			int last_three[] = new int[3];
			int reset_c=0;
			for(int j=0;j<52;j++)
			{
				if(reset_c < 3)
				{
					last_three[reset_c++] = cards[j];
					continue;
				}
				
				if(last_three[0] == player_order[0] && last_three[1] == player_order[1] && last_three[2] == player_order[2])
				{
					player_points++;
					reset_c=1;
					last_three[0] = cards[j];
				}
				else if(last_three[0] == dealer_order[0] && last_three[1] == dealer_order[1] && last_three[2] == dealer_order[2])
				{
					dealer_points++;
					reset_c=1;
					last_three[0] = cards[j];
				}
				else
				{
					last_three[0] = last_three[1];
					last_three[1] = last_three[2];
					last_three[2] = cards[j];
				}
			}
				
			//System.out.printf("Player points: %d, dealer points: %d\n", player_points, dealer_points);
			if(player_points > dealer_points)
				player_wins++;
			else if(dealer_points > player_points)
				dealer_wins++;
			else
				draw++;
		}
		
		//System.out.printf("Draw: %d\nPlayer wins: %d\nDealer wins: %d\n", draw, player_wins, dealer_wins);
		//System.out.printf("Draw: %f%%\nPlayer: %f%%\nDealer: %f%%\n", (double) draw / (double) rounds * 100, (double) player_wins / (double) rounds * 100, (double) dealer_wins / (double) rounds * 100);
		System.out.printf("Player: %.2f%%\nDealer: %.2f%%\n",  (double) player_wins / (double) (rounds-draw) * 100, (double) dealer_wins / (double) (rounds-draw) * 100);
	}
}