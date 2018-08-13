import javax.servlet.*;
import java.io.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
public class EditCart extends HttpServlet
{
	Connection c;
	
	public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		response.setContentType("text/html");
		String itemno=request.getParameter("itemno");
		PrintWriter out=response.getWriter();
		HttpSession hs=request.getSession();
		ArrayList al=(ArrayList)hs.getAttribute("cartdetails");
		al.remove(itemno);
		hs.setAttribute("cartdetails",al);
		//
	try{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		c=DriverManager.getConnection("jdbc:odbc:MyDSN");
		ResultSet res=c.createStatement().executeQuery("select * from users where Name='"+hs.getAttribute("name")+"';");
		String cart=null;
		if(res.next())
		cart=res.getString(5);
		int i=-1;
		if(cart!=null&&!(cart.trim().equals("")))
		i=cart.indexOf(itemno);
		if(i==-1);
		else
		{cart=cart.substring(0,i)+cart.substring(i+itemno.length()+1,cart.length());
		c.createStatement().executeUpdate("update users set cartdetails='"+cart+"' where Name='"+hs.getAttribute("name")+"';");}
		//
		request.getRequestDispatcher("menus.html").include(request,response);
			Iterator it=((ArrayList)hs.getAttribute("cartdetails")).iterator();
		while(it.hasNext())
		{
			String query="select * from Products where ProductId='"+(String)it.next()+"';";
			ResultSet rr=c.createStatement().executeQuery(query);
			while(rr.next()){
			out.println(
					"<table><tr><td rowspan=\"6\"><img src="+rr.getString(6)+" width=\"147px\" height=\"127px\"></td>"+
					"<td><font size=3><b>"+rr.getString(1)+"</b></font></td></tr>"+
					"<tr><td><font size=2><i>"+rr.getString(3)+"</i></font></td></tr>"+
					"<tr><td><font size=2><b>"+rr.getString(2)+"/- Rs.</b></font></td></tr>"
					);
					if(new Float(rr.getString(4)).floatValue()>0)
					out.println("<tr><td><font color=\"green\"><u>In Stock</u></font></td></tr>");
					else
					out.println("<tr><td><font color=\"red\"><u>Out Of Stock</u></font></td></tr>");
					out.println("<tr><td><a href=\"EditCart?itemno="+rr.getString(7)+"\"><font color=\"red\">Remove From Cart</font></a></td></tr>");
					out.println("<tr><td><a href=\"#\">Buy Now</a></td></tr></table><hr>");
		}}

	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	}
}
