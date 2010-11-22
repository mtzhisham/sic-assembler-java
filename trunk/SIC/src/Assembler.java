import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
	public static String [][] sourceFile;
	//public static String Common = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\";
	public static String Common = "D:\\Android\\SIC\\";
	public static String EX_2_1 = "EX-2-1.txt";
	public static String InterFile = "InterFile.txt";
	public static String AssemblyListing = "AssemblyListing.txt";
	public static String ObjectCode = "ObjectCode.txt";
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
		source = Common+EX_2_1;
		sourceFile = ReaderFile.read(source);
		PassOne(purpose);		
		source = Common+InterFile;//中間檔儲存地點。
		sourceFile = ReaderFile.read(source);
		PassTwo(purpose);
	}
	private static void PassOne(String purpose) throws IOException{
		purpose = Common+InterFile;	
		CreateFile(purpose);
		PassOne.readFirstLine();
		for(int i = 1; i < sourceFile.length-1; i++)
			PassOne.readline(i);
		PassOne.readLatline();		
	}	
	private static void PassTwo(String purpose) throws Exception{
		purpose = Common+AssemblyListing;	
		CreateFile(purpose);
		purpose = Common+ObjectCode;	
		CreateObjectCodeFile(purpose);
		PassTwo.readFirstLine();
		for(int i = 1; i < sourceFile.length-2; i++)//扣掉最後一行(程式長度)、倒數第二行END
			PassTwo.readLine(i);
		if(PassTwo.TextRecord.length() != 0) PassTwo.writeBuffer(); //最後一行TextRecord
		PassTwo.readLastLine();
		Assembler.bufferedwriter.close();
		Assembler.bufferedObjectCode.close();
	}
}
