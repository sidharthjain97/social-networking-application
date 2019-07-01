import java.nio.file.*;
import java.net.*;
import java.io.*;
import java.sql.*;

public class ServerTest
{
	//static int z=1;
	ServerSocket ss;
	Socket s;
	/*int count=0;
	FileOutputStream fout;
	FileInputStream fin;*/
	ServerTest(int port)throws Exception
	{


		ss=new ServerSocket(port);
		while(true){
			s=ss.accept();
			ServiceHandler sh=new ServiceHandler(s);
			
		}
	}

	public static void main(String args[])throws Exception
	{
		ServerTest sr=new ServerTest(4321);

	}
}

class ServiceHandler implements Runnable
{
	Thread t;
	Socket s;
	static int z=1;
	/*ServerSocket ss;
	Socket s;
	*/int count=0;
	FileOutputStream fout;
	FileInputStream fin;
	ServiceHandler(Socket ser)
	{
		s=ser;
		t=new Thread(this);
		t.start();
	}

	public void run()
	{
		try{
			System.out.println("Connection established");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("database connected");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fb","root","srhtrip");
			Statement stmt=con.createStatement();

			BufferedReader console=new BufferedReader(new InputStreamReader(System.in));
			//BufferedReader client=new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintStream output=new PrintStream(s.getOutputStream());
			DataInputStream client=new DataInputStream(s.getInputStream());

		
			String check=" ";

			while(!(check.equals("over"))){
				check=client.readLine();
				System.out.println(check);
				if(check.equals("signin")){
					System.out.println("entered signin");
					int id,flag=0;
					String pswd;

					id=Integer.parseInt(client.readLine());
					pswd=client.readLine();


					ResultSet rs=stmt.executeQuery("select id,password from users");
					while(rs.next()){
						int id_check=rs.getInt(1);
						String password_check=rs.getString(2);
						if(id==id_check && pswd.equals(password_check)){
							flag=1;
							break;

						}
					}

					if(flag==1)
						output.println("valid");
					else
						output.println("invalid");
				}
				else if(check.equals("signup")){
					int i=0;
					String name=client.readLine();
					int id=Integer.parseInt(client.readLine());
					String date=client.readLine();
					String password=client.readLine();
					System.out.println("values received");
					
					String query="insert into users(id,name,date,password) values("+id+",'"+name+"','"+date+"','"+password+"');";

					String q1="create table "+id+"_friends (user_name varchar(30) primary key,status varchar(30),path varchar(100));";
					System.out.println("tables created");
					
					stmt.executeUpdate(query);
					stmt.executeUpdate(q1);
					String path="/home/saurabh/Pictures/user_image_"+id+".jpg";
					fout=new FileOutputStream(path);
					String qry="update users set image='"+path+"' where id="+id+";";
					stmt.executeUpdate(qry);
					while(true)
					{
						//fout.write(i);
						if((i=client.read())==255)
						{
							fout.write(i);
							if((i=client.read())==217)
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

					System.out.println("Image received");
					fout.close();

					output.println("usercreated");
				}
				else if(check.equals("addfriend")){
			
					System.out.println("add friend db");
					String f_name=client.readLine();String user_name=null,status=null,f_name_user=null;
					int f_id=0,flag1=0;int id=Integer.parseInt(client.readLine());

					System.out.println(id);
					System.out.println(f_name);
					String q1="select id,name from users;";
					ResultSet rs1=stmt.executeQuery(q1);
					while(rs1.next()){
						f_name_user=rs1.getString(2);
						if(f_name.equals(f_name_user)){
							System.out.println("user exists");
							f_id=rs1.getInt(1);
							flag1=1;
							break;
						}
					}
					String q2="select name from users where id="+id+";";
					ResultSet rs2=stmt.executeQuery(q2);
					while(rs2.next()){
						user_name=rs2.getString(1);
						System.out.println(user_name);
						break;
					}

					if(flag1==1){
						System.out.println("lets seee");
						//System.out.println(id);
						String q3="select * from "+id+"_friends;";
						//System.out.println(id);
						ResultSet rs3=stmt.executeQuery(q3);
						System.out.println("rssss");
						if(rs3.next()){
							rs3.beforeFirst();
							System.out.println("if read");
							while(rs3.next()){
								System.out.println("check");
								String a=rs3.getString(1);
								if(a.equals(f_name)){
									System.out.println("friend name found in table");
									status=rs3.getString(2);
									if(status.equals("friend")){
										output.println("already friend");
										//f.setVisible(false);
										//h.setVisible(true);
										break;
									}
									else if(status.equals("requested")){
										System.out.println("alreay requested");
										output.println("already requested");
										//f.setVisible(false);
										//h.setVisible(true);
										break;
									}
									else if(status.equals("request")){

		  								String path="/home/saurabh/Pictures/"+id+"_"+f_id+"_messenger.txt";
		  								FileOutputStream fout=new FileOutputStream(path);
										System.out.println("sending");
										String q4="update "+id+"_friends set status='friend' where user_name='"+f_name+"';";
										stmt.executeUpdate(q4);
										String q10="update "+id+"_friends set path='"+path+"' where user_name='"+f_name+"';";
										stmt.executeUpdate(q10);
										String q5="update "+f_id+"_friends set status='friend' where user_name='"+user_name+"';";
										stmt.executeUpdate(q5);
										q10="update "+f_id+"_friends set path='"+path+"' where user_name='"+user_name+"';";
										
										stmt.executeUpdate(q10);
										output.println("request");
										break;
									}
								}
							
							}
						}
						else{
							System.out.println("new else");
				
							String q8="insert into "+id+"_friends(user_name,status) values('"+f_name+"','requested');";
							stmt.executeUpdate(q8);
							String q9="insert into "+f_id+"_friends(user_name,status) values('"+user_name+"','request');";
							stmt.executeUpdate(q9);
							output.println("request sent");
						}
					}
					else{
						System.out.println("user doesnot exist");
						output.println("doesnot exist");
					}
				
					
				}
				else if(check.equals("total")){
					int id=Integer.parseInt(client.readLine());
					String query="select user_name from "+id+"_friends where status='friend';";
					//System.out.println("hello total");
					ResultSet rs=stmt.executeQuery(query);
					if(rs.next()){
						rs.beforeFirst();
						while(rs.next()){
							output.println(rs.getString(1));
							count++;
						}
					}
					output.println("done");
					output.println(count);
					count=0;
				}
				else if(check.equals("upcoming")){
					String f_name_user=null,user_name=null;
					int f_id=0;

					int id=Integer.parseInt(client.readLine());
					String query="select user_name from "+id+"_friends where status='request';";
				
					ResultSet rs=stmt.executeQuery(query);
			
					while(rs.next()){
						//friends_name.append(rs.getString(1)+"\n");
						output.flush();
						output.println(rs.getString(1));

						System.out.println(rs.getString(1));
					}

					output.println("end");
					
					
				}
				else if(check.equals("upp add")){
					int id=Integer.parseInt(client.readLine());
						String f_name=client.readLine();
						String q1="select id,name from users;";
						ResultSet rs1=stmt.executeQuery(q1);
						String f_name_user=null;
						String user_name=null;
						int f_id=0;
						while(rs1.next()){	
							f_name_user=rs1.getString(2);
							if(f_name.equals(f_name_user)){
								//System.out.println("user exists");
								f_id=rs1.getInt(1);
						
								break;
							}
						}
						String q2="select name from users where id="+id+";";
						ResultSet rs2=stmt.executeQuery(q2);
						while(rs2.next()){
							user_name=rs2.getString(1);
							System.out.println(user_name);
							break;
						}
						String q3="select user_name from "+id+"_friends where status='request';";
				
						ResultSet rs3=stmt.executeQuery(q3);
						String f_name_check=null;
						while(rs3.next()){
							f_name_check=rs3.getString(1);
						}
						System.out.println(f_name);
						System.out.println(f_name_check);
							if(f_name.equals(f_name_check)){
								String path="/home/saurabh/Pictures/"+id+"_"+f_id+"_messenger.txt";
	  							FileOutputStream fout=new FileOutputStream(path);
								String q4="update "+id+"_friends set status='friend' where user_name='"+f_name+"';";
								stmt.executeUpdate(q4);
								String q10="update "+id+"_friends set path='"+path+"' where user_name='"+f_name+"';";
								stmt.executeUpdate(q10);
								String q5="update "+f_id+"_friends set status='friend' where user_name='"+user_name+"';";
								stmt.executeUpdate(q5);
								q10="update "+f_id+"_friends set path='"+path+"' where user_name='"+user_name+"';";
								stmt.executeUpdate(q10);
								output.println("Friend added");
								break;
							}
				}
				else if(check.equals("messenger")){
					int id=Integer.parseInt(client.readLine());
					String query="select user_name from "+id+"_friends where status='friend';";
					ResultSet rs=stmt.executeQuery(query);
					while(rs.next()){
						output.println(rs.getString(1));
					}

					output.println("end");

				}
				else if(check.equals("messengerlist")){
					//String status=client.readLine();
					ResultSet rs;
					String msg=null;
					//if(status.equals("list")){
					int f_id=0;
					String u_name=null;
					String path=null;
					//if (!e.getValueIsAdjusting()){
					int id=Integer.parseInt(client.readLine());
						String f_name=client.readLine();
						try{
							String q1="select id from users where name='"+f_name+"';";
							rs=stmt.executeQuery(q1);
							while(rs.next()){
								f_id=rs.getInt(1);
							}
				
							String q2="select name from users where id="+id+";";
							rs=stmt.executeQuery(q2);
							while(rs.next()){
								u_name=rs.getString(1);
							}

							String q3="select path from "+id+"_friends where user_name='"+f_name+"';";
							rs=stmt.executeQuery(q3);
							while(rs.next()){
								path=rs.getString(1);
							}

							fin=new FileInputStream(path);	

							int i=0;    
			            	while((i=fin.read())!=-1){    
					            //convo.append(String.valueOf((char)i));    
					            msg=String.valueOf((char)i);
					            System.out.print(msg);
					            if(((char)i)=='\n'){
					            	output.println("~");
					            }
					            else
					            	output.println(msg);
				            }   
				             output.println("end");
				            fin.close();  
						}
			
						catch(Exception e3)	{
							System.out.println(e3);
						}
					//}
				//}
				}
				else if(check.equals("messengersend")){
					System.out.println("entered into inbox and sending msg");
					ResultSet rs;
					int f_id=0;
					String u_name=null;
					String path=null;
					int id=Integer.parseInt(client.readLine());
					String f_name=client.readLine();
					//if(e.getSource()==send){
						String msg2=client.readLine();
						String msg1=null;
						try{
							String q1="select id from users where name='"+f_name+"';";
							rs=stmt.executeQuery(q1);
							while(rs.next()){
								f_id=rs.getInt(1);
							}
				
							String q2="select name from users where id="+id+";";
							rs=stmt.executeQuery(q2);
							while(rs.next()){
								u_name=rs.getString(1);
							}

							String q3="select path from "+id+"_friends where user_name='"+f_name+"';";
							rs=stmt.executeQuery(q3);
							while(rs.next()){
								path=rs.getString(1);
							}

							fout=new FileOutputStream(path,true);
							msg2=u_name+":"+msg2+"\n";
							byte b[]=msg2.getBytes();//converting string into byte array    
			             	fout.write(b);   

				            fout.close(); 
				           // convo.setText(null);
				            fin=new FileInputStream(path);	

							int i=0;    
			            	while((i=fin.read())!=-1){    
					            //convo.append(String.valueOf((char)i));    
					            msg1=String.valueOf((char)i);
					            System.out.print(msg1);
					            if(((char)i)=='\n'){
					            	output.println("~");
					            }
					            else
					            	output.println(msg1);
				            }   
				            output.println("end");
				            fin.close();
						}
			
						catch(Exception e3)	{
							System.out.println(e3);
						}
				}
				else if(check.equals("remove")){
					String user_name=null,status=null,f_name_user=null;
					int f_id=0,flag1=0;
					int id=Integer.parseInt(client.readLine());
					String f_name=client.readLine();
					String q1="select id,name from users;";
					ResultSet rs1=stmt.executeQuery(q1);
					while(rs1.next()){
						f_name_user=rs1.getString(2);
						if(f_name.equals(f_name_user)){
							System.out.println("user exists");
							f_id=rs1.getInt(1);
							flag1=1;
							break;
						}
					}
					String q2="select name from users where id="+id+";";
					ResultSet rs2=stmt.executeQuery(q2);
					while(rs2.next()){
						user_name=rs2.getString(1);
						System.out.println(user_name);
						break;
					}

					if(flag1==1){
						String q3="select path from "+id+"_friends where user_name='"+f_name+"';";
						ResultSet rs3=stmt.executeQuery(q3);
						String path=null;
						while(rs3.next())
							path=rs3.getString(1);
						String q4="delete from "+id+"_friends where user_name='"+f_name+"';";
						stmt.executeUpdate(q4);
						String q5="delete from "+f_id+"_friends where user_name='"+user_name+"';";
						stmt.executeUpdate(q5);

						Files.deleteIfExists(Paths.get(path));
					}
					output.println("Friend removed");
				}
				else if(check.equals("profile")){
					
					int id=Integer.parseInt(client.readLine());

					String q1="select * from users where id="+id+";";
					ResultSet rs=stmt.executeQuery(q1);
					String name=null;
					int idg=0;
					String password=null;
					String date=null;
					while(rs.next()){
						name=rs.getString(2);
						output.println(name);
						idg=rs.getInt(1);
						output.println(idg);
						password=rs.getString(4);
						output.println(password);
						date=rs.getString(3);
						output.println(date);
					}
				}
				else if(check.equals("profile image"))
				{
					System.out.println("server profile entered");
					int id = Integer.parseInt(client.readLine());
					System.out.println(id);
					String path=null;
					String qry="select image from users where id="+id+";";
					ResultSet rs=stmt.executeQuery(qry);
					while(rs.next())
					{
						path=rs.getString(1);
					}
					System.out.println(path);
					try
		           	{
		           		System.out.println("entered try");
			           	FileInputStream fin=new FileInputStream(path);
			           	int i;
			           //	while((i=fin.read())>-1)
			           	while(true)
			           	{
			           		
			           		if((i=fin.read())==255)
							{
								output.write(i);
								if((i=fin.read())==217)
								{
									output.write(i);
									break;
								}	
								else
									output.write(i);		
							}
							else
								output.write(i);
			           		//System.out.println(i);
			           	}
			           	//output.flush();
			           	fin.close();
			           	//System.out.println(i);

			           	System.out.println("File Send from server");
			        }
			        catch(Exception e)
			        {
			        	System.out.println(e);
			        }
				}
				else if(check.equals("change dp"))
				{
					int i=0;
					int id = Integer.parseInt(client.readLine());
					String path="/home/saurabh/Pictures/user_image_"+id+".jpg";
					fout=new FileOutputStream(path);
					String qry="update users set image='"+path+"' where id="+id+";";
					stmt.executeUpdate(qry);
					while(true)
					{
						//fout.write(i);
						if((i=client.read())==255)
						{
							fout.write(i);
							if((i=client.read())==217)
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

					System.out.println("Image received");
					fout.close();
				}
				else{}
				System.out.println("time:"+z);
				z++;
			}
		}
		catch(Exception ef){
			System.out.println(ef);
		}
	}
}
