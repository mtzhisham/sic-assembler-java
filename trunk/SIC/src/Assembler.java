import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
	public static String [][] sourceFile;

	private static String source = null;
	//public static AssemblyListing[] assemblyListing = new AssemblyListing[interFile.length];
	
	private static String purpose;	
	public static BufferedWriter bufferedwriter;
	public static void CreateFile(String purpose) throws IOException{
		FileWriter writefile = null;
		writefile = new FileWriter(purpose);
		bufferedwriter = new BufferedWriter(writefile);
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub				
		source = "D:\\Android\\SIC\\EX-2-1.txt";
		sourceFile = ReaderFile.read(source);
		PassOne();		
		source = "D:\\Android\\SIC\\InterFile.txt";//中間檔儲存地點。
		sourceFile = ReaderFile.read(source);
		//PassTwo();
	}
	private static void PassOne() throws IOException{
		purpose = "D:\\Android\\SIC\\InterFile.txt";	
		CreateFile(purpose);
		PassOne.readFirstLine();
		for(int i = 1; i < sourceFile.length-1; i++)
			PassOne.readline(i);
		PassOne.readLatline();		
	}	
	private void PassTwo() throws IOException{
		purpose = "D:\\Android\\SIC\\AssemblyListing.txt";	
		CreateFile(purpose);
		PassTwo.readFirstLine();
		/*for(int i = 1; i < interFile.length-1; i++){
			
		}*/		
	}

}
