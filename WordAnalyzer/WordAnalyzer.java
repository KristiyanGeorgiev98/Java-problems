import java.util.Arrays;

public class WordAnalyzer {

	public static void main(String[] args) {
		System.out.println(getSharedLetters("house", "villa")	);

	}
	public static String getSharedLetters(String word1, String word2)
	{
		String one,two,w1,w2;
		w1=word1.toLowerCase();
		w2=word2.toLowerCase();
		char tempArray[] = w1.toCharArray(); 
        Arrays.sort(tempArray);
        one=new String(tempArray);
       // System.out.println(one);
        char tempArray2[] = w2.toCharArray(); 
        Arrays.sort(tempArray2);
        two=new String(tempArray2);
       // System.out.println(two);
        int i=0,j=0;
        String finals="";
        while((i < one.length()) && (j < two.length()))
        {
        	
        	if(one.charAt(i)==(two.charAt(j)))
        	{
        		finals+=one.charAt(i);
        		i++;
        		j++;
        	}
        	
        	else if((one.charAt(i)>(two.charAt(j))))
        	{
        		j++;
        		
        	
        	}
        	
        	else if(((one.charAt(i))<(two.charAt(j))))
        	{
        		i++;
        		
        	}
        	
        }
        
        return finals;
	}
}

