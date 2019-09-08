import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class registertech extends HttpServlet
{    
public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
{
try{  
PrintWriter pw=res.getWriter();
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3307/mydb","root","root");  
PreparedStatement stmt1=con.prepareStatement("insert into techdetails values(?,?,?,?,?)");    
PreparedStatement stmt2=con.prepareStatement("insert into techlogin values(?,?)");    
stmt1.setString(1,req.getParameter("tid")); 
stmt1.setString(2,req.getParameter("name")); 
stmt1.setString(3,req.getParameter("email")); 
stmt1.setString(4,req.getParameter("phno")); 
stmt1.setString(5,req.getParameter("branch")); 
int i=stmt1.executeUpdate();	
stmt1.close();
  

stmt2.setString(1,req.getParameter("tid")); 
stmt2.setString(2,req.getParameter("pass")); 
int j=stmt2.executeUpdate();	
stmt2.close();

if(i==1&&j==1)
	{pw.println("Account Created");}
else
	{pw.println("Error");}

con.close();
}catch(Exception e){ System.out.println(e);}  
}  
}