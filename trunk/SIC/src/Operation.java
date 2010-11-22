import java.util.HashMap;
import java.util.Map;


public class Operation {
	public static final int WORD = -1;
	public static final int BYTE = -2;	
	public static final int RESW = -3;
	public static final int RESB = -4;
	public static final int NoFound = -5;	
	public static Map<String, Integer> OPTAB;
	
	public static void setOperator(){//≥]∏mOPTABLE°AµÍ¿¿ΩX
		OPTAB = new HashMap<String, Integer>();
		OPTAB.put("J",     0x3C);		OPTAB.put("LDA",   0x00);
		OPTAB.put("SUB",   0x1C);		OPTAB.put("CLEAR", 0xB4);
		OPTAB.put("JLT",   0x38);		OPTAB.put("STX",   0x10);
		OPTAB.put("LDCH",  0x50);		OPTAB.put("STCH",  0x54);
		OPTAB.put("TD",    0xE0);		OPTAB.put("RSUB",  0x4C);
		OPTAB.put("STC",   0x14);		OPTAB.put("LDX",   0x04);
		OPTAB.put("STA",   0x0C);		OPTAB.put("ADD",   0x18);
		OPTAB.put("JSUB",  0x48);		OPTAB.put("STL",   0x14);
		OPTAB.put("COMP",  0x28);		OPTAB.put("JEQ",   0x30);
		OPTAB.put("LDL",   0x08);		OPTAB.put("RD",    0xD8);
		OPTAB.put("TIX",   0x2C);		OPTAB.put("WD",    0xDC);
		OPTAB.put("MUL",   0x20);		OPTAB.put("HLT",   0x76);
		OPTAB.put("JGT",   0x34);		OPTAB.put("DIV",   0x24);		
		OPTAB.put("WORD",  WORD);		OPTAB.put("BYTE",  BYTE);
		OPTAB.put("RESW",  RESW);		OPTAB.put("RESB",  RESB);
	}
	public static int getOperator(String Code){
		if(OPTAB == null)
			setOperator();
		try	{
			if(OPTAB.containsKey(Code))
				return OPTAB.get(Code);
			else
				return NoFound;
		}catch (NullPointerException e){
			return NoFound;
		}
	}
	public static boolean isOperator(String op)	{
		return isOperator(getOperator(op));
	}
	public static boolean isOperator(int opCode){
		return opCode >= 0?true:false;
	}	
	
}
