import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class MyCart extends HttpServlet
{
	Connection c;
	ResultSet rs;
	ArrayList al;
	
	public MyCart()
	{
	try{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	c=DriverManager.getConnection("jdbc:odbc:MyDSN");
	}
	catch(Exception e){
	}
	}
	
	public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		request.getRequestDispatcher("menus.html").include(request,response);
		try{
		String itemno=request.getParameter("itemno");
		Statement s=c.createStatement();
		rs=s.executeQuery("select * from Products where ProductId='"+itemno+"';");
		HttpSession hs=request.getSession();
		al=(ArrayList)hs.getAttribute("cartdetails");
		al.add(itemno);
		//
		ResultSet rst=c.createStatement().executeQuery("select * from users where Name='"+hs.getAttribute("name")+"';");
		String text=null;
		while(rst.next())
		text=rst.getString(5);
		if(text!=null)
			text.trim();
		if(text==null||text.equals(""));
		else
		{
			String cart=text;
		while(true&&cart!=null)
		{
		if(!al.contains(cart.substring(0,cart.indexOf("-"))))
			al.add(cart.substring(0,cart.indexOf("-")));
		if(cart.indexOf("-")+1==cart.length())
		break;
		cart=cart.substring(cart.indexOf("-")+1,cart.length());
		}
		}
		//
		hs.setAttribute("cartdetails",al);
		Iterator it=al.iterator();
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
	catch(Exception ee)
	{
		System.out.println("here");
		System.out.println(ee);
	}
	}
}