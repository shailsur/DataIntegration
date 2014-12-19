package cse636;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;

public class EditDistance {
	
	public static ArrayList<String> filesDeleted = new ArrayList<String>();
	static HashMap<String,Double> hMap = new HashMap<String,Double>();
	static HashMap<String,Integer> hMapDeletedCount = new HashMap<String,Integer>();
	public static int Sum= 0;
	public static int editDistance(String s, String t) {
        int m = s.length();
        int n = t.length();
        int[][] d = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            d[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1];
                } else {
                    d[i][j] = min((d[i - 1][j] + 1), (d[i][j - 1] + 1),
                            (d[i - 1][j - 1] + 1));
                }
            }
        }
        return (d[m][n]);
    }

    public static int min(int a, int b, int c) {
        return (Math.min(Math.min(a, b), c));
    }
    
    public static void main (String[] args) {
    	try
    	{
    	Dup d1=new Dup();
    	HashMap<Long,HashSet<String>> hm1=new HashMap<Long,HashSet<String>>();
    	HashMap<Long,String[]> hm2=new HashMap<Long,String[]>();
    	hm1=d1.fileSize();
    	Iterator<Entry<Long, HashSet<String>>> it1 = hm1.entrySet().iterator();
    	 while (it1.hasNext()) {
    		 Map.Entry<Long,HashSet<String>> pairs = (Map.Entry<Long,HashSet<String>>)it1.next();
    		 Long key=pairs.getKey();
    		 //System.out.println(key);
    		 HashSet<String> s1=pairs.getValue();
    		 String [] st= s1.toArray(new String[s1.size()]);
    		 for(int i=0;i<st.length;i++)
    		 {
    			 for(int j=0;j<st.length;j++)
    			 {
    				 if(st[i]!=null&&st[j]!=null&i!=j)
    				 {
    				 int ed;
    				 BasicFileAttributes view1;
    				 BasicFileAttributes view2;
    				 String s=st[i];
    				 String t=st[j];
    				 int index1 = s.lastIndexOf("\\");
    				 int index2 = t.lastIndexOf("\\");
    				 String fileName1 = s.substring(index1 + 1);	 
    				 String fileName2 = t.substring(index2 + 1);
    				 ed=editDistance(fileName1,fileName2);
    				 File f1=new File(s);
    				 File f2=new File(t);
    				 if(ed==0)
    				 {
    					 Path p1 = Paths.get(f1.getAbsolutePath());
    					 Path p2 = Paths.get(f2.getAbsolutePath());
    					 view1 = Files.readAttributes(p1, BasicFileAttributes.class);
    					 view2 = Files.readAttributes(p2, BasicFileAttributes.class);
    					 	if(view1.lastModifiedTime().compareTo(view2.lastModifiedTime())<=0)
    					 	{
    					 		filesDeleted.add(st[i]);
    					 		Sum += (new File(st[j])).length();
    					 		st[i]=null;
    					 		//remove s[i]
    					
    					 	}
    					 	else
    					 	{
    					 		//remove st[j]
    					 		filesDeleted.add(st[j]);
    					 		Sum += (new File(st[j])).length();
    					 		st[j]=null;
    					 	}
    				 }
    			 }
    		 }
    		 }
    		 List<String> list = new ArrayList<String>();

    		    for(String s : st) {
    		       if(s != null && s.length() > 0) {
    		          list.add(s);
    		       }
    		    }
    		    st = list.toArray(new String[list.size()]);
    		 hm2.put(key, st); 
    	}
    	 int count = 0;
    	 long sum = 0;
    	 for(String s : filesDeleted)
    	 {
    		 count++;
    		 long size = getfileSize(s);
    		 sum += size;
    	 }
    	 System.out.println("Edit distance--->>\nCount:-"+count+"\nSum:-"+sum);
    	 //print_hm2(hm2);
    	 ngrams(hm2);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

}
      public static void ngrams(HashMap<Long,String[]> hm1)
    {
    	try
    	{
    		HashMap<Long,String[]> hm2=new HashMap<Long,String[]>();
    		Iterator<Entry<Long, String[]>> it1 = hm1.entrySet().iterator();
       	 	while (it1.hasNext()) {
       		 Map.Entry<Long,String[]> pairs = (Map.Entry<Long,String[]>)it1.next();
       		 Long key=pairs.getKey();
       		 //System.out.println(key);
       		 String [] st=pairs.getValue();
       		 for(int i=0;i<st.length;i++)
       		 {
       			 for(int j=0;j<st.length;j++)
       			 {
       				 if(st[i]!=null&&st[j]!=null&i!=j)
       				 {
       				 int ed;
       				 BasicFileAttributes view1;
       				 BasicFileAttributes view2;
       				 String s=st[i];
       				 String t=st[j];
       				 int index1 = s.lastIndexOf("\\");
       				 int index2 = t.lastIndexOf("\\");
       				 String fileName1 = s.substring(index1 + 1);
       				 String fileName2 = t.substring(index2 + 1);
       				 Ngram nobj=new Ngram(3);
       				 double ng=nobj.compare(fileName1,fileName2);
       				 //System.out.println(ng);
       				 File f1=new File(s);
       				 File f2=new File(t);
       				 if(ng>0.9)
       				 {
       					 Path p1 = Paths.get(f1.getAbsolutePath());
       					 Path p2 = Paths.get(f2.getAbsolutePath());
       					 //System.out.println(p1.toString());
       					 //System.out.println(p2.toString());
       					 view1 = Files.readAttributes(p1, BasicFileAttributes.class);
       					 view2 = Files.readAttributes(p2, BasicFileAttributes.class);
       					 	if(view1.lastModifiedTime().compareTo(view2.lastModifiedTime())<=0)
       					 	{
       					 		filesDeleted.add(st[i]);
       					 		Sum += (new File(st[i])).length();
       					 		st[i]=null;
       					 		//remove s[i]
       					
       					 	}
       					 	else
       					 	{
       					 		//remove st[j]
       					 		filesDeleted.add(st[j]);
       					 		Sum += (new File(st[j])).length();
       					 		st[j]=null;
       					 	}
       				 }
       			 }
       		 }
//       			 System.out.println();
       		 }
       		 List<String> list = new ArrayList<String>();

       		    for(String s : st) {
       		       if(s != null && s.length() > 0) {
       		          list.add(s);
       		       }
       		    }
       		    st = list.toArray(new String[list.size()]);
       		 hm2.put(key, st); 
       	}
       	 	System.out.println();
       	 /*for(long key : hm2.keySet())
    	 	{
    	 		System.out.println(key);
    	 		String [] value = hm2.get(key);
    	 		for(int i = 0;i<value.length;i++)
    	 			System.out.println(value[i]);
    	 	}*/
       	 int count = 0;
       	 long sum = 0;
    	 for(String s : filesDeleted)
    	 {
    		 count++;
    		 long size = getfileSize(s);
    		 sum += size;
    	 }
    	 System.out.println("Jaccard--->>\nCount:-"+count+"\nSum:-"+sum);
       	 	removeFilewithCopyinName(hm2);
//       	 print_hm2(hm2);
       	 //ngrams(hm2);
       	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    
    public static void removeFilewithCopyinName(HashMap<Long,String[]> hm1)
    {
    	HashMap<Long,String[]> hm2=new HashMap<Long,String[]>();
		Iterator<Entry<Long, String[]>> it1 = hm1.entrySet().iterator();
   	 	while (it1.hasNext())
   	 	{
   	 		Map.Entry<Long,String[]> pairs = (Map.Entry<Long,String[]>)it1.next();
   	 		Long key=pairs.getKey();
   	 		//	System.out.println(key);
   	 		String [] st=pairs.getValue();
   	 		for(int i=0;i<st.length;i++)
   	 		{
   	 			for(int j=0;j<st.length;j++)
   	 			{
   	 				if(st[i]!=null&&st[j]!=null&i!=j)
   	 				{
   	 					BasicFileAttributes view1;
   	 					BasicFileAttributes view2;
   	 					String s=st[i];
   	 					String t=st[j];
   	 					int index1 = s.lastIndexOf("\\");
   	 					int index2 = t.lastIndexOf("\\");
   	 					String fileName1 = s.substring(index1 + 1);
   	 					String fileName2 = t.substring(index2 + 1);
   	 					if(fileName1.toLowerCase().contains("(copy)")||fileName2.toLowerCase().contains("(copy)"))
   	 					{
//   	 						System.out.println(fileName1);
//   	 						System.out.println(fileName2);
   	 						if(fileName1.toLowerCase().contains("(copy)"))
   	 						{
   	 							filesDeleted.add(st[i]);
   	 							Sum += (new File(st[i])).length();
   	 							st[i] = null;
   	 						}
   	 						else
   	 						{
   	 							filesDeleted.add(st[j]);
   	 							Sum += (new File(st[j])).length();
   	 							st[j] = null;
   	 						}
   	 					}
   	 				}
   	 			}
   	 		}
   	 		List<String> list = new ArrayList<String>();
		    for(String s : st)
		    {
		       if(s != null && s.length() > 0)
		       {   
		          list.add(s);
		       }
		    }
		    st = list.toArray(new String[list.size()]);
		    hm2.put(key, st);
   	 	}  
//   	 	for(long l : hm2.keySet())
//   	 	{
//   	 		String[] arr = hm2.get(l);
//   	 		for(String s : arr)
//   	 		{
//   	 			System.out.println(l +":"+s);
//   	 		}
//   	 	}
   	 int count = 0;
   	 long sum = 0;
	 for(String s : filesDeleted)
	 {
		// System.out.println(s);
		 count++;
		 long size = getfileSize(s);
		 sum += size;
	 }
	 System.out.println("Copy--->>\nCount:-"+count+"\nSum:-"+sum);
   	 	
   	 	try {
			removeFilesWithBus(hm2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void removeFilesWithBus(HashMap<Long,String[]> hm1) throws Exception
    {
    	HashMap<Long,String[]> hm2=new HashMap<Long,String[]>();
		Iterator<Entry<Long, String[]>> it1 = hm1.entrySet().iterator();
   	 	while (it1.hasNext())
   	 	{
   	 		Map.Entry<Long,String[]> pairs = (Map.Entry<Long,String[]>)it1.next();
   	 		Long key=pairs.getKey();
   	 		//	System.out.println(key);
   	 		String [] st=pairs.getValue();
   	 		for(int i=0;i<st.length;i++)
   	 		{
   	 			for(int j=0;j<st.length;j++)
   	 			{
   	 				if(st[i]!=null&&st[j]!=null&i!=j)
   	 				{
   	 					BasicFileAttributes view1;
   	 					BasicFileAttributes view2;
   	 					String s=st[i];
   	 					String t=st[j];
   	 					int index1 = s.lastIndexOf("\\");
   	 					int index2 = t.lastIndexOf("\\");
   	 					String fileName1 = s.substring(index1 + 1);
   	 					String fileName2 = t.substring(index2 + 1);
   	 					if(fileName1.contains("BUS_")||fileName2.contains("BUS_"))
   	 					{
   	 						int flag = 0;
   	 						String comparer1 = fileName1,comparer2 = fileName2;
   	 						if(fileName1.contains("BUS_"))
   	 						{
   	 							comparer2 = "BUS_"+fileName2;
   	 							flag = 2;
   	 						}
   	 						else
   	 						{
   	 							comparer1 = "BUS_"+fileName1;
   	 							flag = 1;
   	 						}
   	 						if(comparer1.contains(comparer2))
   	 						{
   	 							//System.out.println(fileName1+" COPY BUS "+fileName2);
   	 							if(flag == 1)
   	 							{
   	 								//System.out.println("Name deleted:"+st[i]);
   	 								filesDeleted.add(st[i]);
   	 								Sum += (new File(st[i])).length();
   	 								st[i] = null;
   	 							}
   	 							else if(flag == 2)
   	 							{
   	 								//System.out.println("Name deleted:"+st[j]);
   	 								filesDeleted.add(st[j]);
   	 								Sum += (new File(st[j])).length();
   	 								st[j] = null;
   	 							}
   	 							else
   	 								throw new Exception();
   	 						}
   	 					}
   	 				}
   	 			}
   	 		}
   	 		List<String> list = new ArrayList<String>();
		    for(String s : st)
		    {
		       if(s != null && s.length() > 0)
		       {   
		          list.add(s);
		       }
		    }
		    st = list.toArray(new String[list.size()]);
		    hm2.put(key, st);
   	 	}  
//   	 	for(long l : hm2.keySet())
//   	 	{
//   	 		String[] arr = hm2.get(l);
//   	 		for(String s : arr)
//   	 		{
//   	 			System.out.println(l +":"+s);
//   	 		}
//   	 	}
   	 int count = 0;
	 long sum = 0;
	 for(String s : filesDeleted)
	 {
		 count++;
		 long size = getfileSize(s);
		 sum += size;
	 }
	 System.out.println("Bus--->>\nCount:-"+count+"\nSum:-"+sum);
   	 	long newSum = 0;
   	 	//HashMap<String,Long> hMap = new HashMap<String,Long>();
   	 		for(String i : filesDeleted)
   	 		{
   	 			File f1 = new File(i);
   	 			double size = (double) f1.length()/1048576;
   	 			String ext = com.google.common.io.Files.getFileExtension(f1.getAbsolutePath().toUpperCase());
   	 			//System.out.println(f1.getAbsolutePath().toString());
   	 			if(ext == "")
   	 			{
	 				ext = "emptykey";
   	 			}
   	 			if(hMap.containsKey(ext) && hMapDeletedCount.containsKey(ext))
   	 			{
   	 				double p = (double) hMap.get(ext);
   	 				int val = (int) hMapDeletedCount.get(ext);
   	 				hMap.put(ext,p+size);
   	 				hMapDeletedCount.put(ext, ++val);
   	 			}
   	 			else
   	 			{
   	 				hMap.put(ext, size);
   	 				hMapDeletedCount.put(ext, 1);
   	 			}
   	 			newSum += size;
   	 		}
   	 		//System.out.println(newSum);
   	 		//fileCopyFunction();
   	 		
   	 		long CheckSum = 0;
   	 		//fillMap();
   	 		//System.out.println(ParserDirectory.hMapTotal.size()-hMap.size());
//   	 	printDeletedMap();
   	 		//printFileCountMaps();
   	 		ParserDirectory.printTotalMap();
   	 		//System.out.println(CheckSum);
    }
    
    private static void fileCopyFunction() {
		// TODO Auto-generated method stub
		MovingFiles mover = new MovingFiles("C:/MovingDir");
		for(String p : filesDeleted)
		{
			//Path p1 = Paths.get(p);
			mover.moveFile(p, 0);
		}
	}
    
    public static long getfileSize(String path)
    {
    	String newPath = path.replace("\\", "/"); //takes care of // \ discrepancy.
    	File f = new File(newPath);
    	return f.length();
    }

	public static void print_hm(HashMap<Long,HashSet<String>> hm1)
    {
		Iterator<Entry<Long, HashSet<String>>> it1 = hm1.entrySet().iterator();
	    while (it1.hasNext())
	    {
	        Map.Entry<Long,HashSet<String>> pairs = (Map.Entry<Long,HashSet<String>>)it1.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue().toString());
	        it1.remove(); // avoids a ConcurrentModificationException
	    }
    }
    public static void print_hm2(HashMap<Long,String[]> hm1) throws IOException
    {
		Iterator<Entry<Long, String[]>> it1 = hm1.entrySet().iterator();
	    while (it1.hasNext()) {
	        Map.Entry<Long,String[]> pairs = (Map.Entry<Long,String[]>)it1.next();
	        //System.out.println(pairs.getKey() + " = " +Arrays.toString(pairs.getValue()) );
	        if(pairs.getValue().length>1)
	        {
	        	String s1[] = pairs.getValue();
	        	for(int i = 0;i<s1.length;i++)
	        	{
	        		for(int j = 0;j<s1.length;j++)
	        		{
	        			Path p1 = Paths.get(s1[i]);
	        			Path p2 = Paths.get(s1[j]);
	        			String str1 = FilenameUtils.getExtension(p1.toString());
	        			String str2 = FilenameUtils.getExtension(p2.toString());
	        			int flag = 0;
	        			if(str1.equals("txt")||str1.equals("TXT")||str2.equals("txt")||str2.equals("TXT")||str2.equals("Po")||str1.equals("Po"))
	        			{
	        				flag = 1;
	        			}
	        			BasicFileAttributes view1 = Files.readAttributes(p1, BasicFileAttributes.class);
	        			BasicFileAttributes view2 = Files.readAttributes(p2, BasicFileAttributes.class);
	        			if(view1.lastModifiedTime().equals(view2.lastModifiedTime()) && i != j && flag == 0)
	        			{
	        				System.out.println(s1[i]+" AND "+s1[j]);
	        			}
	        		}
	        	}
	        }
	        
	        it1.remove(); // avoids a ConcurrentModificationException
	      
	    }
    }
    static HashMap<String,Double> ratioMap = new HashMap<String,Double>();
    
    public static void fillMap()
	{
		for(String i : ParserDirectory.hMapTotal.keySet())
		{
			String key = null;
			if(i=="")
				key ="emptykey";
			else
				key = i;
			double totalSize=0,removedSize=0,ratio=0;
			if(ParserDirectory.hMapTotal.containsKey(key))
				totalSize = ParserDirectory.hMapTotal.get(key);
			if(hMap.containsKey(key))
				removedSize = hMap.get(key);
			if(ParserDirectory.hMapTotal.containsKey(key) && hMap.containsKey(key))
			{
				ratio = removedSize/totalSize;
//				if(ratio>0.9)
//					System.out.println(key+"="+ratio);
				ratioMap.put(key, ratio);
			}
		}
	}
    
    public static void printDeletedMap()
    {
    	double TotalSum = 0;
    	for(String i : hMap.keySet())
    	{
    		System.out.println(i+"="+hMap.get(i));
    		TotalSum += hMap.get(i);
    	}
    	System.out.println("Total sum of files deleted.: "+TotalSum+" MB");
    }
    
    public static void printFileCountMaps()
    {
    	for(String i : hMapDeletedCount.keySet())
    	{
    		System.out.println(i+"="+hMapDeletedCount.get(i)+"="+ParserDirectory.hMapofFileCountTotal.get(i));
    	}
    }
    public static void printRatioMap()
    {
    	for(String i : ratioMap.keySet())
    	{
    		System.out.println(i+"="+ratioMap.get(i));
    	}
    }
}
