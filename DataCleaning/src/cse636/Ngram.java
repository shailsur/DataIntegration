package cse636;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Ngram {
	
	public int gram = 2;
	/*
	 * Sets values for gram and lengthval
	 * If you increment grams by 1, be sure to decrease lengthval by 1
	 * or there will be a StringOutOfBoundsException
	 */
	public Ngram()
	{
		this.gram = 2;
	}
	public Ngram(int gram)
	{
		this.gram = gram;
	}
	
	public double compare(String s1,String s2)
	{	
		int mod1 = s1.length() % gram;
		int mod2 = s2.length() % gram;
		
		//Fill up extra spaces with "_" to align the strings s1 and s2 with the length of gram.

		int count = 0;
		while(count < mod1)
		{
			s1 = s1 + "_";
			count++;
		}
		
		count = 0;
		while(count < mod2)
		{
			s2 = s2 + "_";
			count++;
		}
		
		ArrayList<String> ar = new ArrayList<String>();
		count = 0;
		for(int i=0;i<s1.length();i++)
		{
			String newstr = "";
			while(count<gram)
			{
				if(i+count>s1.length()-1)
					break;
				char str1 = (char) s1.charAt(i+count);
				newstr = (newstr + str1);
				newstr = newstr.toUpperCase();
				count++;
			}
			if(i<s1.length()-(gram-1))
			ar.add(newstr);
			count = 0;
			
		}
		//System.out.println(ar.toString());
		ArrayList<String> ar1 = new ArrayList<String>();
		for(int i=0;i<s2.length();i++)
		{
			String newstr = "";
			while(count<gram)
			{
				if(i+count>s2.length()-1)
					break;
				char str1 = (char) s2.charAt(i+count);
				newstr = newstr + str1;
				newstr = newstr.toUpperCase();
				count++;
			}
			if(i<s2.length()-(gram-1))
			ar1.add(newstr);
			count = 0;
		}
		//System.out.println(ar1.toString());
//		int counter = 0;
//		Iterator<String> itr = ar.iterator();
//		while(itr.hasNext())
//		{
//			Iterator<String> itr1 = ar1.iterator();
//			String str1 = (itr.next()).toUpperCase();
//			//System.out.println("str1 : "+str1);
//			while(itr1.hasNext())
//			{
//				String str2 = itr1.next();
//				//System.out.println("str2 : "+str2);
//				if(str1.equals(str2.toUpperCase()))
//				{
//					
//					counter++;
//					break;
//				}
//			}
//		}
		int sizeh1 = ar.size();
		//Retains all elements in h3 that are contained in h2 ie intersection
		ar.retainAll(ar1);
		//h1 now contains the intersection of h1 and h2
		if(ar.size()>0)
		//System.out.println("Intersection "+ h1);
		
			
		ar1.removeAll(ar);
		//h2 now contains unique elements
		//System.out.println("Unique in h2 "+ h2);
		
		//Union 
		int union = sizeh1 + ar1.size();
		int intersection = ar.size();
		
		double ratio = (double) intersection/union;
		//System.out.println(counter);
		//System.out.println(Union);
		return ratio;
		/*
		int counter=0;
		int newcount=0;
		for(int i=0;i<ar.size();i++)
		{
			String c1 = (ar.get(i)).toUpperCase();
			System.out.println("c1"+c1);
			for(int j=0;j<ar1.size();j++)
			{
				String c2 = (ar1.get(j)).toUpperCase();
				System.out.println("c2:"+c2);
				newcount++;
				if(c1.equals(c2))
				{
					counter++;
				}
			}
		}
		System.out.println(counter);
		System.out.println(newcount);
		int total = (ar.size()+ar1.size())/2;
		System.out.println(total);
		*/
		//double d1 = (double) counter/total;
		//return d1;
		//return 0;
	}
	
	public double newCompare(String similar1,String similar2)
	{
//		HashSet<String> h1 = new HashSet<String>();
//		HashSet<String> h2 = new HashSet<String>();
		ArrayList<String> h1 = new ArrayList<String>();
		ArrayList<String> h2 = new ArrayList<String>();
		
		for(String s: similar1.split("\\s+")){
		h1.add(s);		
		}
		//System.out.println("h1 "+ h1.toString());
		for(String s: similar2.split("\\s+")){
		h2.add(s);		
		}
		//System.out.println("h2 "+ h2);
		
		int sizeh1 = h1.size();
		//Retains all elements in h3 that are contained in h2 ie intersection
		h1.retainAll(h2);
		//h1 now contains the intersection of h1 and h2
		if(h1.size()>0)
		//System.out.println("Intersection "+ h1);
		
			
		h2.removeAll(h1);
		//h2 now contains unique elements
		//System.out.println("Unique in h2 "+ h2);
		
		//Union 
		int union = sizeh1 + h2.size();
		int intersection = h1.size();
		
		double ratio = (double) intersection/union;
		if(ratio > 0.5)
		{
			System.out.println(ratio);
			System.out.println(h1+"&"+h2);
		}
		return (double)intersection/union;
	}

}
