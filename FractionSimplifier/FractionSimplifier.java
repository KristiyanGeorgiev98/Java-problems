
public class FractionSimplifier {
	public static String simplify(String fraction)
	{
		int k=fraction.indexOf('/');
		String fst=fraction.substring(0, k);
		String snd=fraction.substring(k+1, fraction.length());
		int one=Integer.parseInt(fst);
		int two=Integer.parseInt(snd);
		int gcd=1;
		for(int i = 1; i <= one && i <= two; i++)
        {
            if(one%i==0 && two%i==0)
                gcd = i;
        }
		one=one/gcd;
		two=two/gcd;
		if(two==1)
		{
			fst=Integer.toString(one);
			return fst;
		}
		else
		{
			if(two==0)
			{
				snd=Integer.toString(two);
				return snd;
			}
			fst=Integer.toString(one);
			snd=Integer.toString(two);
			return one+"/"+two;
		}
		}
				
	}

