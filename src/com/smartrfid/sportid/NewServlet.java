package com.smartrfid.sportid;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/NewServlet")
public class NewServlet extends HttpServlet {
	
	ReaderController rc = new ReaderController();
	Calendar current;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	request.setCharacterEncoding("UTF-8");
	current = Calendar.getInstance();
    current.setTime(new Date());
    String timeStr = String.format("%d:%02d:%02d", current.get(Calendar.HOUR), current.get(Calendar.MINUTE),current.get(Calendar.SECOND));
		
	response.setContentType("text/plain");
	response.setContentType("text/html; charset=utf-8");
	String fullname = request.getParameter("fullname");
	System.out.println("Command: "+ fullname + " " + timeStr);

	PrintWriter out = response.getWriter();
	if (fullname.equals("start")) {
		rc.COMConnect();
		out.print("connected ");
	}
	
	if (fullname.equals("stop")) {
		rc.TCPDisconnect();
	}
	
	if (fullname.equals("reset")) {
		rc.resetEPC();
		out.print("reseted ");
	}
	
	if (fullname.equals("check")) {

	int i = rc.getUniqueTagCount();
		if (i != 0) {
			out.print("<table>	<tr>\r\n" + 
					"		<td>\r\n" + 
					"			<p><strong><em>Number</em></strong></p>\r\n" + 
					"		</td>\r\n" + 
					"		<td>\r\n" + 
					"			<p><strong><em>EPC</em></strong></p>\r\n" + 
					"		</td>\r\n" + 
					"		<td>\r\n" + 
					"			<p><strong><em>Last seen</em></strong></p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>");
			for (int index = 0; index < i; index++) {
				int hi = index + 1;
				out.print("		<td>\r\n" + 
						"			<p><strong>"+ hi +"</strong></p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p><strong>"+ rc.getEPC(index) + "</strong></p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p><strong><em>Last seen</em></strong></p>\r\n" + 
						"		</td>\r\n" + 
						"	</tr>");
			}
			out.print("</table>");
		} else out.print("<table><tr>"
				+ "<td><p>№</p></td>"
				+ "<td><p><strong><em>EPC</em></strong></p></td>"
				+ "<td><p><strong><em>Last seen</em></strong></p></td>"
				+ "</tr></table> ");
		}
	}
}
