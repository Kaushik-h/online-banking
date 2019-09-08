<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>


<%
try
{
String x=null;
String y=null;
String yes="yes";
String no="no";
int q=0;
Cookie t[]=request.getCookies();
int a=t.length;                      
String tech=t[a-1].getValue();
Class.forName("com.mysql.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb",
"root","root"); 
Statement stmt=con.createStatement();

out.println("<table border='1'><tr><th>from(acco)</th><th>to(accno)</th><th>amt</th><th>type_of_transaction</th></tr>");	
ResultSet rs=stmt.executeQuery("select num,user1,user2,amt,opt from auth where result is NULL and tech="+tech);  

while(rs.next())
{
	
q=rs.getInt(1);
out.println("<tr><td>"+rs.getString(2)+" </td><td> "+rs.getString(3)+" </td><td> "+rs.getInt(4)+"</td><td>"+rs.getString(5)+"</td></tr>");  
out.println("</table>");
%><form action="authtech.jsp">
	<input type="submit" name="accept" value="accept">
	<input type="submit" name="reject" value="reject">
  </form>
<%  
x=request.getParameter("accept");
y=request.getParameter("reject");
if(x!=null)
{
stmt.executeUpdate("update auth set result='y' where num="+q);
}
if(y!=null)
{
stmt.executeUpdate("update auth set result='n' where num="+q);
}





}  





}
catch(Exception e)
{System.out.println(e);}

%>
<form method="post" action="userservlet.jsp">
	
	<input type="submit" value="update">
</form><br><br>
<%

%>