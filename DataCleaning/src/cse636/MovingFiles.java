package cse636;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class MovingFiles {

	String movingDir;
	public MovingFiles(String dirName)
	{
		movingDir = dirName;
	}
	
	public boolean moveFile(String fileName, int count)
	{
		fileName = fileName.replace("\\","/");
		File f = new File(fileName);
		if(!f.exists())
		{
			try {
				throw new Exception("File doesnt exist!"+fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		
		File moving = new File(movingDir);
		if(moving.exists() && !moving.isDirectory())
		{
			try {
				throw new Exception("Folder doesnt exist!"+fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		String extension = FilenameUtils.getExtension(fileName);
		String name = FilenameUtils.getBaseName(fileName);
		int index1 = fileName.lastIndexOf("/");
		String fName = fileName.substring(index1+1);
		for(int i=0;i<=count;i++){}
		fName = fName+"_"+count;
		if(new File(movingDir+"/"+fName).exists())
		{
			boolean success = moveFile(fileName,++count);
			return true;
		}
		else
		{
			try {
				File newFile = new File(movingDir+"/"+fName);
				FileUtils.copyFile(f, newFile, true);
				//boolean success = f.renameTo(newFile);
				String Size = String.valueOf(f.length());
				Path p1 = Paths.get(f.getAbsolutePath());
				BasicFileAttributes view1 = Files.readAttributes(p1, BasicFileAttributes.class);
				String lastModTime = view1.lastAccessTime().toString();
				String logString = newFile.getAbsolutePath()+","+f.getAbsolutePath()+","+f.getParent()+","+Size+","+lastModTime;
				LogMaker.appender(logString,movingDir);
				if(!newFile.exists())
				{
					throw new Exception("File name:"+newFile.getAbsolutePath()+" not created");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}	
		return false;
	}
	
	/*public static void main(String args[])
	{
		MovingFiles m = new MovingFiles("C:/MovingDir");
		m.moveFile("C:/testDir/Shail.txt.old",0);
		m.moveFile("C:/testDir1/Shail.txt.old", 0);
		m.moveFile("C:/testDir2/Shail.txt.old", 0);
		
	}*/
}
