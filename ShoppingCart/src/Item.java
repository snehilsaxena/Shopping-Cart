import java.io.*;
import javax.servlet.*;
import java.sql.*;
public class Item extends GenericServlet{
	Connection c;
	ResultSet rs;
	String itemno;
	
	public Item()
	{
	try{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	}
	catch(Exception ee){}
	}
	
	public void service(ServletRequest request,ServletResponse response)throws ServletException,IOException
	{
	response.setContentType("text/html");
	PrintWriter out=response.getWriter();
	try{
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	request.getRequestDispatcher("menus.html").include(request,response);
		Statement st=c.createStatement();
		rs=st.executeQuery("select * from Products where Category='"+(String)request.getParameter("itemname")+"';");
		while(rs.next())
		{
		itemno=(String)rs.getString(7);
		out.println(
		"<table><tr><td rowspan=\"5\"><img src="+rs.getString(6)+" width=\"147px\" height=\"127px\"></td>"+
		"<td><font size=3><b>"+rs.getString(1)+"</b></font></td></tr>"+
		"<tr><td><font size=2><i>"+rs.getString(3)+"</i></font></td></tr>"+
		"<tr><td><font size=2><b>"+rs.getString(2)+"/- Rs.</b></font></td></tr>"
		);
		if(new Float(rs.getString(4)).floatValue()>0)
		out.println("<tr><td><font color=\"green\"><u>In Stock</u></font></td></tr>");
		else
		out.println("<tr><td><font color=\"red\"><u>Out Of Stock</u></font></td></tr>");
		out.println("<tr><td><a href=\"MyCart?itemno="+itemno+"\">Add To Cart</a></table><hr></td></tr>");
		}
	}
	catch(Exception e){
	System.out.println(e);
	}
	}
}
