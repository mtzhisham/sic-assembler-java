import java.io.IOException;


public class PassTwo {
	private static int start = 0;
	private static ListFile listFile;
	private static int lastLine = Assembler.sourceFile.length-1;;
	private static StringBuffer TextRecord = new StringBuffer();
	private static String ObjectCode="";
	private static String label="";
	private static String locctrStr;
	private static int locctr;
	private static int opcode;
	public static void readFirstLine()	throws IOException{		
		String [] line = Assembler.sourceFile[0];				//讀進第一行
		listFile = new ListFile();
		if(line[3].equals("START")){							//判斷是否為START，將第一個#[OPERAND]定義為開始地址
			listFile.setInterLine(Integer.parseInt(line[0]), line[2], line[3], line[4]);
			Assembler.bufferedwriter.write(listFile.outPut());
			Assembler.bufferedwriter.newLine();		
		}
		writeHeadRecord(line);
	}	
	public static void readLine(int index)	throws Exception{
		label = "";
		String [] line = Assembler.sourceFile[index];				//讀進第一行
		opcode = Integer.valueOf(line[1]);
		locctrStr = intToHexString(Integer.parseInt(line[0]), 4);
		//locctr = Integer.valueOf(locctrStr);///Integer.parseInt(intToHexString(Integer.parseInt(line[0]), 4));
		//System.out.println(locctr);
		listFile = new ListFile();
		String Mnemonic = null;
		String Operand = null;
		int byteLength;
		String str = "";
		int address = 0;
		switch(line.length){										//判斷一行有幾個元素。
			case 3:												//1+2	//RSUB
				Mnemonic = line[line.length - 1];
				Operand = "";
				break;
			case 5:												//3+2//有label											
				label = line[2];
				Mnemonic = line[line.length - 2];				//Mnemonic
				Operand = line[line.length - 1];
				//System.out.println(Operand);
				break;
			/*case 2: // label + RSUB
				if(opcode 在OPTAB內 == true)
				break;*/
			default:								//label + Mnemonic + operand || Mnemonic + operand
				Mnemonic = line[line.length - 2];	//Mnemonic
				Operand = line[line.length - 1];
				//System.out.println(Operand);
				break;
		}
		if(opcode != Operation.WORD && opcode != Operation.BYTE 	//判斷line[1]是否不為虛擬碼，不為即是OPCODE
				&& opcode != Operation.RESB && opcode != Operation.RESW){
			if(Operand != "" && Operand.contains(",X") ){	//索引定址	
				address = (1 << 15);
				Operand = line[line.length - 1].split(",X")[0];
				ObjectCode = opcode + Integer.valueOf(address).toString();
				//System.out.println(ObjectCode + address);
			}
			if(PassOne.symbolTAB.containsKey(Operand))	{	//有在symbolTAB找到Operand，
				//System.out.println(Operand);
				address = PassOne.symbolTAB.get(Operand);	//取出其地址
			}else if(Operand != ""){										//沒有則為一個尚未定義個symbol
				address =0;
				System.out.println(Operand+"設定ERROR旗標undefined symbol");//設定ERROR旗標(undefined symbol)
			}
			ObjectCode = pad(Integer.toHexString(opcode)+Integer.toHexString(address), 6);			
		}		
		else if(opcode == Operation.WORD){			//如果OPCODE為'WORD'：將內容轉成目的碼			
			ObjectCode = intToHexString(Integer.valueOf(line[4]), 6);
			
		}
		else if(opcode == Operation.BYTE){			//如果OPCODE為'BYTE'：將內容轉成目的碼
			String [] sta = line[4].split("'");			
			if(sta[0].equals("C")){									//如果後面接的是C'內容'
				int i = sta[1].length();							//計算內容長度
				int value = 0;
				i--;
				for(int bit = 0; i >= 0 ; i--, bit+=8)	{			//並轉成ASCII CODE
					int tmp = sta[1].charAt(i);
					tmp <<= bit;
					value += tmp;
				}
				address = value;
				byteLength = sta[1].length() * 2;
				ObjectCode = pad(intToHexString(address, byteLength), 6);				
			}else if(sta[0].equals("X")){							//如果後面接的是x'內容'	
				ObjectCode = sta[1];								//16進制碼
			}
		}
		else
			ObjectCode ="";			
		//System.out.println(locctr+"\t"+line[2]+"\t"+line[3]+"\t"+line[4]+"\t"+ObjectCode);
		listFile.setInterLine(locctr, label, Mnemonic, Operand, ObjectCode);		
		Assembler.bufferedwriter.write(listFile.outPut());
		Assembler.bufferedwriter.newLine();
		TextRecord.append(str);
		//System.out.println(sb);
		if(TextRecord.length() >= 50)
			writeTextRecord(TextRecord);
	}
	public static void readLastLine()	throws Exception{
		String [] line = Assembler.sourceFile[lastLine-1];				//讀進第一行
		listFile = new ListFile();
		listFile.setInterLine(line[2], line[3]);
		Assembler.bufferedwriter.write(listFile.outPut());
		writeEndRecord();
	}
	private static void writeHeadRecord(String [] line) throws IOException{
		start = Integer.parseInt(line[0]);	
		int length = Integer.parseInt(Assembler.sourceFile[lastLine][0]);		
		//------------------------------Head Record+程式名稱
		Assembler.bufferedObjectCode.write("H" + getSpaceString(line[2], 6));		
		//------------------------------起始位置+程式長度10進位轉成16進位
		Assembler.bufferedObjectCode.write(intToHexString(start, 6)+intToHexString(length, 6));	
		Assembler.bufferedObjectCode.newLine();
	}
	private static void writeTextRecord(StringBuffer sb)throws Exception{
		
	}
	private static void writeEndRecord() throws Exception	{
		Assembler.bufferedObjectCode.write("E");
		Assembler.bufferedObjectCode.write(intToHexString(start, 6));
	}	
	private static String intToHexString(int value, int length)	{
		String str = Integer.toHexString(value).toUpperCase();
		length -= str.length();
		for(int i = 0; i < length; i++)										//不足6位補零
			str = "0" + str;
		return str;
	}	
	private static String getSpaceString(String s, int length)	{
		length = length - s.length();
		for(int i = 0; i < length; i++)	
			s = s + " ";
		return s;
	}
    public static String pad(String str, int length) {
    	length -= str.length();
		for(int i = 0; i < length; i++)										//不足6位補零
			str = "0" + str;
		return str;
    }    
}
