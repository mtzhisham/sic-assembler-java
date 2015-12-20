import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Assembler {
	public static String [][] sourceFile;
	//source file location SYMBTAB in the end of PassOne class
	public static String Common = "C:\\Users\\Moataz\\workspace\\SicAssembler\\"; 
	public static String EX_2_1 = "SRCFILE.txt";
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
		
		source = Common+EX_2_1;
		PassOne.printSYMTAB();
		sourceFile = ReaderFile.read(source);
		PassOne(purpose);
		
		System.out.println(Arrays.deepToString(sourceFile));
		
	}
	private static void PassOne(String purpose) throws IOException{
		purpose = Common+InterFile;	
		CreateFile(purpose);
		PassOne.readFirstLine();
		for(int i = 1; i < sourceFile.length-1; i++)
			PassOne.readline(i);
		PassOne.readLatline();		
		PassOne.closeWriter();
	}	
	
}
