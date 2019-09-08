<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>


<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
<form action="chat.jsp">
<input type="textarea" name="msg" required>
<input type="submit" name="user" value="Send"></form>
<form><input type="submit" name="clear" value="clear">
</form><br><br><br>
<%
String str1 = request.getParameter("user"); 
String str2 = request.getParameter("clear"); 

Class.forName("com.mysql.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb",
"root","root"); 
Statement stmt=con.createStatement();


String mytext = request.getParameter("msg"); 
Cookie c[]=request.getCookies();
int a=c.length;                      
String user=c[a-1].getValue();
String sender=null;
String br=null;
String rec=null;
 


ResultSet rs=stmt.executeQuery("select userid from accdetails where accno="+user);  
if(rs.next()) 
{
	sender=rs.getString(1);
} 
ResultSet rss=stmt.executeQuery("select branch from accdetails where userid="+sender);  
if(rss.next()) 
{
	br=rss.getString(1);
} 
ResultSet rs1=stmt.executeQuery("select tid from techdetails where branch='"+br+"' order by rand() limit 1");
if(rs1.next()) 
{
	rec=rs1.getString(1);
} 


//out.println(sender+rec);
//stmt.executeUpdate("delete from chat where sender="+sender);

ResultSet ms=stmt.executeQuery("select msg from chat where reciever='"+sender+"'");
while(ms.next()) 
{
	if(ms.getString(1)!=null)
	out.println(ms.getString(1));%>
	<br>
	<%
} 

if(str1!=null)
{
if(mytext!=null)
	{
PreparedStatement stmt1=con.prepareStatement("insert into chat values(?,?,?)");    
stmt1.setString(1,sender);
stmt1.setString(2,rec);
stmt1.setString(3,"user:"+mytext);
stmt1.executeUpdate();	
stmt1.close();}


ResultSet ms1=stmt.executeQuery("select msg from chat where sender=" +sender);
while(ms1.next()) 
{
	if(ms1.getString(1)!=null)
	out.println(ms1.getString(1));%>
	<br>
	<%
} 
 
} 

if(str2!=null)
{

stmt.executeUpdate("delete from chat where sender='"+sender+"' or reciever='"+sender+"'");
stmt.close();
}
 

%>
</body>

</html>




