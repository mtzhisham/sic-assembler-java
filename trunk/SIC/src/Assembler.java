public class Assembler {
	public static String [][] sourceFile;

	private static String source = null;
	//public static AssemblyListing[] assemblyListing = new AssemblyListing[interFile.length];
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub				
		source = "C:\\Documents and Settings\\Siobhan\\workspace\\SIC\\EX-2-1.txt";
		sourceFile = ReaderFile.read(source);
		PassOne();
		//source = "中間檔儲存地點";//中間檔儲存地點。
		//sourceFile = FileReaderPassOne.read(source);
		//PassTwo();
	}
	private static void PassOne(){
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
