
public class ListFile {
	public int locCtr;
	public int opCode;	
	public String label;
	public String Mnemonic;
	public String oprand;
	public String ObjectCode;
	public String ERROR;
	public ListFile(){
		locCtr = -1;
		opCode = -5;
		label = "";
		Mnemonic = "";
		oprand = "";
		ObjectCode = "";
		ERROR = "";
	}
	public void setInterLine( String Mnem, String opra){
		Mnemonic = Mnem;
		oprand = opra;
	}	
	public void setInterLine( int loc, String lab, String Mnem, String opra){
		locCtr = loc;
		label = lab;
		Mnemonic = Mnem;
		oprand = opra;
	}	
	public void setInterLine(int loc, int op, String Mnem){
		locCtr = loc;
		opCode = op;
		Mnemonic = Mnem;		
	}
	
	public String outPut(int line){
		return locCtr+"\t"+opCode+"\t"+label+"\t"+Mnemonic+"\t"+oprand+"\t"+ERROR;		
	}
	
}
