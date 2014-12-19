package cse636;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogMaker {
	
	public static void appender(String text,String dir)
	{
		File file = new File(dir+"/myLog.csv");
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
			out.println(text);
		}catch (IOException e) {
			//	exception handling left as an exercise for the reader
		}
	}
}
