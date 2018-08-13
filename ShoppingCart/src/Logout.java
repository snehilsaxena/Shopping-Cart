import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class Logout extends HttpServlet
{
	Connection c;
	
	public Logout()
	{
	try{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	}catch(Exception e){}
	}
	
	public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		response.setContentType("text/html");
		response.getWriter().println("<font color=\"white\">You Have Been Logged Out Successfully !</font>");
		HttpSession hs=request.getSession();
		String username=(String)hs.getAttribute("name");
		ArrayList a=(ArrayList)hs.getAttribute("cartdetails");
		Iterator it=a.iterator();
		String detail="";
		while(it.hasNext())
		{
		String temp=(String)it.next();
		if(temp!=null)	
		detail=detail+temp+"-";
		}
			try{
		if(detail!=null||!detail.equals(""))
				c.createStatement().executeUpdate("update users set cartdetails='"+detail+"' where Name='"+username+"';");	
		}catch(Exception ee){}
		hs.removeAttribute("cartdetails");
		hs.invalidate();
		request.getRequestDispatcher("index.html").include(request,response);
	}
}