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
			while((line = buffer.readLine()) != null){//Ū���@��
				line = line.trim().replaceAll("\\s+", " ");//�N�U�تťզr��(\t\n\x0B\f\r)���m���ť�(" ")
				if(line.equals("")) continue;//���L�ťզ�
				if(line.charAt(0) == '.') continue;//���L���Ѧ� 
				temp = line.split(" ");//�r����Φ��r��}�C
				sourceFile.add(temp);//�[�J��LinkedList<String []> 
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
