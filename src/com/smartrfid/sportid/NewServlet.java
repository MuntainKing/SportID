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


@WebServlet("/NewServlet")
public class NewServlet extends HttpServlet {

	ReaderController rc = new ReaderController();
	CompetitionController cc = new CompetitionController();
	Calendar current;


	private static final long serialVersionUID = 1L;
	

	public NewServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


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
			out.close();
		}
		
		if (action.equals("retrieveCompetitors")) {
			//System.out.println("Retrieveing competitors");
			if (rc.isConnected()) {
			out.write("{\"sus\":\"/SportID/images/onlineIndicator.png\",");
			System.out.println("online");}
			else out.write("{\"sus\":\"/SportID/images/offlineINdicator.png\",");
			
			out.write("\"competitors\":\"<table><tr>" +
					"<td><p>Порядковый</p></td>" + 
					"<td><p>Имя</p></td>" + 
					"<td><p>Фаимлия</p></td>" + 
					"<td><p>Отчество</p></td>" + 
					"<td><p>Номер</p></td>" + 
					"<td><p>Год рождения</p></td>" + 
					"<td><p>Пол</p></td>" + 
					"<td><p>Метка</p></td>" + 
					"</tr>");
			int ci = cc.getCompetitorsCount();
			if (ci != 0) {
				for (int index = 0; index < ci; index++) {
					int hi = index + 1;
					out.print("<tr><td><p>"+ hi +"</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).Name + "</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).Surname +"</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).Patron +"</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).Number +"</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).BYear +"</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).Gender +"</p></td>" + 
							"<td><p>" + cc.getCompetitor(index).EPC +"</p></td>" + 
							"</tr>");
				}
			}
			out.print("</table>\"}");
			out.close();
		}
		
		if (action.equals("saveList")) {
			String listName = request.getParameter("listname");
			out.print(cc.saveCompetitors(listName));
			out.close();
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
			out.close();
		}

		if (action.equals("startRead")) {
			rc.COMConnect();
			System.out.println("Reader connected ");
			out.print("Reader connected<br>");
			out.close();
		}
		
		if (action.equals("stopRead")) {
			rc.TCPDisconnect();
			System.out.println("Reader disconnected ");
			out.print("Reader disconnected<br>");
			out.close();
		}
		
		if (action.equals("resetTags")) {
			rc.resetEPC();
			out.print("Tags reset <br>");
			out.close();
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
			out.close();
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
			out.close();
		}
	}
}
