import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class SignUp extends JFrame implements ActionListener{
	JLabel id,password,date,name,page,imgl,upl_page;
	JTextField password_text,id_text,date_text,name_text;
	JButton signup,back,upload;
	JFrame f;
	JSeparator sptr;
	Connect c;
	String path="default_user_img.png";
	//static int count=1;
	SignUp(){

	}
	SignUp(Connect cp){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		c=cp;
		f=new JFrame("sign up");
		id=new JLabel("ID");
		id_text=new JTextField();
		password=new JLabel("Password");
		password_text=new JTextField(20);
		name=new JLabel("Name");
		name_text=new JTextField(20);
		date=new JLabel("DOB");
		date_text=new JTextField(20);
		page=new JLabel("SIGN UP PAGE");
		upl_page=new JLabel("UPLOAD IMAGE");
		signup=new JButton("SIGN UP");
		back=new JButton("BACK");
		imgl=new JLabel();
		upload=new JButton("UPLOAD");

		sptr = new JSeparator(JSeparator.VERTICAL);
		sptr.setForeground(Color.BLACK);
		final int wd,hg;
		wd=sptr.getPreferredSize().width;
		hg=550;
		Dimension size=new Dimension(wd,hg);
		sptr.setMaximumSize(size);
		
		sptr.setBounds(520,10,wd,hg);
		page.setBounds(200,50,200,60);
		upl_page.setBounds(625,50,200,60);
		id.setBounds(100,200,120,30);
		id_text.setBounds(300,200,120,30);
		password.setBounds(100,250,120,30);
		password_text.setBounds(300,250,120,30);
		name.setBounds(100,300,120,30);
		name_text.setBounds(300,300,120,30);
		date.setBounds(100,350,120,30);
		date_text.setBounds(300,350,120,30);

		upload.setBounds(630,450,120,40);
		imgl.setBounds(585,180,200,200);
		imgl.setIcon( ResizeImage("default_user_img.png"));		

		signup.setBounds(120,450,120,40);
		back.setBounds(270,450,120,40);

		signup.addActionListener(this);
		back.addActionListener(this);
		upload.addActionListener(this);
		
		f.add(upload);
		f.add(sptr);
		f.add(imgl);
		f.add(page);
		f.add(upl_page);
		f.add(id);
		f.add(id_text);
		f.add(password);
		f.add(password_text);
		f.add(name);
		f.add(name_text);
		f.add(date);
		f.add(date_text);
		f.add(signup);
		f.add(back);
	//	id_text.setText(String.valueOf(count));
		f.setLayout(null);
		f.setSize(850,600);
		f.setVisible(true);
		f.setLocation(200,50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void actionPerformed(ActionEvent e){
		

		if(e.getSource()==signup){
			String name=name_text.getText();
			String id=id_text.getText();
			String date=date_text.getText();
			String password=password_text.getText();

			try{
			
				c.output.println("signup");
				c.output.println(name);
				c.output.println(id);
				c.output.println(date);
				c.output.println(password);

				FileInputStream fin=new FileInputStream(path);
	           	int i;
	           	while(true)
	           	{
	           		
	           		if((i=fin.read())==255)
					{
						c.output.write(i);
						if((i=fin.read())==217)
						{
							c.output.write(i);
							break;
						}	
						else
							c.output.write(i);		
					}
					else
						c.output.write(i);
	           		//System.out.println(i);
	           	}
	           	fin.close();
	           	//System.out.println(i);

	           	System.out.println("File Send");

				String answer=null;

				answer=c.input.readLine();
				if(answer.equals("usercreated")){
					JOptionPane.showMessageDialog(null,"USER CREATED");
					SignIn si=new SignIn(1);
					f.setVisible(false);
					si.setVisible(true);
					f.dispose();
				}
			}
			catch(Exception e1){
				System.out.println(e);
			}
		
		}
		else if(e.getSource()==back){
			
			SignIn si=new SignIn(1);
			f.setVisible(false);
			si.setVisible(true);
			f.dispose();
		}
		else if(e.getSource()==upload)
		{
			
           	

           	System.out.println("upld");
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

	        }
	        //if the user click on save in Jfilechooser


	        else if(result == JFileChooser.CANCEL_OPTION){
	            System.out.println("No File Select");
	        }
		}
		else{}
		//count++;
	}

	 public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(imgl.getWidth(), imgl.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

 /*   public void paint(Graphics g)
	{
	     //Get the current size of this component
	    Dimension d = this.getSize();

	     //draw in black
	    g.setColor(Color.BLACK);
	     //draw a centered horizontal line
	    g.drawLine(0,d.height/2,d.width,d.height/2);
	}*/

	public static void main(String[] args) {
		new SignUp();
		
	}
}