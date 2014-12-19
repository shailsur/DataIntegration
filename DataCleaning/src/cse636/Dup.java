package cse636;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;


public class Dup {

	public HashMap<Long, HashSet<String>> fileSize() {
		HashMap<Long,HashSet<String>> hm1 = new HashMap<Long,HashSet<String>>();
		try {
			
			HashMap<Long,HashSet<String>> hm = new HashMap<Long,HashSet<String>>();
			//BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\sai\\Desktop\\Sorted_files.txt"));
			String line1=null;
			ParserDirectory p=new ParserDirectory();
			//String line2=null;
			//line1=br.readLine();
			//System.out.println(line1);
			//line2=br.readLine();
			//System.out.println(line2);
			PriorityQueue<Entry<String, Long>> pq=p.parse();
			line1=pq.poll().toString();
//			System.out.println(line1);
			while(!pq.isEmpty())
			{
				HashSet<String> hs = new HashSet<String>();
				int p1=line1.indexOf("=");
				//int p2=line2.indexOf("=");
				String sub1=line1.substring(p1+1);
				//System.out.println(sub1);
				if(hm.containsKey(Long.parseLong(sub1)))
				{
					HashSet<String> hs1 = hm.get(Long.parseLong(sub1));
					hs1.add(line1.substring(0, p1));
					hm.put(Long.parseLong(sub1), hs1);
				}
				else
				{
					hs.add(line1.substring(0,p1));
					hm.put(Long.parseLong(sub1),hs);
				}
				line1 = pq.poll().toString();
				//System.out.println(p1);
				//String sub2=line2.substring(p2);
				//System.out.println(p2);
			}
			Set<Long> keys = hm.keySet();
			
			for(Long key : keys)
			{
				HashSet<String> hs = hm.get(key);
				if(hs.size()>1)
				{
					hm1.put(key, hs);
				}
				else
				{
					//System.out.println(hm.get(key).toString());
				}
				//System.out.println("");
			}
			hm.values().size();
//			Iterator<Long> itr1 = hm1.keySet().iterator();
//			while(itr1.hasNext())
//			{
//				//System.out.println(hm1.get(itr1.next()).toString());
//				//itr1.remove();
//			}
			//br.close();
			return hm1;
			//System.out.println(count);
		}
		catch (Exception ex) {    
			ex.printStackTrace();
	}
		return hm1;
	}
}
