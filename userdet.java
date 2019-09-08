import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class userdet extends HttpServlet
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
String s1=req.getParameter("vdet");
String s3=req.getParameter("vtran");
String s4=req.getParameter("vbal");


Cookie c[]=req.getCookies();
int a=c.length;                      
String user=c[a-1].getValue();



//userdetails
if(s1!=null)
{
pw.println("<table border='1'><tr><th>userid</th><th>name</th><th>email</th><th>phoneno</th><th>address</th></tr>");	
ResultSet rs=stmt.executeQuery("select * from userdetails where userid=(select userid from accdetails where accno="+user+");");  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");  
}
pw.println("</table>");


con.close();  
}


if(s3!=null)
{
pw.println("<table border='1'><tr><th>sentfrom</th><th>sentto</th><th>date</th><th>amt.withdrawn</th><th>amt.deposited</th><th>balance</th></tr>");	
ResultSet rs=stmt.executeQuery("select sentfrom,sentto,date,withd,dep,bal from transaction where accno="+user);  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getDate(3)+"</td><td>"+rs.getInt(4)+"</td><td>"+rs.getInt(5)+"</td><td>"+rs.getInt(6)+"</td></tr>");  
}
pw.println("</table>");
con.close();  
}

//balance
if(s4!=null)
{
ResultSet rs=stmt.executeQuery("select balance from accdetails where accno="+user);  
if(rs.next())  
{
	pw.println("Your balance is "+rs.getInt(1));
}
con.close();  
}





}catch(Exception e){System.out.println(e);}
}
}