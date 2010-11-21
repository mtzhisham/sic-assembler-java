import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
	public static String [][] sourceFile;

	private static String source = null;
	//public static AssemblyListing[] assemblyListing = new AssemblyListing[interFile.length];
	private static String purpose = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\Object.txt";	
	public static BufferedWriter bufferedwriter;
	public static void write(){
		FileWriter writefile = null;
		try {
			writefile = new FileWriter(purpose);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferedwriter = new BufferedWriter(writefile);
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub				
		source = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\EX-2-1.txt";
		sourceFile = ReaderFile.read(source);
		PassOne();
		bufferedwriter.close();
		//source = "中間檔儲存地點";//中間檔儲存地點。
		//sourceFile = FileReaderPassOne.read(source);
		//PassTwo();
	}
	private static void PassOne() throws IOException{
		write();
		PassOne.readFirstLine();
		for(int i = 1; i < sourceFile.length-1; i++)
			PassOne.readline(i);
		PassOne.readLatline();		
	}	
	private void PassTwo(){
		PassTwo.readFirstLine();
		/*for(int i = 1; i < interFile.length-1; i++){
			
		}*/		
	}
}
