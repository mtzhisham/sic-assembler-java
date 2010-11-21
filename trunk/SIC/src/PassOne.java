import java.util.HashMap;
import java.util.Map;


public class PassOne {
	public static int start = 0;
	public 	static int locCtr = 0;
	private static String programName;
	private static String Mnemonic;
	private static LineInterFile[] interFile = new LineInterFile[Assembler.sourceFile.length+1];
	private static int opCode;
	private static int programLength;
	private static Map<String, Integer> symbolTAB = new HashMap<String, Integer>();
	
	public static void readFirstLine()	{
		String [] line = Assembler.sourceFile[0];				//讀進第一行
		if(line[1].equals("START"))				//判斷是否為START			
			//將第一個#[OPERAND]定義為開始地址
			locCtr = start = Integer.parseInt(line[2]);	//將最後一個數值16進位轉成10進位		
		programName = line[0];		
		interFile[0] = new LineInterFile();
		interFile[0].locCtr = start;
		interFile[0].label = programName;
		interFile[0].Mnemonic = "START";
		interFile[0].oprand = line[2];
		System.out.println(0+"\t"+interFile[0].locCtr+"\t"+interFile[0].label+"\t"+interFile[0].Mnemonic+"\t"+interFile[0].oprand);
	}	
	
	public static void readline(int index){
		String [] line = Assembler.sourceFile[index];
		interFile[index] = new LineInterFile();
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
			default:		//label + Mnemonic + operand || Mnemonic + operand
				Mnemonic = line[line.length - 2];	//Mnemonic
				interFile[index].oprand = line[line.length-1];
				break;
		}
		opCode = Operation.getOperator(Mnemonic);
		interFile[index].Mnemonic = Mnemonic;
		interFile[index].opCode = opCode;
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
		interFile[index].locCtr = locCtr;		
		System.out.println(index+"\t"+interFile[index].locCtr+"\t"+interFile[index].label+"\t"+interFile[index].Mnemonic+"\t"+interFile[index].oprand+"\t"+interFile[index].ERROR);
	}	
	public static void readLatline(){		
		int lastLine = Assembler.sourceFile.length-1;	
		String [] line = Assembler.sourceFile[lastLine];		
		if(line.length == 2 && line[0].equals("END"))	
			programLength = locCtr - start;
		else if(line[0].equals("END"))
			programLength = locCtr;			
		System.out.println(lastLine+""+interFile.length);
		interFile[lastLine].Mnemonic = "END";	
		
		interFile[lastLine].oprand = line[1];
		System.out.println(interFile[lastLine].locCtr+"\t"+interFile[lastLine].label+"\t"+interFile[lastLine].Mnemonic+"\t"+interFile[lastLine].oprand);
	}
	public static void putLabelInSymbolTAB(String label){
		symbolTAB.put(label, locCtr);
	}
}
