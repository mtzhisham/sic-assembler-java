
public class InterFile {
	public int locCtr;
	public int opCode;	
	public String label;
	public String Mnemonic;
	public String oprand;
	public int ObjectCode;
	public String ERROR;
	public InterFile(){
		locCtr = -1;
		opCode = -5;
		label = "";
		Mnemonic = "";
		oprand = "";
		ObjectCode = 0;
		ERROR = "";
	}
	public InterFile( int loc, int op, String lab, String Mnem, String opra, String ER){
		locCtr = loc;
		opCode = op;
		label = lab;
		Mnemonic = Mnem;
		oprand = opra;
		ERROR = ER;
	}
	public InterFile( int loc, String Mnem, String opra){
		locCtr = loc;
		opCode = -5;
		label = "";
		Mnemonic = Mnem;
		oprand = opra;
		ERROR = "";
	}	
	public InterFile( int loc, String lab, String Mnem, String opra){
		locCtr = loc;
		opCode = -5;
		label = lab;
		Mnemonic = Mnem;
		oprand = opra;
		ERROR = "";
	}	
	public void setInterLine(int loc, int op, String Mnem){
		locCtr = loc;
		opCode = op;
		Mnemonic = Mnem;		
	}
	public void outPut(int line){
		System.out.println(line+"\t"+locCtr+"\t"+opCode+"\t"+label+"\t"+Mnemonic+"\t"+oprand+"\t"+ERROR);
	}
}
