import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class Profile extends JFrame implements ActionListener,WindowListener{
	JLabel profile,name,password,date,idl,imgl;
	JTextField name_text,password_text,date_text,id_text;
	JFrame f;
	SignIn si=new SignIn();
	int id=si.id_get();
	Connect cp;
	JButton back,changedp;
	Profile(Connect c){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		cp=c;
		f=new JFrame();

		profile=new JLabel("PROFILE");
		name=new JLabel("NAME");
		idl=new JLabel("ID");
		password=new JLabel("PASSWORD");
		date=new JLabel("DOB");
		back=new JButton("BACK");
		changedp=new JButton("Change Avatar");
		imgl=new JLabel();

		name_text=new JTextField(30);
		id_text=new JTextField(30);
		password_text=new JTextField(30);
		date_text=new JTextField(30);

		profile.setBounds(200,50,100,40);
		name.setBounds(100,100,100,30);
		name_text.setBounds(250,100,150,30);
		idl.setBounds(100,130,100,30);
		id_text.setBounds(250,130,150,30);
		password.setBounds(100,170,100,30);
		password_text.setBounds(250,170,150,30);
		
		date.setBounds(100,250,100,30);
		date_text.setBounds(250,250,150,30);
		back.setBounds(250,400,150,30);
		changedp.setBounds(450,350,150,30);
		imgl.setBounds(430,150,150,150);

		name_text.setEditable(false);
		id_text.setEditable(false);
		password_text.setEditable(false);
		date_text.setEditable(false);

		//change.addMouseListener(this);
		back.addActionListener(this);
		changedp.addActionListener(this);
		this.addWindowListener(this);
		
		f.add(profile);
		f.add(name);
		f.add(name_text);
		f.add(idl);
		f.add(id_text);
		f.add(password);
		f.add(imgl);
		f.add(password_text);
		
		f.add(date);
		f.add(date_text);
		f.add(back);
		f.add(changedp);
		imgl.setIcon( ResizeImage("default_user_img.png"));

		f.setLayout(null);
		
		f.setSize(600,600);
		f.setLocation(100,50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		
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
		if(e.getSource()==back){
			Home h=new Home(cp);
			f.setVisible(false);
			h.setVisible(true);
		}
		else if(e.getSource()==changedp){

			cp.output.println("change dp");
			cp.output.println(id);
			String path=null;

	        JFileChooser file = new JFileChooser();
	        file.setCurrentDirectory(new File(System.getProperty("user.home")));
	        //filter the files
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
	        file.addChoosableFileFilter(filter);
	        int result = file.showSaveDialog(null);
	        //if the user click on save in Jfilechooser
	        if(result == JFileChooser.APPROVE_OPTION){
	            File selectedFile = file.getSelectedFile();
	            path = selectedFile.getAbsolutePath();
	            imgl.setIcon(ResizeImage(path));
	           	System.out.println(path);

	           try
	           {
	           		FileInputStream fin=new FileInputStream(path);
		           	int i;
		           	while(true)
		           	{
		           		
		           		if((i=fin.read())==255)
						{
							cp.output.write(i);
							if((i=fin.read())==217)
							{
								cp.output.write(i);
								break;
							}	
							else
								cp.output.write(i);		
						}
						else
							cp.output.write(i);
		           		//System.out.println(i);
		           	}
		           	fin.close();
	           }
	           catch(Exception e4)
	           {
	           	e4.printStackTrace();
	           }
	           	//System.out.println(i);

	           	System.out.println("File Send");
	           	JOptionPane.showMessageDialog(null,"Avatar Updated!!");

	        }
	        //if the user click on save in Jfilechooser


	        else if(result == JFileChooser.CANCEL_OPTION){
	            System.out.println("No File Select");
	        }

		}
		else{}
	}
	public void windowOpened(WindowEvent e){
	}
	  public void windowClosing(WindowEvent e) {
	    System.out.println("Window Closing Event");
	  }

	  public void windowClosed(WindowEvent e) {
	    System.out.println("Window Close Event");
	  }

	  public void windowIconified(WindowEvent e) {
	    System.out.println("Window Iconified Event");
	  }

	  public void windowDeiconified(WindowEvent e) {
	    System.out.println("Window Deiconified Event");
	  }

	  public void windowActivated(WindowEvent e) {
	    System.out.println("Window Activated Event");
	    SignIn si=new SignIn();
		int u_id=si.id_get();
		int i=0;
		try{
	
			cp.output.println("profile");
			cp.output.println(id);
			
			String answer=cp.input.readLine();
			name_text.setText(answer);
			answer=cp.input.readLine();
			id_text.setText(answer);
			answer=cp.input.readLine();
			password_text.setText(answer);
			answer=cp.input.readLine();
			date_text.setText(answer);

		}		
		catch(Exception e1){
			System.out.println(e1);
		}

	    try{
			System.out.println("window event performed");
			cp.output.println("profile image");
			cp.output.println(u_id);
			System.out.println(u_id);

			String path="/home/saurabh/Documents/user_image_"+u_id+".jpg";
			FileOutputStream fout=new FileOutputStream(path);
			while(true)
			{
				//fout.write(i);
				if((i=cp.input.read())==255)
				{
					fout.write(i);
					if((i=cp.input.read())==217)
					{
						fout.write(i);
						break;
					}	
					else
						fout.write(i);		
				}
				else
					fout.write(i);
					//System.out.println(i);
			}
			fout.write(i);

			System.out.println("Image received");
			fout.close();
			imgl.setIcon( ResizeImage(path));
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	  }

	  public void windowDeactivated(WindowEvent e) {
	    System.out.println("Window Deactivated Event");
	  }

	  public void windowStateChanged(WindowEvent e) {
	    System.out.println("Window State Changed Event");
	  }

	  public void windowGainedFocus(WindowEvent e) {
	    System.out.println("Window Gained Focus Event");
	  }

	  public void windowLostFocus(WindowEvent e) {
	    System.out.println("Window Lost Focus Event");
	  }

}