import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class FileReaderPassOne {
	public static String [][] read(String source)	{
		LinkedList<String []> sourceFile = new LinkedList<String []>();
		FileReader fileReader = readSourceFile(source);
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;
		String[] temp;
		try		{
			while((line = buffer.readLine()) != null){//讀取一行
				line = line.trim().replaceAll("\\s+", " ");//將各種空白字元(\t\n\x0B\f\r)換置成空白(" ")
				if(line.equals("")) continue;//略過空白行
				if(line.charAt(0) == '.') continue;//略過註解行 
				temp = line.split(" ");//字串切割成字串陣列
				sourceFile.add(temp);//加入到LinkedList<String []> 
			}
		}
		catch (IOException e) 		{
			System.out.println("read file error");
		}
		return sourceFile.toArray(new String[sourceFile.size()][]);
	}
	private static FileReader readSourceFile(String source)	{
		try {
			return new FileReader(source);
		}
		catch (FileNotFoundException e){
			System.out.println("file "+ source +" not found");
			return null;
		}
	}
}
