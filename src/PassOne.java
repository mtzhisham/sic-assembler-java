import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PassOne  {
	public static int start = 0;
	public 	static int locCtr = 0;
	public 	static ListFile listFile;
	private static int opCode;
	private static int programLength = 0;
	public static Map<String, Integer> symbolTAB = new HashMap<String, Integer>();
	private static BufferedWriter bufferedWriter;

	public static void readFirstLine()	throws IOException{		
		String [] line = Assembler.sourceFile[0];				
		System.out.println(line[1]);
		listFile = new ListFile();
		if(line[1].equals("START")){							
			locCtr = start = Integer.parseInt(line[2], 16);		
			listFile.setInterLine(locCtr, line[0], line[1], line[2]);
			Assembler.bufferedwriter.write(listFile.outPut(0));
			Assembler.bufferedwriter.newLine();			
		}
	}		
	public static void readline(int index) throws IOException{
		String [] line = Assembler.sourceFile[index];
		String Mnemonic;
		listFile = new ListFile();
		if(line.length == 3){								
			String label = line[0];
			if(!symbolTAB.containsKey(label)){					
				putLabelInSymbolTAB(label);
				listFile.label = label;
			}
			else	listFile.ERROR = "line: " + index + "duplicate label: " + label;	
		}
		switch(line.length){
			case 1:			
				Mnemonic = line[line.length - 1];
				break;
			
			default:		
				Mnemonic = line[line.length - 2];	
				listFile.oprand = line[line.length-1];
				break;
		}
		opCode = Operation.getOperator(Mnemonic);
		listFile.setInterLine(locCtr, opCode, Mnemonic);
		switch(opCode){
			case Operation.NoFound:						
				listFile.ERROR = "line: " + index + " can't find operator " + Mnemonic;
				break;
			case Operation.WORD:						
				locCtr += 3;							
				break;
			case Operation.RESW:						
				locCtr += 3*Integer.parseInt(line[line.length - 1]);	
				break;
			case Operation.RESB:						
				locCtr += Integer.parseInt(line[line.length - 1]);	
				break;
			case Operation.BYTE:
				
				String [] sta = line[line.length - 1].split("'");		
				if(sta[0].equals("C"))
					locCtr += sta[1].length();					 	
					
				break;
			default:									
				locCtr += 3;
				break;
		}			
		Assembler.bufferedwriter.write(listFile.outPut(index));
		Assembler.bufferedwriter.newLine();	
	}	
	public static void readLatline() throws IOException{		
		int lastLine = Assembler.sourceFile.length-1;		
		String [] line = Assembler.sourceFile[lastLine];	
		listFile = new ListFile();
		if(line.length == 2 && line[0].equals("END"))	{
			programLength = locCtr - start;
			listFile.setInterLine(line[0], line[1]);
		}else if(line[0].equals("END")){
			programLength = locCtr;		
			listFile.Mnemonic = line[0];
		}
		Assembler.bufferedwriter.write(listFile.outPut(lastLine));
		Assembler.bufferedwriter.newLine();
		Assembler.bufferedwriter.write(Integer.valueOf(programLength).toString());
		Assembler.bufferedwriter.close();
	}
	
	
	public static void putLabelInSymbolTAB(String label) throws IOException{
		symbolTAB.put(label, locCtr);
		bufferedWriter.write(locCtr +"   "+ label);
		bufferedWriter.newLine();
}
	public static void closeWriter() throws IOException{
		bufferedWriter.close();
	}
	
	
	public static void printSYMTAB() throws IOException {
		//SYMBTAB location
		File f = new File("C:\\Users\\Moataz\\workspace\\SicAssembler\\SYMBTAB.txt");
		f.createNewFile();
		FileWriter writefile = new FileWriter(f);
		bufferedWriter = new BufferedWriter(writefile);
		System.out.println(symbolTAB.toString());
	}
	
}
