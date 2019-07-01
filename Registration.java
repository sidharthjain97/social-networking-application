import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BackGroundPanel extends JPanel
{
	Image bgi=new ImageIcon("register.jpg").getImage();
	public void paintComponent(Graphics g)
	{
		g.drawImage(bgi,0,0,getWidth(),getHeight(),this);
	}
}
class Registration extends JPanel implements ItemListener
{
	JPanel p;
	ClientFrame cf;
	JPanel bgp;
	int def;
	JButton submit,reset,back;
	JTextField name;JPasswordField pswd;
	JLabel nm,pd,ql; 
	JComboBox box;
	JCheckBox cb;
	String str[]={"Student","Faculty","Non-teaching Staff","Others","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a"};
	Registration()
	{

				try 
				{
		         	//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); 
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					//UIManager.setLookAndFeel ("javax.swing.plaf.windows.WindowsLookAndFeel"); 
					//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
					UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel"); 
					//UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
		        } 
		        catch (Exception e) 
		        {
		          System.err.println("Look and feel not set.");
		        }


		p=new JPanel();
		bgp=new BackGroundPanel();
		bgp.setLayout(new BorderLayout());
		cb=new JCheckBox("Show Password");
		submit=new JButton("ok");
		reset=new JButton("reset");
		back=new JButton("back");

		name=new JTextField(25);
		pswd=new JPasswordField(25);
		//pswd.setEchoChar('*');
		def=pswd.getEchoChar();
		nm=new JLabel("NAME");
		pd=new JLabel("PASSWORD");
		ql=new JLabel("QUALIFICATION");

		box=new JComboBox(str);

		nm.setBounds(100,100,120,30);
		name.setBounds(230,100,250,30);
		ql.setBounds(100,180,120,30);
		box.setBounds(230,180,250,30);
		pd.setBounds(100,260,120,30);
		pswd.setBounds(230,260,250,30);
		cb.setBounds(235,320,140,20);
		submit.setBounds(170,370,80,30);
		reset.setBounds(260,370,80,30);
		back.setBounds(350,370,80,30);

		cb.addItemListener(this);

		nm.setFont(new Font("Ubuntu",Font.BOLD,13));
		pd.setFont(new Font("Ubuntu",Font.BOLD,13));
		ql.setFont(new Font("Ubuntu",Font.BOLD,13));

		p.add(nm);p.add(name);
		p.add(ql);p.add(box);
		p.add(pd);p.add(pswd);
		p.add(cb);
		p.add(submit);p.add(reset);p.add(back);

		p.setSize(600,600);
		p.setOpaque(false);
		p.setVisible(true);
		p.setLayout(null);
		bgp.add(p,BorderLayout.CENTER);

		cf = new ClientFrame("Registration");
		cf.f.add(bgp);
		cf.f.setContentPane(bgp);
		cf.f.setVisible(true);
	}

	public void itemStateChanged(ItemEvent e)
	{
		if(e.getStateChange()==ItemEvent.SELECTED)
		{
			pswd.setEchoChar((char)0);
		}
		else
		{
			String str= new String(pswd.getPassword());
			//String str=pswd.getText();
			pswd.setEchoChar((char)def);
			pswd.setText(str);
		}
	}	

	public static void main(String args[])
	{
		new Registration();
	}
}