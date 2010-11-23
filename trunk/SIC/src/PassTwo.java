import java.io.IOException;


public class PassTwo {
	private static int start = 0;
	private static ListFile listFile;
	private static int lastLine = Assembler.sourceFile.length-1;;
	public static String TextRecord="";
	private static String ObjectCode="";
	private static String label="";
	private static int nextLoc = 0;
	private static int locctr = 0;
	private static int lineStart;
	private static int opcode;
	private final static int MAX_OUTPUT_LENGTH = 60;
	public static void readFirstLine()	throws IOException{		
		String [] line = Assembler.sourceFile[0];				//Ū�i�Ĥ@��
		nextLoc = Integer.parseInt(line[0]);
		listFile = new ListFile();
		if(line[3].equals("START")){							//�P�_�O�_��START�A�N�Ĥ@��#[OPERAND]�w�q���}�l�a�}
			listFile.setInterLine(Integer.parseInt(line[0]), line[2], line[3], line[4]);
			Assembler.bufferedwriter.write(listFile.outPut());
			Assembler.bufferedwriter.newLine();		
		}
		writeHeadRecord(line);
	}	
	public static void readLine(int index)	throws Exception{
		label = "";
		String [] line = Assembler.sourceFile[index];				//Ū�i�Ĥ@��
		opcode = Integer.valueOf(line[1]);
		locctr = Integer.valueOf(line[0]);
		listFile = new ListFile();
		String Mnemonic = null;
		String Operand = null;
		int byteLength = 0;
		int address = 0;
		switch(line.length){										//�P�_�@�榳�X�Ӥ����C
			case 3:												//1+2	//RSUB
				Mnemonic = line[line.length - 1];
				Operand = "";
				break;
			case 5:												//3+2//��label											
				label = line[2];
				Mnemonic = line[line.length - 2];				//Mnemonic
				Operand = line[line.length - 1];
				break;
			/*case 2: // label + RSUB
				if(opcode �bOPTAB�� == true)
				break;*/
			default:								//label + Mnemonic + operand || Mnemonic + operand
				Mnemonic = line[line.length - 2];	//Mnemonic
				Operand = line[line.length - 1];
				break;
		}
		if(opcode != Operation.WORD && opcode != Operation.BYTE 	//�P�_line[1]�O�_���������X�A�����Y�OOPCODE
				&& opcode != Operation.RESB && opcode != Operation.RESW){
			if(Operand != "" && Operand.contains(",X") ){	//���ީw�}	
				address = (1 << 15);
				Operand = line[line.length - 1].split(",X")[0];				//Operand�h���F",X"
			}
			if(PassOne.symbolTAB.containsKey(Operand))	{					//���bsymbolTAB���Operand�A
				address += PassOne.symbolTAB.get(Operand);					//���XOperand�a�}+X
			}else if(Operand != ""){										//�S���h���@�ө|���w�q��symbol
				address =0;
				listFile.ERROR = Operand+"�]�wERROR�X��undefined symbol";	//�]�wERROR�X��(undefined symbol)
				System.out.println(Operand+"�]�wERROR�X��undefined symbol");
			}
			ObjectCode = pad(Integer.toHexString(opcode)+Integer.toHexString(address), 6);
			if(address == 0)
				ObjectCode = pad(Integer.toHexString(opcode)+"0000", 6);		//�g���F�A�s
		}		
		else if(opcode == Operation.WORD){			//�p�GOPCODE��'WORD'�G�N���e�ন�ت��X			
			ObjectCode = intToHexString(Integer.valueOf(line[4]), 6);
		}
		else if(opcode == Operation.BYTE){			//�p�GOPCODE��'BYTE'�G�N���e�ন�ت��X
			String [] sta = line[4].split("'");			
			if(sta[0].equals("C")){									//�p�G�᭱�����OC'���e'
				int i = sta[1].length()-1;							//�p�⤺�e����
				for(int bit = 0; i >= 0 ; i--, bit+=8)	{			//���নASCII CODE
					int tmp = sta[1].charAt(i);
					tmp <<= bit;
					address += tmp;
				}
				byteLength = sta[1].length() * 2;
				ObjectCode = intToHexString(address, byteLength);				
			}else if(sta[0].equals("X"))							//�p�G�᭱�����Ox'���e'	
				ObjectCode = sta[1];								//16�i��X			
		}
		else
			ObjectCode ="";			
		listFile.setInterLine(locctr, label, Mnemonic, Operand, ObjectCode);		
		Assembler.bufferedwriter.write(listFile.outPut());
		Assembler.bufferedwriter.newLine();
		if(ObjectCode != "")										//�S��ObjectCode�N���g�J��ObjectCodeFile
			writeTextRecord(Integer.parseInt(line[0]), ObjectCode);	
	}
	public static void readLastLine()	throws Exception{
		String [] line = Assembler.sourceFile[lastLine-1];				//Ū�i�Ĥ@��
		listFile = new ListFile();
		if(line.length == 4 && line[2].equals("END"))	{
			listFile.setInterLine(line[2], line[3]);
		}else if(line[2].equals("END")){
			listFile.Mnemonic = line[2];
		}
		Assembler.bufferedwriter.write(listFile.outPut());
		writeEndRecord();
	}
	private static void writeHeadRecord(String [] line) throws IOException{//------�gHeadRecord
		start = Integer.parseInt(line[0]);	
		int length = Integer.parseInt(Assembler.sourceFile[lastLine][0]);		
		//------------------------------H+�{���W�ٸɪťը줻��+�_�l��m+�{������10�i���ন16�i��
		Assembler.bufferedObjectCode.write(
				"H" + getSpaceString(line[2], 6)+intToHexString(start, 6)+intToHexString(length, 6));
		Assembler.bufferedObjectCode.newLine();
	}
	private static void writeTextRecord(int loc, String objcode) throws Exception	{//------�gTextRecord
		int objcodeLength = (int)(objcode.length() * 0.5 + 0.5);		
		if(nextLoc == loc)	{							//�ثe��}=�s���@��_�l��m�A�ݭn�}�ҷs���@��
			if(TextRecord.isEmpty())					//�@��}�lTextRecord���Ū�
				lineStart = loc;						//�NlineStart��l�Ƭ�loc
			else if(TextRecord.length()+objcodeLength*2 > MAX_OUTPUT_LENGTH)//TextRecord���׶W�LMAX_OUTPUT_LENGTH
				writeBuffer();								//�p��üg�J�@��TextRecord
		}
		else{											//�g�XTextRecord
			writeBuffer();
			lineStart = loc;							//�U�@��lineStart
		}
		TextRecord += objcode;							
		nextLoc = loc + objcodeLength;					//�U�@�Ӱ_�l��}=�ثe�o��_�l��m+�o�����
	}
	public static void writeBuffer() throws Exception	{//-------------�g�X�ɮ�
		String lineOutput;
		if(TextRecord.length() < MAX_OUTPUT_LENGTH)	{			//�S���W�LMAX_OUTPUT_LENGTH
			lineOutput = TextRecord;
			TextRecord = "";
		}
		else{															//�W�LMAX_OUTPUT_LENGTH
			lineOutput = TextRecord.substring(0, MAX_OUTPUT_LENGTH); 	//�g�JMAX_OUTPUT_LENGTH����������
			TextRecord = TextRecord.substring(MAX_OUTPUT_LENGTH);		//�~��O�dMAX_OUTPUT_LENGTH������
		}		
		//------------------------------"T"+�Ӧ����+�_�l��}+ObjectCode
		Assembler.bufferedObjectCode.write(
				"T"+intToHexString(lineStart, 6)+getBufferLengthString(lineOutput)+lineOutput);
		Assembler.bufferedObjectCode.newLine();		
		lineStart += (int)(lineOutput.length() * 0.5 + 0.5);
	}
	private static void writeEndRecord() throws Exception	{				//------�gEndRecord
		//------------------------------"E"+�{���}�Y��}
		Assembler.bufferedObjectCode.write("E"+intToHexString(start, 6));
	}	
    public static String pad(String str, int length) {
		if(length < str.length())
			str = str.substring(str.length()-length, str.length());
		else {
			length -= str.length();
			for(int i = 0; i < length; i++)										//����length�� "�e��"�ɹs
				str = "0" + str;
		}
		return str;
    }   
	private static String intToHexString(int value, int length)	{			
		String str = Integer.toHexString(value).toUpperCase();				//�ন16�i��
		return pad(str,length);												//����length�� "�e��"�ɹs
	}	
	private static String getSpaceString(String s, int length)	{			//����length�� "�᭱"�ɪťզr��
		length -= s.length();
		for(int i = 0; i < length; i++)	
			s += " ";
		return s;
	}
	private static String getBufferLengthString(String buf)	{				//�p����buf���סA���ন16�i��
		String str = Integer.toHexString((int) (buf.length()*0.5+0.5));
		return pad(str, 2);
	}
}