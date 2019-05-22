package com.smartrfid.sportid;


import java.io.IOException;
import java.io.PrintWriter;
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
	FileController fc = new FileController();
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

		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		if (action.equals("savingAct")) {
			try {
				fc.newFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String saving = request.getParameter("saving");
			System.out.println("Saving: "+ saving + " " + timeStr);

			if (saving.equals("true")) {System.out.println("Saving complete");}
		}

		if (action.equals("startRead")) {
			rc.COMConnect();
			System.out.println("Reader connected ");
			out.print("Reader connected<br>");
		}
		
		if (action.equals("stopRead")) {
			rc.TCPDisconnect();
			System.out.println("Reader disconnected ");
			out.print("Reader disconnected<br>");
		}
		
		if (action.equals("resetTags")) {
			rc.resetEPC();
			out.print("Tags reset <br>");
		}
	
		if (action.equals("epcCheckAct")) {
			String fullname = request.getParameter("fullname");
			System.out.println("Command: "+ fullname + " " + timeStr);

			if (fullname.equals("check")) {

				int i = rc.getUniqueTagCount();
				if (i != 0) {
					out.print("<table>	<tr>\r\n" + 
							"		<td>\r\n" + 
							"			<p>№</p>\r\n" + 
							"		</td>\r\n" + 
							"		<td>\r\n" + 
							"			<p>EPC</p>\r\n" + 
							"		</td>\r\n" + 
							"		<td>\r\n" + 
							"			<p>Last seen</p>\r\n" + 
							"		</td>\r\n" + 
							"	</tr>");
					for (int index = 0; index < i; index++) {
						int hi = index + 1;
						out.print("		<td>\r\n" + 
								"			<p>"+ hi +"</p>\r\n" + 
								"		</td>\r\n" + 
								"		<td>\r\n" + 
								"			<p>"+ rc.getEPC(index) + "</p>\r\n" + 
								"		</td>\r\n" + 
								"		<td>\r\n" + 
								"			<p>" + rc.getLastTimestamp(index)+"</p>\r\n" + 
								"		</td>\r\n" + 
								"	</tr>");
					}
					out.print("</table>");
				} else out.print("<table><tr>"
						+ "<td><p>№</p></td>"
						+ "<td><p>EPC</p></td>"
						+ "<td><p>Last seen</p></td>"
						+ "</tr></table> ");
			}
		}
	}
}
