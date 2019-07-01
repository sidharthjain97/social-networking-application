
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.event.*;

class Messenger extends JFrame implements ActionListener,ListSelectionListener{
	JList<String> f_list;
	DefaultListModel<String> l=new DefaultListModel<>();
	JFrame f;
	JButton send,back;
	JTextArea convo,message;
	JLabel l1,l2,l3;
	SignIn si=new SignIn();
	int id=si.id_get();
	FileInputStream fin;
	FileOutputStream fout;
	Connect cp;
	Messenger(Connect c){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		cp=c;
		f=new JFrame("Inbox");
		send=new JButton("Send");
		back=new JButton("back");
		convo=new JTextArea();
		message=new JTextArea();
		f_list=new JList<>(l);
		l1=new JLabel("Friends name");
		l2=new JLabel("Conversation");
		l3=new JLabel("enter new message");

		send.addActionListener(this);
		back.addActionListener(this);
		f_list.addListSelectionListener(this);

		l1.setBounds(50,20,100,25);
		f_list.setBounds(50,50,200,300);
		send.setBounds(50,375,100,30);
		back.setBounds(200,375,100,30);
		l2.setBounds(350,20,100,25);
		convo.setBounds(350,50,300,300);
		l3.setBounds(50,420,200,30);
		message.setBounds(50,450,350,100);

		f_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		f.add(f_list);
		f.add(send);
		f.add(back);
		f.add(convo);
		f.add(message);
		f.add(l1);
		f.add(l2);
		f.add(l3);

		f.setLayout(null);
		f.setVisible(true);
		f.setLocation(50,50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800,600);
		try{
			cp.output.println("messenger");
			cp.output.println(id);
			String answer=cp.input.readLine();
			while(!(answer.equals("end"))){
				l.addElement(answer);
				answer=cp.input.readLine();
			}

		}
		catch(Exception e4){
			System.out.println(e4);
		}
		
	}
	public void actionPerformed(ActionEvent e){

		String f_name=f_list.getSelectedValue();
		if(e.getSource()==send){
			cp.output.println("messengersend");
			cp.output.println(id);

			try{
				String msg=message.getText();
				//cp.output.println("send");
				cp.output.println(f_name);
				cp.output.println(msg);
				convo.setText(null);
				String answer=cp.input.readLine();

					while(!(answer.equals("end"))){
						if(answer.equals("~")){
							answer="\n";
							convo.append(answer);
						}
						else{
							convo.append(answer);
						}
						answer=cp.input.readLine();
						
					}
				f.setVisible(false);
				f.setVisible(true);
			}
			catch(Exception e3){
				System.out.println(e3);
			}
		}
		else if(e.getSource()==back){
			Home h=new Home(cp);
			f.setVisible(false);
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			h.setVisible(true);
		}
		else{}

	}
	public void valueChanged(ListSelectionEvent e){
		if (!e.getValueIsAdjusting()){
			try{	
				String f_name=f_list.getSelectedValue();
				cp.output.println("messengerlist");
				cp.output.println(id);
				cp.output.println(f_name);
				String answer=cp.input.readLine();
				convo.setText(null);
				
					
					while(!(answer.equals("end"))){
						if(answer.equals("~")){
							answer="\n";
							convo.append(answer);
						}
						else{
							convo.append(answer);
						}
						answer=cp.input.readLine();
						
					}
					f.setVisible(false);
					f.setVisible(true);
			}

			catch(Exception e2){
				System.out.println(e2);
			}
		}
	}

}
