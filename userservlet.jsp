<%@page import="java.sql.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>


<%

try
{ 

//PrintWriter out=response.getWriter();
//response.setContentType("text/html");
response.setIntHeader("Refresh",1);
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3307/mydb","root","root");  
Statement stmt=con.createStatement(); 


/*Cookie c[]=request.getCookies();
int a=c.length;                      
String user=c[a-1].getValue();*/
String a1=null;//rec accno
String temp=null;//rec accno
int a2=0;//amount
String option=null;
String user=null;
String empty="-";


stmt.executeUpdate("delete from auth where result='n'");
ResultSet ms=stmt.executeQuery("select * from auth");
if(!ms.next())
{
stmt.executeUpdate("alter table auth auto_increment=1");
ms.close();
}




ResultSet rs=stmt.executeQuery("select user1,user2,amt,opt from auth where result='y' limit 1");
while(rs.next())
{
user=rs.getString(1).toString();
temp=rs.getString(2);
a2=rs.getInt(3);
option=rs.getString(4).toString();





int bal=0;
ResultSet rs1=stmt.executeQuery("select balance from accdetails where accno="+user);  
if(rs1.next())
{bal=rs1.getInt(1);}




//moneytransfer

if(option.equals("transfer"))
{

if(a2<=bal)
{

a1=temp.toString();
int up1=bal-a2;
int up2=bal+a2;
PreparedStatement ps=con.prepareStatement("update accdetails set balance=? where accno=?");
PreparedStatement ps1=con.prepareStatement("update accdetails set balance=? where accno=?");
ps.setInt(1,up1);
ps.setString(2,user);
ps1.setInt(1,up2);
ps1.setString(2,a1);
int i=ps.executeUpdate();
int j=ps1.executeUpdate();
if(j==1&&i==1)
	out.println("money transfered");


PreparedStatement stmt1=con.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
stmt1.setString(1,user); 
stmt1.setString(2,user); 
stmt1.setString(3,a1); 
stmt1.setDate(4,new java.sql.Date(System.currentTimeMillis())); 
stmt1.setInt(5,a2); 
stmt1.setInt(6,0); 
stmt1.setInt(7,up1); 
stmt1.executeUpdate();	
stmt1.close();   

PreparedStatement stmt2=con.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
stmt2.setString(1,a1); 
stmt2.setString(2,user); 
stmt2.setString(3,a1); 
stmt2.setDate(4,new java.sql.Date(System.currentTimeMillis())); 
stmt2.setInt(5,0); 
stmt2.setInt(6,a2); 
stmt2.setInt(7,up2); 
stmt2.executeUpdate();	
stmt2.close();

}
else
{out.println("Insufficient balance");}

}


//deposit
if(option.equals("withdraw"))
{
int dw=bal+a2;
PreparedStatement ps=con.prepareStatement("update accdetails set balance=? where accno=?");
ps.setInt(1,dw);
ps.setString(2,user);
int j=ps.executeUpdate();
if(j==1)
	{out.println("Balance updated");}

PreparedStatement stmt5=con.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
stmt5.setString(1,user); 
stmt5.setString(2,empty); 
stmt5.setString(3,empty); 
stmt5.setDate(4,new java.sql.Date(System.currentTimeMillis())); 
stmt5.setInt(5,0); 
stmt5.setInt(6,a2); 
stmt5.setInt(7,dw); 
stmt5.executeUpdate();	
stmt5.close();   

}



//withdraw
if(option.equals("deposit"))
{
if(a2<=bal)
{

int up=bal-a2;
PreparedStatement ps=con.prepareStatement("update accdetails set balance=? where accno=?");
ps.setInt(1,up);
ps.setString(2,user);
int j=ps.executeUpdate();
if(j==1)
	{out.println("Balance updated");}

PreparedStatement stmt6=con.prepareStatement("insert into transaction values(?,?,?,?,?,?,?)");
stmt6.setString(1,user); 
stmt6.setString(2,empty); 
stmt6.setString(3,empty); 
stmt6.setDate(4,new java.sql.Date(System.currentTimeMillis())); 
stmt6.setInt(5,a2); 
stmt6.setInt(6,0); 
stmt6.setInt(7,up); 
stmt6.executeUpdate();	
stmt6.close();  



}

else
{out.println("Insufficient balance");}

}

stmt.executeUpdate("delete from auth where result='y' and user1='"+user+"' limit 1");
rs.close();
}//if(rs.next())

}//try

catch(Exception e)
{System.out.println(e);}


%>