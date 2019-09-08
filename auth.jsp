<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>


<%
String result=null;
String option=null;
String user2=null;
int amt=0;
String s2=request.getParameter("transf");
String s3=request.getParameter("dep");
String s4=request.getParameter("with");
Class.forName("com.mysql.jdbc.Driver"); 
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydb",
"root","root"); 
Statement stmt=con.createStatement();

Cookie c[]=request.getCookies();
int a=c.length;                      
String user1=c[a-1].getValue();





String br=null;
String tech=null;



ResultSet rss=stmt.executeQuery("select branch from accdetails where accno="+user1);  
if(rss.next()) 
{
	br=rss.getString(1);
} 
ResultSet rs1=stmt.executeQuery("select tid from techdetails where branch='"+br+"' order by rand() limit 1");
if(rs1.next()) 
{
	tech=rs1.getString(1);
} 





if(s2!=null)
{	
	user2=request.getParameter("uaccno");
	if(user2.equals(user1))
		{out.println("Can't transfer to same account");}
else
{
option="transfer";	
int bal=0;
amt=Integer.parseInt(request.getParameter("uamt").toString().trim());
ResultSet rs=stmt.executeQuery("select balance from accdetails where accno="+user1);  
if(rs.next())
{bal=rs.getInt(1);}
if(amt>=bal)
{
	result="n";
	out.println("Insufficient balance");
}
else
{
	out.println("Request sent");
}
}

}



if(s3!=null)
{
option="deposit";	
amt=Integer.parseInt(request.getParameter("uamtdw").toString().trim());
out.println("Request sent");

}




if(s4!=null)
{
option="withdraw";	
int bal=0;
amt=Integer.parseInt(request.getParameter("uamtdw").toString().trim());
ResultSet rs=stmt.executeQuery("select balance from accdetails where accno="+user1);  
if(rs.next())
{bal=rs.getInt(1);}
if(amt>=bal)
{
	result="n";
	out.println("Insufficient balance");
}
else
{
	out.println("Request sent");
}
}

if(option!=null)
	{
PreparedStatement stmt1=con.prepareStatement("insert into auth values(null,?,?,?,?,?,?)");
stmt1.setString(1,user1); 
stmt1.setString(2,user2); 
stmt1.setInt(3,amt); 
stmt1.setString(4,option); 
stmt1.setString(5,result); 
stmt1.setString(6,tech); 
stmt1.executeUpdate();	
stmt1.close();   
	}


%>