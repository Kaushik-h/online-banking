import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class update extends HttpServlet
{    
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
{
try
{ 

PrintWriter out=response.getWriter();
response.setContentType("text/html");
response.setIntHeader("Refresh",4);
 //response.setIntHeader("Refresh", 60);
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3307/mydb","root","root");  
Statement stmt=con.createStatement(); 


/*Cookie c[]=request.getCookies();
int a=c.length;                      
String user=c[a-1].getValue();*/
String a1=null;//rec accno
int a2=0;//amount
String option=null;
String user=null;




ResultSet rs=stmt.executeQuery("select user1,user2,amt,opt from auth where result='y' limit 1");
if(rs.next())
{
user=rs.getString(1).toString();
a1=rs.getString(2).toString();
a2=rs.getInt(3);
option=rs.getString(4).toString();

out.println(user+a1+a2+option);



int bal=0;
ResultSet rs1=stmt.executeQuery("select balance from accdetails where accno="+user);  
if(rs1.next())
{bal=rs1.getInt(1);}




//moneytransfer

if(option.equals("transfer"))
{

if(a2<=bal)
{

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

}
}








