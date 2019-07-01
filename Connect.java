import java.net.*;
import java.io.*;
import java.sql.*;

public class Connect
{
	Socket s;
	DataInputStream input;
	PrintStream output;

	Connect(){
		try{	
		s=new Socket(InetAddress.getLocalHost(),4321);
		input=new DataInputStream(s.getInputStream());
		output=new PrintStream(s.getOutputStream());
		}
		catch(Exception e){
			System.out.println(e);
		}


	}

}