

public class FunnelChecker {
	
public static boolean isFunnel(String str1, String str2)
{
    if(str1.length()<=str2.length()) {
    	return false;
    }
    if(str2.length()==0 && str1.length()==1)
    {
    	return true;
    }
    if(str2.length()==0)
    {
    	return false;
    }
    if((str2.length()+1)==str1.length())
    {	
    	int index=0,error=0;
    	char[] fst=str1.toCharArray();
    	char[] snd=str2.toCharArray();
    	for(int i=0;i<str1.length();i++)
    	{
    		if(fst[i]==snd[index])
    		{
    			++index;
    			continue;
    		}
    		else error++;
    	}
    	if(error==1)
    	{
    		return true;
    	}
    	else return false;
    }
    else return false;
}
}
