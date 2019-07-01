import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

class ServerMain extends JFrame implements ActionListener{
	JFrame f;
	JButton show,exit;
	JLabel l1;
	JTable t;

	ServerMain(){
		f=new JFrame("server");
		show=new JButton("SHOW");
		exit=new JButton("EXIT");
		l1=new JLabel("USERS DETAILS:");

		l1.setBounds(50,100,200,40);
		
		show.setBounds(50,400,100,30);
		exit.setBounds(200,400,100,30);

		f.add(l1);
		
		f.add(show);
		f.add(exit);

		show.addActionListener(this);
		exit.addActionListener(this);	

		f.setLayout(null);
		f.setVisible(true);
		f.setSize(600,600);
		f.setLocation(100,100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==show){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fb","root","rita1234");
				Statement stmt=con.createStatement();
				String q="select * from users;";
				ResultSet rs=stmt.executeQuery(q);
				ResultSetMetaData rsmd=rs.getMetaData();
				int col=rsmd.getColumnCount();
				Vector<String> column=new Vector<String>(col);
				
				
				for(int i=0;i<col;i++){
					column.add(rsmd.getColumnName(i+1));
				}
				System.out.println(column);
				Vector data=new Vector();
				data.add(column);
				while(rs.next()){
					Vector row=new Vector(col);
					for(int i=0;i<col;i++){
						row.add(rs.getString(i+1));
					}
					data.add(row);
				}
				t=new JTable(data,column);
				t.setBounds(50,180,400,200);
				f.add(t);		
				
				f.setVisible(false);
				f.setVisible(true);

				con.close();
				

			}
			catch(Exception e1){
				System.out.println(e1);
			}
		}
		else if(e.getSource()==exit){
			System.exit(0);
		}
		else{}
	}
	public static void main(String[] args) {
		new ServerMain();
	}

}
