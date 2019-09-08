<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>


<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>
<form action="chattech.jsp">
<input type="textarea" name="reply" required>
<input type="submit" name="user">
</form><br><br><br>
<%
String text = request.getParameter("reply"); 
Cookie t[]=request.getCookies();
int a=t.length;                      
String user=t[a-1].getValue();
String sender=user;
Class.forName("com.mysql.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb",
"root","root"); 
Statement stmt=con.createStatement(); 
ArrayList<String> list=new ArrayList<String>();

ResultSet rs=stmt.executeQuery("select sender from chat where reciever='"+sender+"'");
while(rs.next())
{
list.add(rs.getString(1));
}

ResultSet ms=stmt.executeQuery("select msg from chat where reciever='"+sender+"'");
while(ms.next()) 
{
	out.println(ms.getString(1));%>
	<br>
	<%
}

for(String obj:list)
{
if(text!=null)
	{
PreparedStatement stmt1=con.prepareStatement("insert into chat values(?,?,?)");    
stmt1.setString(1,sender);
stmt1.setString(2,obj);
stmt1.setString(3,"technician:"+text);
stmt1.executeUpdate();	
stmt1.close();}

}


ResultSet ms1=stmt.executeQuery("select msg from chat where sender='"+sender+"'");
while(ms1.next()) 
{
	out.println(ms1.getString(1));%>
	<br>
	<%
}

 //for (String num : list) { 		      
           		
   //   }
/*stmt.executeUpdate("delete from chat where sender="+sender);



if(mytext!=null)
	{
PreparedStatement stmt1=con.prepareStatement("insert into chat values(?,?,?)");    
stmt1.setString(1,sender);
stmt1.setString(2,rec);
stmt1.setString(3,mytext);
stmt1.executeUpdate();	
stmt1.close();}


ResultSet ms=stmt.executeQuery("select msg from chat where sender='"+sender+"' or reciever='"+sender+"'");
while(ms.next()) 
{
	if(ms.getString(1)!=null)
	out.println(ms.getString(1));%>
	<br>
	<%
} 

  */
 

%>
</body>

</html>




