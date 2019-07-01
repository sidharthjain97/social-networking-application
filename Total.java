import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class Total extends JFrame implements ActionListener{
	JFrame f;
	JButton back,remove;
	JList<String> friends_name;
	DefaultListModel<String> l=new DefaultListModel<>();

	JLabel page,number;
	SignIn si=new SignIn();
	int id=si.id_get();
	Connect cp;

	Total(Connect c){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		cp=c;
		f=new JFrame("total friend");
		page=new JLabel("TOTAL NUMBER OF FRIENDS");
		number=new JLabel();

		friends_name=new JList<>(l);
		back=new JButton("BACK");
		remove=new JButton("REMOVE");

		page.setBounds(150,50,350,40);
		number.setBounds(400,300,100,30);
		friends_name.setBounds(150,150,200,200);
		back.setBounds(350,400,100,40);
		remove.setBounds(350,500,100,40);
		
		f.add(friends_name);
		f.add(back);
		f.add(remove);
		f.add(page);
		f.add(number);

		back.addActionListener(this);
		remove.addActionListener(this);

		f.setLayout(null);
		f.setSize(600,600);
		f.setLocation(100,50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		

		try{
	
			cp.output.println("total");
			cp.output.println(id);

			
			String names=cp.input.readLine();
			//System.out.println("names receiving");
			while(!names.equals("done")){
				l.addElement(names);
				System.out.println(names);
				names=cp.input.readLine();
			}
			//System.out.println("count recevied");
			int count=Integer.parseInt(cp.input.readLine());
			//System.out.println(count);
			number.setText(String.valueOf(count));
		}		
		catch(Exception e1){
			System.out.println(e1);
		}
	
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==back){
			Home h=new Home(cp);
			f.setVisible(false);
			h.setVisible(true);
		}
		else if(e.getSource()==remove){
			try{
				cp.output.println("remove");
				cp.output.println(id);

				String f_name=friends_name.getSelectedValue();
				int index=friends_name.getSelectedIndex();
				l.remove(index);
				cp.output.println(f_name);	
				
				String answer=cp.input.readLine();
				if(answer.equals("Friend removed")){
					JOptionPane.showMessageDialog(null,"friend removed");
				}

			}
			catch(Exception e1){
				System.out.println(e1);
			}
			f.setVisible(false);
			f.setVisible(true);
		}
		else{}
	}

}