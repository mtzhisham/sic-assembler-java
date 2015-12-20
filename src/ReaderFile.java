import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


public class ReaderFile {
	public static String [][] read(String source)	{
		LinkedList<String []> sourceFile = new LinkedList<String []>();
		FileReader fileReader = readSourceFile(source);
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;
		String[] temp;
		try		{
			while((line = buffer.readLine()) != null){
				line = line.trim().replaceAll("\\s+", " ");
				if(line.equals("")) continue;
				if(line.charAt(0) == '.') continue;
				temp = line.split(" ");
				sourceFile.add(temp);
			}
		}
		catch (IOException e) 		{
			System.out.println("read file error");
		}
		System.out.println("Reader File "+ Arrays.deepToString(sourceFile.toArray(new String[sourceFile.size()][]) ));
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
