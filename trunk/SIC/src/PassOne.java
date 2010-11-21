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
		String [] line = Assembler.sourceFile[0];				//Ū�i�Ĥ@��
		if(line[1].equals("START"))				//�P�_�O�_��START			
			//�N�Ĥ@��#[OPERAND]�w�q���}�l�a�}
			locCtr = start = Integer.parseInt(line[2]);	//�N�̫�@�Ӽƭ�16�i���ন10�i��		
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
		if(line.length == 3){//�@�榳3�Ӥ����A���w��label
			String label = line[0];
			if(!symbolTAB.containsKey(label)){	//�qsymbolTAB�ݦ��S�����ƪ�label
				putLabelInSymbolTAB(label);
				interFile[index].label = label;
			}
			else
				interFile[index].ERROR = "line: " + index + "duplicate label: " + label;
		}
		switch(line.length){//�P�_�@�榳�X�Ӥ����C
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
			case Operation.NoFound:						//�]�wERROR�X��(invalid operation code)
				interFile[index].ERROR = "line: " + index + " can't find operator " + Mnemonic;
				break;
			case Operation.WORD:						
				locCtr += 3;							//�NLOCCTR������LOCCTR+3BTYE
				break;
			case Operation.RESW:						//label + RESW + �Ʀr(line[2])
				locCtr += 3*Integer.parseInt(line[2]);	//�NLOCCTR������3*#[OPERAND]
				break;
			case Operation.RESB:						//label + RESB + �Ʀr(line[2])
				locCtr += Integer.parseInt(line[2]);	//�NLOCCTR������#[OPERAND]
				break;
			case Operation.BYTE:
				//����sta[0]:C + sta[1]:���e || sta[0]:X + sta[1]:���e
				String [] sta = line[2].split("'");		
				if(sta[0].equals("C"))
					locCtr += sta[1].length();					 	//�NLOCCTR������[OPERAND]�����e���סC
				else if(sta[0].equals("X"))
					locCtr += (int)(sta[1].length() * 0.5 + 0.5);	//�NLOCCTR������[OPERAND]�����e���סC
				break;
			default:									//���OPTAB��������OPCODE
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
