import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class manageadmin extends HttpServlet
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
String s1=req.getParameter("uaccno");
String s2=req.getParameter("vuser"); 
String s3=req.getParameter("vusera"); 
String s4=req.getParameter("deluser"); 
String s5=req.getParameter("vusertran"); 
String s6=req.getParameter("techid"); 
String s7=req.getParameter("vtech"); 
String s8=req.getParameter("vtecha"); 
String s9=req.getParameter("deltech");
String s10=req.getParameter("vusertran");
String uid=null;


if(s2!=null)
{
pw.println("<table border='1'><tr><th>account no</th><th>user id</th><th>balance</th><th>branch</th></tr>");	
ResultSet rs=stmt.executeQuery("select * from accdetails where accno=" +s1);  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getInt(3)+"</td><td>"+rs.getString(4)+"</td></tr>");  
}
pw.println("</table>");

con.close();  
}

if(s3!=null)
{
pw.println("<table border='1'><tr><th>account no</th><th>user id</th><th>balance</th><th>branch</th></tr>");	
ResultSet rs=stmt.executeQuery("select * from accdetails");  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getInt(3)+"</td><td>"+rs.getString(4)+"</td></tr>");  
}
pw.println("</table>");

con.close();  
}

if(s4!=null)
{

ResultSet rs=stmt.executeQuery("select userid from accdetails where accno="+s1);  
if(rs.next()) 
{
	uid=rs.getString(1).toString();
	rs.close();
} 	

PreparedStatement ps2=con.prepareStatement("delete from userdetails where userid=" +uid);
PreparedStatement ps=con.prepareStatement("delete from accdetails where accno=" +s1);
PreparedStatement ps1=con.prepareStatement("delete from userlogin where accno=" +s1);
int i=ps.executeUpdate();
int j=ps1.executeUpdate();
int k=ps2.executeUpdate(); 
if(i==1&&j==1&&k==1)
	pw.println("row deleted");
}

if(s7!=null)
{
pw.println("<table border='1'><tr><th>technician id</th><th>name</th><th>email</th><th>phno</th><th>branch</th></tr>");	
ResultSet rs=stmt.executeQuery("select * from techdetails where tid=" +s6);  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");  
}
pw.println("</table>");

con.close();  
}



if(s8!=null)
{
pw.println("<table border='1'><tr><th>tehnician id</th><th>name</th><th>email</th><th>phno</th><th>branch</th></tr>");	
ResultSet rs=stmt.executeQuery("select * from techdetails");  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");  
}
pw.println("</table>");
con.close();  
}



if(s9!=null)
{
PreparedStatement ps=con.prepareStatement("delete from techdetails where tid=" +s6);
PreparedStatement ps1=con.prepareStatement("delete from techlogin where tid=" +s6);
int i=ps.executeUpdate();
int j=ps1.executeUpdate();
if(i==1&&j==1)
	pw.println("row deleted");
}


if(s10!=null)
{
pw.println("<table border='1'><tr><th>account no</th><th>from</th><th>to</th><th>date</th><th>amount withdrawn</th><th>amount deposited</th><th>balance</th></tr>");	
ResultSet rs=stmt.executeQuery("select * from transaction where accno=" +s1);  
while(rs.next())  
{
pw.println("<tr><td>"+rs.getString(1)+" </td><td> "+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getDate(4)+"</td><td>"+rs.getInt(5)+"</td><td>"+rs.getInt(6)+"</td><td>"+rs.getInt(7)+"</td></tr>");  
}
pw.println("</table>");
con.close();  
}


}catch(Exception e){ System.out.println(e); 
}



}  
}

 
