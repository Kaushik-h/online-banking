import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class userservlet extends HttpServlet
{    
public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
{
try{ 
PrintWriter pw=res.getWriter();
res.setContentType("text/html");
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3307/mydb","root","root");  
Statement stmt=con.createStatement(); 
String s2=req.getParameter("transf");
String s3=req.getParameter("dep");
String s4=req.getParameter("with");

Cookie c[]=req.getCookies();
String user=c[0].getValue();




//moneytransfer

if(s2!=null)
{
int bal=0;
String a1=req.getParameter("uaccno");
int a2=Integer.parseInt(req.getParameter("uamt").toString().trim());
ResultSet rs=stmt.executeQuery("select balance from accdetails where accno="+user);  
if(rs.next())
{bal=rs.getInt(1);}
if(a2>=bal)
{pw.println("Insufficient balance");}

int up1=bal-a2;
int up2=bal+a2;
PreparedStatement ps=con.prepareStatement("update accdetails set balance=? where accno=?");
PreparedStatement ps1=con.prepareStatement("update accdetails set balance=? where accno=?");
ps.setInt(1,up1);
ps.setString(2,user);
ps1.setInt(1,up2);
ps1.setString(2,a1);
int i=ps.executeUpdate();
int j=ps1.executeUpdate();
if(j==1&&i==1)
	pw.println("money transfered");


PreparedStatement stmt1=con.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
stmt1.setString(1,user); 
stmt1.setString(2,user); 
stmt1.setString(3,a1); 
stmt1.setDate(4,new java.sql.Date(System.currentTimeMillis())); 
stmt1.setInt(5,a2); 
stmt1.setInt(6,0); 
stmt1.setInt(7,up1); 
stmt1.executeUpdate();	
stmt1.close();   

PreparedStatement stmt2=con.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
stmt2.setString(1,a1); 
stmt2.setString(2,user); 
stmt2.setString(3,a1); 
stmt2.setDate(4,new java.sql.Date(System.currentTimeMillis())); 
stmt2.setInt(5,0); 
stmt2.setInt(6,a2); 
stmt2.setInt(7,up2); 
stmt2.executeUpdate();	
stmt2.close();

}

//deposit
if(s3!=null)
{
int bal=0;
int a3=Integer.parseInt(req.getParameter("uamtdw").toString().trim());
ResultSet rs=stmt.executeQuery("select balance from accdetails where accno="+user);  
if(rs.next())
{bal=rs.getInt(1);}
int dw=bal+a3;
PreparedStatement ps=con.prepareStatement("update accdetails set balance=? where accno=?");
ps.setInt(1,dw);
ps.setString(2,user);
int j=ps.executeUpdate();
if(j==1)
	{pw.println("Balance updated");}
}



//withdraw
if(s4!=null)
{
int bal=0;
int a3=Integer.parseInt(req.getParameter("uamtdw").toString().trim());
ResultSet rs=stmt.executeQuery("select balance from accdetails where accno="+user);  
if(rs.next())
{bal=rs.getInt(1);}
if(a3>=bal)
{pw.println("Insufficient balance");}

int up=bal-a3;
PreparedStatement ps=con.prepareStatement("update accdetails set balance=? where accno=?");
ps.setInt(1,up);
ps.setString(2,user);
int j=ps.executeUpdate();
if(j==1)
	{pw.println("Balance updated");}
}





}catch(Exception e){System.out.println(e);}
}
}
