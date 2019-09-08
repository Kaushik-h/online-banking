<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>


<%
Cookie t[]=request.getCookies();
int a=t.length;                      
String user=t[a-1].getValue();
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3307/mydb","root","root");  
Statement stmt=con.createStatement(); 
String s1=request.getParameter("uaccno");
String s2=request.getParameter("vuser"); 
String s3=request.getParameter("vusera"); 
String s4=request.getParameter("deluser"); 
String s5=request.getParameter("vusertran");
String ubr=null;
String tbr=null;
String uid=null;


ResultSet rs=stmt.executeQuery("select branch from techdetails where tid="+user);  
if(rs.next()) 
{
	tbr=rs.getString(1).toString();
} 

ResultSet rss=stmt.executeQuery("select branch from accdetails where accno="+s1);  
if(rss.next()) 
{
	ubr=rss.getString(1).toString();
} 


if(s3==null)
{
if(ubr.equals(tbr))
{

if(s2!=null)//acc details
{
out.println("<table border='1'><tr><th>account no</th><th>user id</th><th>balance</th><th>branch</th></tr>");	
ResultSet rs1=stmt.executeQuery("select * from accdetails where accno=" +s1);  
while(rs1.next())  
{
out.println("<tr><td>"+rs1.getString(1)+" </td><td> "+rs1.getString(2)+"</td><td>"+rs1.getInt(3)+"</td><td>"+rs1.getString(4)+"</td></tr>");  
}
out.println("</table>");
}

if(s4!=null)//delete acc
{

ResultSet rs2=stmt.executeQuery("select userid from accdetails where accno="+s1);  
if(rs2.next()) 
{
	uid=rs2.getString(1).toString();
	rs2.close();
} 	

PreparedStatement ps2=con.prepareStatement("delete from userdetails where userid=" +uid);
PreparedStatement ps=con.prepareStatement("delete from accdetails where accno=" +s1);
PreparedStatement ps1=con.prepareStatement("delete from userlogin where accno=" +s1);
int i=ps.executeUpdate();
int j=ps1.executeUpdate();
int k=ps2.executeUpdate(); 
if(i==1&&j==1&&k==1)
	out.println("row deleted");
}

if(s5!=null)
{
out.println("<table border='1'><tr><th>account no</th><th>from</th><th>to</th><th>date</th><th>amount withdrawn</th><th>amount deposited</th><th>balance</th></tr>");	
ResultSet rs3=stmt.executeQuery("select * from transaction where accno=" +s1);  
while(rs3.next())  
{
out.println("<tr><td>"+rs3.getString(1)+" </td><td> "+rs3.getString(2)+"</td><td>"+rs3.getString(3)+"</td><td>"+rs3.getDate(4)+"</td><td>"+rs3.getInt(5)+"</td><td>"+rs3.getInt(6)+"</td><td>"+rs3.getInt(7)+"</td></tr>");  
}
out.println("</table>");
con.close();  
}

}

else
{out.println("Access denied");}

}//if s3==null

if(s3!=null)
{

	out.println("<table border='1'><tr><th>account no</th><th>user id</th><th>balance</th><th>branch</th></tr>");	
	ResultSet rsz=stmt.executeQuery("select * from accdetails where branch='"+tbr+"'");  
	while(rsz.next())  
	{
	out.println("<tr><td>"+rsz.getString(1)+" </td><td> "+rsz.getString(2)+"</td><td>"+rsz.getInt(3)+"</td><td>"+rsz.getString(4)+"</td></tr>");  
	}
	out.println("</table>");
	
}



%>