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
	CompetitionController cc = new CompetitionController();
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
		String timeStr = String.format("%d:%02d:%02d", current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE),current.get(Calendar.SECOND));

		response.setContentType("text/plain");
		response.setContentType("text/html; charset=utf-8");

		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		if (action.equals("savingAct")) {
			rc.saveSensList();
			System.out.println("Sens list saved");
		}
		
		if (action.equals("retrieveCompetitors")) {
			//System.out.println("Retrieveing competitors");

			out.write("<table><tr>" +
					"<td><p>Порядковый</p></td>" + 
					"<td><p>Имя</p></td>" + 
					"<td><p>Фаимлия</p></td>\r\n" + 
					"<td><p>Отчество</p></td>\r\n" + 
					"<td><p>Номер</p></td>\r\n" + 
					"<td><p>Год рождения</p></td>\r\n" + 
					"<td><p>Пол</p></td>\r\n" + 
					"<td><p>Метка</p></td>\r\n" + 
					"</tr>\r\n");
			int ci = cc.getCompetitorsCount();
			if (ci != 0) {
				for (int index = 0; index < ci; index++) {
					int hi = index + 1;
					out.print("<tr><td><p>"+ hi +"</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).Name + "</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).Surname +"</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).Patron +"</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).Number +"</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).BYear +"</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).Gender +"</p></td>\r\n" + 
							"<td><p>" + cc.getCompetitor(index).EPC +"</p></td>\r\n" + 
							"</tr>");
				}
			}
			out.print("</table>");

		}
		
		
		if (action.equals("saveCompetitor")) {
			System.out.println("Saving competitor");
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String Pname = request.getParameter("Pname");
			String number = request.getParameter("number");
			String byear = request.getParameter("byear");
			String radioValue = request.getParameter("radioValue");
			String targetEPC = request.getParameter("targetEPC");
			if (name.equals("") || surname.equals("") || Pname.equals("")|| number.equals(""))
				out.print("<span class=\"form-error\"> Заполните все поля </span>");
			else {
				String regex = "[0-9]+";
				if (!number.matches(regex)) out.print("<span class=\"form-error\"> Номер может содержать только цифры </span>");
				else {
					int ci2 = cc.getCompetitorsCount();
					boolean duplicate = false;
					for (int index = 0; index < ci2; index++) {
						if (cc.getCompetitor(index).EPC.contentEquals(targetEPC)) duplicate = true; // toDO: handle compet correction
					}
					if (duplicate == false) { 
						cc.addCompetitor(name, surname, Pname, Integer.parseInt(number), Integer.parseInt(byear), radioValue, targetEPC);
						out.print("<span class=\"form-success\"> Участник зарегистрирован </span>");
					} else out.print("<span class=\"form-error\"> Метка уже зарегистрирована </span>");

				}
			}
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
				out.print("<table><tr>"
						+ "<td><p>№</p></td>"
						+ "<td><p>EPC</p></td>"
						+ "<td><p>Last seen</p></td>"
						+ "</tr>");
				int i = rc.getUniqueTagCount();
				if (i != 0) {
					for (int index = 0; index < i; index++) {
						int hi = index + 1;
						out.print("		<td>\r\n" + 
								"			<p>"+ hi +"</p>\r\n" + 
								"		</td>\r\n" + 
								"		<td>\r\n" + 
								"			<p>" + rc.getEPC(index) + "</p>\r\n" + 
								"		</td>\r\n" + 
								"		<td>\r\n" + 
								"			<p>" + rc.getLastTimestamp(index)+"</p>\r\n" + 
								"		</td>\r\n" + 
								"	</tr>");
					}
				}
				out.print("</table>");
			}
		}
		
		if (action.equals("SensListCheck")) {
			rc.checkExpired();
			//System.out.println("Sens List check " + timeStr);
			int i = rc.getRegListCount();
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
							"			<p ");
					if (index==0) out.print("id = \"targetEPC\"");
					out.print(">"+ rc.getRegEPC(index) + "</p>\r\n" + 
							"		</td>\r\n" + 
							"		<td>\r\n" + 
							"			<p>" + rc.getRegTimestamp(index)+"</p>\r\n" + 
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
