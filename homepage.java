import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.net.*;
import java.io.*;

class Home extends JFrame implements MouseListener,ActionListener{
	JLabel page;
	JRadioButton[] jb;
	JButton ok,logout;
	ButtonGroup bg;
	JFrame f;
	JLabel name,msg,copyright,changebg,imgl;
	JTextField name_text;
	JTextArea area;
	Connect cp;
	
	FileOutputStream fout;
	FileInputStream fin;
	int i=0;
	Home(Connect c){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		cp=c;
		f=new JFrame("homepage");
		page=new JLabel("HOMEPAGE");
		imgl=new JLabel();
		copyright=new JLabel("copyright@SSP.Co");
		changebg=new JLabel("change background");
		jb=new JRadioButton[6];
		ok=new JButton("ok");
		logout=new JButton("LOGOUT");
		bg=new ButtonGroup();

		jb[0]=new JRadioButton("Add Friend");
		jb[1]=new JRadioButton("Inbox");
		jb[2]=new JRadioButton("Upcoming Friend Request");
		//jb[3]=new JRadioButton("Send Message");
		jb[3]=new JRadioButton("total number of friends");
		
		jb[4]=new JRadioButton("Profile");

		bg.add(jb[0]);
		bg.add(jb[1]);
		bg.add(jb[2]);
		bg.add(jb[3]);
		bg.add(jb[4]);
		bg.add(jb[5]);
		
		
		page.setBounds(200,50,120,50);
		jb[0].setBounds(50,100,200,40);
		jb[1].setBounds(50,150,200,40);
		jb[2].setBounds(50,200,300,40);
		jb[3].setBounds(50,250,200,40);
		jb[4].setBounds(50,300,200,40);
		
		
		ok.setBounds(200,450,100,50);
		imgl.setBounds(500,100,150,150);
		logout.setBounds(400,450,100,50);
		copyright.setBounds(570,550,130,50);
		changebg.setBounds(10,550,150,50);

		//this.addWindowListener(this);
		ok.addActionListener(this);
		logout.addActionListener(this);
		changebg.addMouseListener(this);
		

		f.add(jb[0]);
		f.add(jb[1]);
		f.add(jb[2]);
		f.add(jb[3]);
		f.add(jb[4]);
		
		
		f.add(page);
		f.add(imgl);
		f.add(ok);
		f.add(logout);
		f.add(copyright);
		//f.add(changebg);

		f.setLayout(null);
		f.setVisible(true);
		f.setLocation(100,50);
		f.setSize(700,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imgl.setIcon( ResizeImage("default_user_img.png"));

		
	}
	public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(imgl.getWidth(), imgl.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

	public void actionPerformed(ActionEvent e){
		SignIn si=new SignIn();
		int id=si.id_get();
		if(e.getSource()==ok){
			if(jb[0].isSelected()){						//add friend
				Addfriend af=new Addfriend(cp);
				f.setVisible(false);
				af.setVisible(true);
			}
			else if(jb[1].isSelected()){		//inbox
				Messenger m=new Messenger(cp);
				f.setVisible(false);
				m.setVisible(true);
			}

			else if(jb[2].isSelected()){    //upcoming friend request
				
				Upcoming u=new Upcoming(cp);
				
				f.setVisible(false);
				
				u.setVisible(true);
			}

			else if(jb[3].isSelected()){			//total friends
				Total t=new Total(cp);
				f.setVisible(false);
				t.setVisible(true);	}

			else if(jb[4].isSelected()){			//total friends
				Profile p=new Profile(cp);
				f.setVisible(false);
				p.setVisible(true);	}
			else{}
		}
		else if(e.getSource()==logout){
			cp.output.println("over");
				JOptionPane.showMessageDialog(null,"SIGNING OUT!!! \nSEE YOU AGAIN!!!");
				System.exit(0);
		}
		
		else{}
	}
	public void mouseClicked(MouseEvent e){
		
	}
	public void mouseEntered(MouseEvent e){}  
	public void mouseExited(MouseEvent e){} 
	public void mousePressed(MouseEvent e){}  
	public void mouseReleased(MouseEvent e){}

	
}