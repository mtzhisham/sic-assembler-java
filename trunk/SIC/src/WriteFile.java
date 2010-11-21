import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class WriteFile {
	public static FileWriter filewriter;
	public static BufferedWriter bufferedwriter;
	public static void writeObjectFile(String source)	{
		try		{
			filewriter = new FileWriter(new File(source));
			bufferedwriter = new BufferedWriter(filewriter);
		}
		catch (Exception e)		{
			System.out.println("Can't write Object File");
		}
	}
	public static void writeFirstLine(String programName, int start, int length) throws Exception	{
		bufferedwriter.write("H" + getSpaceString(programName, 6));
		bufferedwriter.write(intToString(start, 6));
		bufferedwriter.write(intToString(length, 6));
		bufferedwriter.newLine();
	}
	private static String getBufferLengthString(String buf)	{
		return intToString((int) (buf.length() * 0.5 + 0.5), 2);
	}	
	private static String intToString(int value, int length)	{
		String s = Integer.toHexString(value).toUpperCase();
		length = length - s.length();
		for(int i = 0; i < length; i++)
			s = "0" + s;
		return s;
	}	
	private static String getSpaceString(String s, int length)	{
		length = length - s.length();
		for(int i = 0; i < length; i++)
			s = s + " ";
		return s;
	}
}
