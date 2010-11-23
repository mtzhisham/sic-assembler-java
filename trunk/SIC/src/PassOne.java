import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class PassOne {
	public static int start = 0;
	public 	static int locCtr = 0;
	public 	static ListFile listFile;
	private static int opCode;
	private static int programLength = 0;
	public static Map<String, Integer> symbolTAB = new HashMap<String, Integer>();

	public static void readFirstLine()	throws IOException{		
		String [] line = Assembler.sourceFile[0];				//Ū�i�Ĥ@��
		listFile = new ListFile();
		if(line[1].equals("START")){							//�P�_�O�_��START�A�N�Ĥ@��#[OPERAND]�w�q���}�l�a�}
			locCtr = start = Integer.parseInt(line[2], 16);		//�N�̫�@�Ӽƭ�16�i���ন10�i��	
			listFile.setInterLine(locCtr, line[0], line[1], line[2]);
			Assembler.bufferedwriter.write(listFile.outPut(0));
			Assembler.bufferedwriter.newLine();			
		}
	}		
	public static void readline(int index) throws IOException{
		String [] line = Assembler.sourceFile[index];
		String Mnemonic;
		listFile = new ListFile();
		if(line.length == 3){								//�@�榳3�Ӥ����A���w��label
			String label = line[0];
			if(!symbolTAB.containsKey(label)){					//�qsymbolTAB�ݨS�����ƪ�label
				putLabelInSymbolTAB(label);
				listFile.label = label;
			}
			else	listFile.ERROR = "line: " + index + "duplicate label: " + label;	//�����Ʃw�qlabel
		}
		switch(line.length){//�P�_�@�榳�X�Ӥ����C
			case 1:			//RSUB
				Mnemonic = line[line.length - 1];
				break;
			/*case 2: // label + RSUB
				if(line[1] �bOPTAB�� == true)
				break;*/
			default:		//label + Mnemonic + operand || Mnemonic + operand
				Mnemonic = line[line.length - 2];	//Mnemonic
				listFile.oprand = line[line.length-1];
				break;
		}
		opCode = Operation.getOperator(Mnemonic);
		listFile.setInterLine(locCtr, opCode, Mnemonic);
		switch(opCode){
			case Operation.NoFound:						//�]�wERROR�X��(invalid operation code)
				listFile.ERROR = "line: " + index + " can't find operator " + Mnemonic;
				break;
			case Operation.WORD:						
				locCtr += 3;							//�NLOCCTR������LOCCTR+3BTYE
				break;
			case Operation.RESW:						//label + RESW + �Ʀr(line[2])
				locCtr += 3*Integer.parseInt(line[line.length - 1]);	//�NLOCCTR������3*#[OPERAND]
				break;
			case Operation.RESB:						//label + RESB + �Ʀr(line[2])
				locCtr += Integer.parseInt(line[line.length - 1]);	//�NLOCCTR������#[OPERAND]
				break;
			case Operation.BYTE:
				//����sta[0]:C + sta[1]:���e || sta[0]:X + sta[1]:���e
				String [] sta = line[line.length - 1].split("'");		
				if(sta[0].equals("C"))
					locCtr += sta[1].length();					 	//�NLOCCTR������[OPERAND]�����e���סC
				else if(sta[0].equals("X"))
					locCtr += (int)(sta[1].length() * 0.5 + 0.5);	//�NLOCCTR������[OPERAND]�����e���סC
				break;
			default:									//���OPTAB��������OPCODE
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
	public static void putLabelInSymbolTAB(String label){
		symbolTAB.put(label, locCtr);			//�Q�i��locCtr
	}
}
