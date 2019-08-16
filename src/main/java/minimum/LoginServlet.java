package minimum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/WEB-INF/views/find2min.jsp").forward(request,response);
        }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		PrintWriter out = response.getWriter();
		
		//business logic
				String a=request.getParameter("numbers");
				
				
				int arr[]=new int[a.length()];
				int count=0;
				
				String result = "";
		        for (String tok : a.split(" "))
		        {
		        	arr[count++]=Integer.parseInt(tok);
		        }
		        //for (int i=0;i<count;i++)
		        //	System.out.println(arr[i]);
		        System.out.println(arr.length);
		        System.out.println(count);
		        
				if (arr.length<2)
				{
					System.out.println("INVALID INPUT there should be atleast 2 elements");
					request.setAttribute("msg", "INVALID INPUT MUST HAVE ATLEAST 2 ELEMENTS");
					request.getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request,response);
					System.exit(0);
				}
		        
		        int min1=0;
		        int min2=0;
		        min1=min2=Integer.MAX_VALUE;
		        for (int i=0;i<count;i++)
		        {
		        	if (arr[i]<min1)
		        	{
		        		min2=min1;
		        		min1=arr[i];
		        	}
		        	else if (arr[i]<min2 && arr[i]!= min1)
		        		min2=arr[i];
		        }
		        System.out.println(min2);
		        
		        //Ending of Business Logic
		        String his="";
		        his=his+"<br>";
		        String g1,g2;
		        //
		        try{  
		        	Class.forName("com.mysql.jdbc.Driver");  
		        	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/findmin","root","root");  
		        	Statement stmt=con.createStatement();  
		        	stmt.executeUpdate("insert into store values(\""+a+"\",\""+Integer.toString(min2)+"\")");  
		        	ResultSet rs=stmt.executeQuery("select * from store");  
		        	while(rs.next())  
		        	{
		        		g1=rs.getString(1);
		        		g2=rs.getString(2);
		        		his=his+g1+":"+g2+"<br>";
		        		System.out.println(g1+"  "+g2);
		        	}
		        	con.close();  
		        	
		        	
		        	}
		        catch(Exception e)
		        { 
		        	System.out.println(e);
		        }  
		        	  
		        //
		request.setAttribute("his", his);
		request.setAttribute("nums", a);
        request.setAttribute("min", min2);
		request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request,response);
	}

}