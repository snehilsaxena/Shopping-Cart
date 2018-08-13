import java.io.*;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;
import java.util.*;
public class Welcome extends HttpServlet{
	Connection c;
	ResultSet rs;
	HttpSession hs;
	ArrayList al;
	
	public Welcome()
	{
	try{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	al=new ArrayList();
	}
	catch(Exception ee){}
	}
	
	public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	String email,pwd;
	email=request.getParameter("name");
	pwd=request.getParameter("password");
	hs=request.getSession();
	if(hs.isNew())
	{
		try{
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	PreparedStatement s=c.prepareStatement("select * from users where Email=? and Password=?");
	s.setString(1,email);
	s.setString(2,pwd);
	ResultSet rs=s.executeQuery();
	if(rs.next())
	{
		String nm=rs.getString(1);
		out.print("Welcome,"+nm);
		out.print("<a href=\"Logout\">Logout</a>");
		request.getRequestDispatcher("home.html").include(request,response);
		hs=request.getSession();
		hs.setAttribute("name",nm);
		hs.setAttribute("cartdetails",new ArrayList());
	}
	else
	{
	out.println("<b><font color=\"white\">Invalid User or Password</font></b>");	
	RequestDispatcher rd=request.getRequestDispatcher("index.html");
	rd.include(request,response);
	hs.invalidate();
	hs=null;
	}
	}
	catch(Exception e){

	}
	}
	else
	{
		hs=request.getSession();
		out.print("Welcome,"+hs.getAttribute("name"));
		out.print("<a href=\"Logout\">Logout</a>");
		request.getRequestDispatcher("home.html").include(request,response);
	}
	
	}
}
