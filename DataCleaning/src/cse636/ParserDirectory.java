package cse636;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.apache.commons.io.FilenameUtils;

import com.google.common.io.Files;

public class ParserDirectory
{
	static Map<String,Long> map = new HashMap<String, Long>();
	public static final HashMap<String,Double> hMapTotal = new HashMap<String,Double>();
	public static HashMap<String,Integer> hMapofFileCountTotal = new HashMap<String,Integer>();
	public PriorityQueue<Entry<String, Long>> parse()
	{	
		File directory = new File("F:/DataIntegration"); // current directory
		//File directory = new File("C:/Data Integration Data"); // current directory
		directoryContents(directory);
        PriorityQueue<Entry<String, Long>> pq = new PriorityQueue<Map.Entry<String,Long>>
        (
       		map.size(), new Comparator<Entry<String, Long>>()
       		{
       			@Override
       			public int compare(Entry<String, Long> arg0, Entry<String, Long> arg1)
       			{
       				return arg0.getValue().compareTo(arg1.getValue());
       			}
       		}
        );
        pq.addAll(map.entrySet());
        return pq;
	}
	public static void directoryContents(File dir)
	{	
		File[] files = dir.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				if(file.getName().toUpperCase().contains("METADATA"))
					continue;
			    directoryContents(file);
			}
			else
			{
				if(file.getName().toUpperCase().contains("METADATA"))
					continue;
				String p=file.getAbsolutePath();
				String ext = Files.getFileExtension(p).toUpperCase();
				if(ext == "")
					ext = "emptykey";
				double size = (double) file.length()/1048576;
	            //int s=(int) file.length();
				long s = file.length();
	            map.put(p,s);
	            if(hMapTotal.containsKey(ext))
   	 			{
   	 				double val = (double) hMapTotal.get(ext);
   	 				int value = hMapofFileCountTotal.get(ext);
   	 				hMapTotal.put(ext,val+size);
   	 				hMapofFileCountTotal.put(ext, ++value);
   	 			}
   	 			else
   	 			{
   	 				hMapofFileCountTotal.put(ext, 1);
   	 				hMapTotal.put(ext, size);
   	 			}
			}	
		}
	}
	public static void printTotalMap()
    {
    	double TotalSum = 0;
		for(String i : hMapTotal.keySet())
    	{
    		System.out.println(i+"="+hMapTotal.get(i));
    		TotalSum += hMapTotal.get(i);
    	}
		System.out.println("Total Sum of files:: "+TotalSum+" MB");
    }
}
