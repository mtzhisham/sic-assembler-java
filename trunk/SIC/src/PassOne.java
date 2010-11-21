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
		
		String [] line = Assembler.sourceFile[0];				//Ū�i�Ĥ@��
		if(line[1].equals("START")){//�P�_�O�_��START			
			//�N�Ĥ@��#[OPERAND]�w�q���}�l�a�}
			locCtr = start = Integer.parseInt(line[2], 16);	//�N�̫�@�Ӽƭ�16�i���ন10�i��		
			programName = line[0];		
			interFile[0] = new InterFile(start, programName, line[1], line[2]);	
			Assembler.bufferedwriter.write(interFile[0].outPut(0));
			Assembler.bufferedwriter.newLine();			
		}
	}		
	public static void readline(int index) throws IOException{
		String [] line = Assembler.sourceFile[index];
		interFile[index] = new InterFile();
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
			/*case 2: // label + RSUB
				if(line[1] �bOPTAB�� == true)
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
