
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SignIn extends JFrame implements MouseListener,ActionListener,ItemListener{
	int def;	
	JLabel welcome,signup,id,password;
	JButton signin;
	JFrame f;
	JTextField id_text;
	JPasswordField password_text;
	Connect cp=new Connect();
	static int id_pass;
	int id_check;
	JCheckBox cb;
	SignIn(){}
	SignIn(int i){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		cb=new JCheckBox("show password",false);
		f=new JFrame("login");
		id=new JLabel("ID");
		id_text=new JTextField();
		password=new JLabel("password");
		password_text=new JPasswordField(20);
		signin=new JButton("Sign In");
		welcome=new JLabel("WELCOME");
		signup=new JLabel("Sign Up");
		def=password_text.getEchoChar();

		signin.addActionListener(this);
		signup.addMouseListener(this);
		cb.addItemListener(this);

		welcome.setBounds(200,75,200,90);
		id.setBounds(100,200,120,30);
		id_text.setBounds(300,200,120,30);
		password.setBounds(100,250,120,30);
		password_text.setBounds(300,250,120,30);
		cb.setBounds(350,290,120,30);
		signin.setBounds(250,400,100,40);
		signup.setBounds(10,460,100,40);

		f.setLayout(null);
		f.add(id);
		f.add(id_text);
		f.add(password);
		f.add(password_text);
		f.add(cb);
		f.add(welcome);
		f.add(signin);
		f.add(signup);
		f.setSize(500,500);
		f.setVisible(true);
		f.setLocation(100,50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getStateChange()==ItemEvent.SELECTED)
		{
			password_text.setEchoChar((char)0);
		}
		else
		{
			String str= new String(password_text.getPassword());
			//String str=pswd.getText();
			password_text.setEchoChar((char)def);
			password_text.setText(str);
		}
	}
	public void actionPerformed(ActionEvent e){
		try{	
			String id=id_text.getText();
			String password=password_text.getText();
			id_check=Integer.parseInt(id);
			cp.output.println("signin");
			cp.output.println(id);
			cp.output.println(password);

			String answer=null;

			answer=cp.input.readLine();
			if(answer.equals("valid")){
				Home h=new Home(cp);
				//	System.out.println(id_check);
				id_set(id_check);
				f.setVisible(false);
				///f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.dispose();
				h.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(null,"Enter Correct credentials");
				f.setVisible(false);
				f.setVisible(true);
			}
		}
		catch(Exception e1){
			System.out.println(e1);
		}
	}
	public void mouseClicked(MouseEvent e){
			SignUp su=new SignUp(cp);
			f.setVisible(false);
			f.dispose();
			su.setVisible(true);	
	}
	public void mouseEntered(MouseEvent e){}  
	public void mouseExited(MouseEvent e){} 
	public void mousePressed(MouseEvent e){}  
	public void mouseReleased(MouseEvent e){}  

	public void id_set(int id){
		id_pass=id;
		//System.out.println(id_pass);
	}
	public int id_get(){
		//JOptionPane.showMessageDialog(null,id_pass);
		//System.out.println(id_pass);
		return id_pass;
	}

	public static void main(String[] args) {
		new SignIn(1);
	}

}
