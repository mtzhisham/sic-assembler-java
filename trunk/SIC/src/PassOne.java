import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PassOne {
	public static int start = 0;
	public 	static int locCtr = 0;
	private static String programName;
	private static String Mnemonic;
	public static InterFile[] interFile = new InterFile[Assembler.sourceFile.length];
	private static int opCode;
	private static int programLength = 0;
	private static Map<String, Integer> symbolTAB = new HashMap<String, Integer>();

	public static void readFirstLine()	throws IOException{
		
		String [] line = Assembler.sourceFile[0];				//讀進第一行
		if(line[1].equals("START")){//判斷是否為START			
			//將第一個#[OPERAND]定義為開始地址
			locCtr = start = Integer.parseInt(line[2], 16);	//將最後一個數值16進位轉成10進位		
			programName = line[0];		
			interFile[0] = new InterFile(start, programName, line[1], line[2]);	
			Assembler.bufferedwriter.write(interFile[0].outPut(0));
			Assembler.bufferedwriter.newLine();			
		}
	}		
	public static void readline(int index) throws IOException{
		String [] line = Assembler.sourceFile[index];
		interFile[index] = new InterFile();
		if(line.length == 3){//一行有3個元素，必定有label
			String label = line[0];
			if(!symbolTAB.containsKey(label)){	//從symbolTAB看有沒有重複的label
				putLabelInSymbolTAB(label);
				interFile[index].label = label;
			}
			else
				interFile[index].ERROR = "line: " + index + "duplicate label: " + label;
		}
		switch(line.length){//判斷一行有幾個元素。
			case 1:			//RSUB
				Mnemonic = line[0];
				break;
			/*case 2: // label + RSUB
				if(line[1] 在OPTAB內 == true)
				break;*/
			default:		//label + Mnemonic + operand || Mnemonic + operand
				Mnemonic = line[line.length - 2];	//Mnemonic
				interFile[index].oprand = line[line.length-1];
				break;
		}
		opCode = Operation.getOperator(Mnemonic);
		interFile[index].setInterLine(locCtr, opCode, Mnemonic);
		Assembler.bufferedwriter.write(interFile[index].outPut(index));
		Assembler.bufferedwriter.newLine();	
		switch(opCode){
			case Operation.NoFound:						//設定ERROR旗標(invalid operation code)
				interFile[index].ERROR = "line: " + index + " can't find operator " + Mnemonic;
				break;
			case Operation.WORD:						
				locCtr += 3;							//將LOCCTR指派為LOCCTR+3BTYE
				break;
			case Operation.RESW:						//label + RESW + 數字(line[2])
				locCtr += 3*Integer.parseInt(line[2]);	//將LOCCTR指派為3*#[OPERAND]
				break;
			case Operation.RESB:						//label + RESB + 數字(line[2])
				locCtr += Integer.parseInt(line[2]);	//將LOCCTR指派為#[OPERAND]
				break;
			case Operation.BYTE:
				//切割sta[0]:C + sta[1]:內容 || sta[0]:X + sta[1]:內容
				String [] sta = line[2].split("'");		
				if(sta[0].equals("C"))
					locCtr += sta[1].length();					 	//將LOCCTR指派為[OPERAND]的內容長度。
				else if(sta[0].equals("X"))
					locCtr += (int)(sta[1].length() * 0.5 + 0.5);	//將LOCCTR指派為[OPERAND]的內容長度。
				break;
			default:									//找到OPTAB有對應的OPCODE
				locCtr += 3;
				break;
		}			
	}	
	public static void readLatline() throws IOException{		
		int lastLine = Assembler.sourceFile.length-1;		
		String [] line = Assembler.sourceFile[lastLine];		
		if(line.length == 2 && line[0].equals("END"))	
			programLength = locCtr - start;
		else if(line[0].equals("END"))
			programLength = locCtr;	
		interFile[lastLine] = new InterFile(programLength, line[0], line[1]);
		Assembler.bufferedwriter.write(interFile[lastLine].outPut(lastLine));
		Assembler.bufferedwriter.newLine();
		Assembler.bufferedwriter.close();
	}
	public static void putLabelInSymbolTAB(String label){
		symbolTAB.put(label, locCtr);
	}
}
