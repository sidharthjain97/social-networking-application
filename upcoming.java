import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Upcoming extends JFrame implements ActionListener{
	JFrame f;
	JButton back,add;
	//JTextArea friends_name;
	int f_id=0;
	JLabel name,page;
	SignIn si=new SignIn();
	JList<String> list;
	int id=si.id_get();
	DefaultListModel<String> l1=new DefaultListModel<>();
	Connect cp;
	Upcoming(Connect c){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		cp=c;
		f=new JFrame("add friend");
		list = new JList<>(l1);  
		name=new JLabel("enter friend name:");
		page=new JLabel("UPCOMING FRIEND REQUESTS");
		//friends_name=new JTextArea();
		back=new JButton("BACK");
		add=new JButton("ADD");

		page.setBounds(200,50,250,40);

		name.setBounds(150,100,250,40);
		//friends_name.setBounds(150,150,200,200);
		list.setBounds(150,150,200,200);
		back.setBounds(350,400,100,40);
		add.setBounds(150,400,100,40);
		f.add(name);
		f.add(list);
		f.add(back);
		f.add(add);
		f.add(page);
		back.addActionListener(this);
		add.addActionListener(this);

		f.setLayout(null);
		f.setSize(500,500);
		f.setLocation(100,50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		//int i=0;
		try{
			cp.output.println("upcoming");
			cp.output.println(id);

			String answer=cp.input.readLine();
			char a,b;
			a=(char)255;
			b=(char)217;
			System.out.println("a"+a+"b"+b);
			System.out.println(answer);
			while(!answer.equals("end")){
				l1.addElement(answer);
				answer=cp.input.readLine();
			}
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
		else if(e.getSource()==add){
			try{
				cp.output.println("upp add");
				cp.output.println(id);
				String f_name=list.getSelectedValue();
				System.out.println(f_name);
				String f_name_user=null;
				String user_name=null;

				cp.output.println(f_name);
				String answer=cp.input.readLine();
				if(answer.equals("Friend added")){
					JOptionPane.showMessageDialog(null,"friend added");
				}
			}
			catch(Exception e1){
				System.out.println(e1);
			}
		}
		else{}
	}
}

			
					