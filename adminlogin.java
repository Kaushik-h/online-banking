import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class adminlogin extends HttpServlet
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
String s1=req.getParameter("user3");
String s2=req.getParameter("pass3"); 
String s3=req.getParameter("userl"); 
String s4=req.getParameter("techl"); 
String s5=req.getParameter("adminl"); 

if(s3!=null)
{
ResultSet rs=stmt.executeQuery("select * from userlogin where accno=" +s1+" and pass='"+s2+"'");  
if(rs.next())
{
Cookie c=new Cookie("id",s1);
c.setMaxAge(1800);
c.setPath("localhost:8089/userservlet");
c.setPath("localhost:8089/userdet");
c.setPath("localhost:8089/last/chat.jsp");
c.setPath("localhost:8089/last/auth.jsp");
c.setPath("localhost:8089/last/userservlet.jsp");
res.addCookie(c);	
res.sendRedirect("user.html"); 
}
else
res.sendRedirect("index.html");
con.close();  
}

if(s4!=null)
{
ResultSet rs=stmt.executeQuery("select * from techlogin where tid=" +s1+" and pass='"+s2+"'");  
if(rs.next())
{
Cookie t=new Cookie("id",s1);
t.setMaxAge(1800);
t.setPath("localhost:8089/last/chattech.jsp");
t.setPath("localhost:8089/last/authtech.jsp");
t.setPath("localhost:8089/last/managetech.jsp");
res.addCookie(t);
res.sendRedirect("technician.html"); 

}
else
res.sendRedirect("index.html");
con.close();  
}

if(s5!=null)
{
ResultSet rs=stmt.executeQuery("select * from adminlogin where adminid=" +s1+" and pass='"+s2+"'");  
if(rs.next())
res.sendRedirect("admin.html"); 
else
res.sendRedirect("index.html");
con.close();  
}

}catch(Exception e){ System.out.println(e); 
}



}  
}

 
