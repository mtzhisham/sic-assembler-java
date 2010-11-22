import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
	public static String [][] sourceFile;
	private static String source = null;	
	private static String purpose = null;	
	public static BufferedWriter bufferedwriter;
	public static BufferedWriter bufferedObjectCode;
	public static void CreateFile(String purpose) throws IOException{
		FileWriter writefile = null;
		writefile = new FileWriter(purpose);
		bufferedwriter = new BufferedWriter(writefile);
	}
	public static void CreateObjectCodeFile(String purpose) throws IOException{
		FileWriter writefile = null;
		writefile = new FileWriter(purpose);
		bufferedObjectCode = new BufferedWriter(writefile);
	}	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub	
		source = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\EX-2-1.txt";
		//source = "D:\\Android\\SIC\\EX-2-1.txt";
		sourceFile = ReaderFile.read(source);
		PassOne(purpose);		
		source = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\InterFile.txt";
		//source = "D:\\Android\\SIC\\InterFile.txt";//中間檔儲存地點。
		sourceFile = ReaderFile.read(source);
		PassTwo(purpose);
	}
	private static void PassOne(String purpose) throws IOException{
		purpose = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\InterFile.txt";
		//purpose = "D:\\Android\\SIC\\InterFile.txt";	
		CreateFile(purpose);
		PassOne.readFirstLine();
		for(int i = 1; i < sourceFile.length-1; i++)
			PassOne.readline(i);
		PassOne.readLatline();		
	}	
	private static void PassTwo(String purpose) throws Exception{
		//purpose = "D:\\Android\\SIC\\AssemblyListing.txt";	
		purpose = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\AssemblyListing.txt";
		CreateFile(purpose);
		purpose = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\ObjectCode.txt";
		//purpose = "D:\\Android\\SIC\\ObjectCode.txt";	
		CreateObjectCodeFile(purpose);
		PassTwo.readFirstLine();
		for(int i = 1; i < sourceFile.length-2; i++)//扣掉最後一行(程式長度)、倒數第二行END
			PassTwo.readLine(i);
		if(PassTwo.TextRecord.length() != 0) PassTwo.writeBuffer();
		PassTwo.readLastLine();
		Assembler.bufferedwriter.close();
		Assembler.bufferedObjectCode.close();
	}

}
