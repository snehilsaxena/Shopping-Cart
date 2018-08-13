import javax.servlet.*;
import java.io.*;
import java.sql.*;
public class Register extends GenericServlet
{
	public void service(ServletRequest request,ServletResponse response)throws ServletException,IOException
	{
	String name,email,pwd,dob;
	response.setContentType("text/html");
	name=request.getParameter("name");
	email=request.getParameter("email");
	pwd=request.getParameter("password");
	dob=request.getParameter("dob");
	PrintWriter out=response.getWriter();
	out.println(
	"<html><body background=\"2.jpg\"><font color=\"white\">You have been Registered with us !!!!!!!</font></body></html>"		
	);
	try
	{
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	Statement s=c.createStatement();
	String query="insert into users values('"+name+"','"+email+"','"+pwd+"','"+dob+"',"+"''"+");";
	s.executeUpdate(query);
	System.out.println("executed");
	}
	catch(Exception e)
	{
	System.out.println(e);
	}
	RequestDispatcher rd=request.getRequestDispatcher("index.html");
	rd.include(request,response);
	}
}

