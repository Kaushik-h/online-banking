import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class registeruser extends HttpServlet
{    
public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
{
try{  
PrintWriter pw=res.getWriter();
Class.forName("com.mysql.jdbc.Driver"); 
String acn=req.getParameter("accno");
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3307/mydb","root","root");  
PreparedStatement stmt1=con.prepareStatement("insert into userdetails values(?,?,?,?,?)");    
PreparedStatement stmt2=con.prepareStatement("insert into accdetails values(?,?,?,?)");    
PreparedStatement stmt3=con.prepareStatement("insert into userlogin values(?,?)");  


stmt1.setString(1,req.getParameter("uid")); 
stmt1.setString(2,req.getParameter("name")); 
stmt1.setString(3,req.getParameter("email")); 
stmt1.setString(4,req.getParameter("phno")); 
stmt1.setString(5,req.getParameter("address")); 
int i=stmt1.executeUpdate();	
stmt1.close();

stmt2.setString(1,acn); 
stmt2.setString(2,req.getParameter("uid")); 
stmt2.setInt(3,5000);
stmt2.setString(4,req.getParameter("branch")); 
int j=stmt2.executeUpdate();	
stmt2.close();  

stmt3.setString(1,acn); 
stmt3.setString(2,req.getParameter("pass"));
int k=stmt3.executeUpdate();	
stmt3.close();



if(i==1&&j==1&&k==1)
	{pw.println("Account created");}
else
	{pw.println("Error");}

con.close();

}catch(Exception e){ System.out.println(e);}  
}  
}