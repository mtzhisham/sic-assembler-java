public class Assembler {
	public static String [][] sourceFile;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String source = "D:\\Android\\SIC\\EX-2-1.txt";
		new Assembler(source);
	}
	public Assembler (String source){
		sourceFile = FileReaderPassOne.read(source);
		PastOne();
	}
	private void PastOne(){
		PassOne.readFirstLine();
		for(int i = 1; i < sourceFile.length-1; i++)
			PassOne.readline(i);
		PassOne.readLatline();		
	}	
}
