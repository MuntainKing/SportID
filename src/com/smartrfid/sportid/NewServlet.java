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
public class NewServlet extends HttpServlet{

	ReaderController rc = new ReaderController();
	CompetitionController cc = new CompetitionController();
	FileController fc = new FileController();
	Calendar current;
    String sessionID;
    String LastCheckedEPC = "";
    int LastSuggestedNum = -1;
    
    
	private static final long serialVersionUID = 1L;
	
	
	public NewServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.response.getWriter().append("Served at: ").append(request.getContextPath());
		String currSessionID = request.getSession().getId();
		if (! currSessionID.equals(sessionID)) {
		//request.setAttribute("ReadCtr", rc);
		request.getSession().setAttribute("ReadCtr", rc);
		//this.getServletConfig().getServletContext().setAttribute("ReadCtr", rc);
		request.getRequestDispatcher("/ContestSrv").forward(request, response);
		sessionID = currSessionID;
		}
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
		
		//Сохранение чувствительного списка меток - Tag
		if (action.equals("savingAct")) {
			rc.saveSensList();
			System.out.println("Sens list saved");
			out.close();
		}
		
		//Обновление индикатора соединения с ридером - Tag, Register, Contest
		if (action.equals("readStatus")) {
			if (rc.isConnected()) 
				out.write("/SportID/images/onlineIndicator.png");
			else out.write("/SportID/images/offlineINdicator.png");
			out.close();
		}
		
		//Вывести в виде таблицы текущий список зарегистрированных участников -Register
		if (action.equals("retrieveCompetitors")) {
			//System.out.println("Retrieveing competitors");
			out.print("<div class=\"table-responsive\"><table class=\"table\"><thead class=\" text-primary\">");		
			out.write("<th>№</td>" + 
					"<th>Имя</th>" + 
					"<th>Фаимлия</th>" + 
					"<th>Отчество</th>" + 
					"<th>Номер</th>" + 
					"<th>Год рождения</th>" + 
					"<th>Пол</th>" + 
					"<th>Метка</th></thead><tbody>");
			int ci = cc.getCompetitorsCount();
			if (ci != 0) {
				for (int index = 0; index < ci; index++) {
					int hi = index + 1;
					out.print("<tr><td>"+ hi +"</td>" + 
							"<td>" + cc.getCompetitor(index).Name + "</td>" + 
							"<td>" + cc.getCompetitor(index).Surname +"</td>" + 
							"<td>" + cc.getCompetitor(index).Patron +"</td>" + 
							"<td>" + cc.getCompetitor(index).Number +"</td>" + 
							"<td>" + cc.getCompetitor(index).BYear +"</td>" + 
							"<td>" + cc.getCompetitor(index).Gender +"</td>" + 
							"<td>" + cc.getCompetitor(index).EPC +"</td>" + 
							"</tr>");
				}
			}
			out.print("</tbody></table></div>");
			out.close();
		}
		
		//Сохранить список участников в файл - Register
		if (action.equals("saveList")) {
			String listName = request.getParameter("listname");
			if (!listName.equals("") & !listName.contains("/")) {
				out.print(cc.saveCompetitors(listName));
				out.close();
			}
		}
		
		//Регистрация участника - Register
		if (action.equals("saveCompetitor")) {
			System.out.println("Saving competitor");
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String Pname = request.getParameter("Pname");
			String number = request.getParameter("number");
			String byear = request.getParameter("byear");
			String radioValue = request.getParameter("radioValue");
			String targetEPC = request.getParameter("targetEPC");
			boolean edit = Boolean.parseBoolean(request.getParameter("edit"));
			if (name.equals("") || surname.equals("") || Pname.equals("")|| number.equals(""))
				out.print("<span class=\"form-error\"> Заполните все поля </span>");
			else {
				String regex = "[0-9]+";
				if (!number.matches(regex)) out.print("<span class=\"form-error\"> Номер может содержать только цифры </span>");
				else {
					//Все поля заполнены и заполнены правильно
					int ci2 = cc.getCompetitorsCount();
					boolean editCompleted = false;;
					if (edit) {
						for (int index = 0; index < ci2; index++) 
							if (cc.getCompetitor(index).EPC.equals(targetEPC)) {
								cc.editCompetitor(name, surname, Pname, number, byear, radioValue, index);
								editCompleted = true;
							}
						if (editCompleted) {out.print("<span class=\"form-success\"> Данные участника отредактированы </span>");
						fc.setSuggestedNumber(targetEPC, Integer.parseInt(number));}
						else out.print("<span class=\"form-error\"> Нет участника с такой меткой </span>");
						
					}
					else {
						
						boolean duplicate = false;
						boolean duplicateCompet = false;
						for (int index1 = 0; index1 < ci2; index1++) {
							if (cc.getCompetitor(index1).EPC.equals(targetEPC)) duplicate = true; // toDO: handle compet correction
							if (cc.getCompetitor(index1).Name.equals(name) &
									cc.getCompetitor(index1).Surname.equals(surname) &
									cc.getCompetitor(index1).Patron.equals(Pname) &
									cc.getCompetitor(index1).Number == number &
									cc.getCompetitor(index1).BYear == byear &
									cc.getCompetitor(index1).Gender.equals(radioValue)) duplicateCompet = false;
						}
						if (!duplicate & !duplicateCompet) { 
							cc.addCompetitor(name, surname, Pname, number, byear, radioValue, targetEPC);
							out.print("<span class=\"form-success\"> Участник зарегистрирован </span>");
							fc.setSuggestedNumber(targetEPC, Integer.parseInt(number));
						} else if (duplicateCompet == false) out.print("<span class=\"form-error\"> Участник с такими данными уже зарегистрирован </span>");
						else out.print("<span class=\"form-error\"> Эта метка уже зарегистрирована </span>");
					}
				}
			}
			out.close();
		}

		//Подключение к ридеру - Tag,Register, Contest
		if (action.equals("startRead")) {
			System.out.println("Trying to connect to the reader");
			//rc.COMConnect();
			rc.TCPConnect();
		}
		
		//Отключение от ридеоа - Tag,Register, Contest
		if (action.equals("stopRead")) {
			//rc.COMDisconnect();
			rc.TCPDisconnect();
		}
		
		// Сброс чувствительного списка меток - Tag
		if (action.equals("resetTags")) {
			rc.resetEPC();
			out.print("Tags reset <br>");
			out.close();
		}
	
		//Обновлене списка меток - Tag
		if (action.equals("epcCheckAct")) {
			//System.out.println("Epc Check: "+ timeStr);
			
			out.print("<div class=\"col-md-12\"><div class=\"card\">");
			out.print("<div class=\"card-body\"><div class=\"table-responsive\"><table class=\"table\"><thead class=\" text-primary\">");		
			out.print("<th>№</th>"
					+ "<th>EPC</th>"
					+ "<th>Last seen</th>"
					+ "<th>Ant num</th></thead><tbody>");
			
			int i = rc.getUniqueTagCount();
			
			if (i != 0) {
				//System.out.println("rc.getRegTimestamp(0): "+ rc.getLastTimestamp(0));
				for (int index = 0; index < i; index++) {
					int[] CheckTime = new int[3];
					int[] ParsedTime = new int[3];
					int[] PassedTime = new int[3];
					boolean expired = false;
					CheckTime[0] = current.get(Calendar.HOUR_OF_DAY);
					CheckTime[1] = current.get(Calendar.MINUTE);
					CheckTime[2] = current.get(Calendar.SECOND);

					String[] pairs = rc.getLastTimestamp(index).split(":");			
					for (int i1 = 0; i1 < pairs.length; i1++) {
						ParsedTime[0] = Integer.parseInt(pairs[0]);
						ParsedTime[1] = Integer.parseInt(pairs[1]);
						ParsedTime[2] = Integer.parseInt(pairs[2]);
					}
					PassedTime[0] = CheckTime[0] - ParsedTime[0];
					PassedTime[1] = CheckTime[1] - ParsedTime[1];
					PassedTime[2] = CheckTime[2] - ParsedTime[2];
					if (PassedTime[1] < 0) {
						PassedTime[0]--;
						PassedTime[1]=PassedTime[1]+60;
						if (PassedTime[2] < 0) {
							PassedTime[1]--;
							PassedTime[2]=PassedTime[2]+60;
						}
					}
					if (PassedTime[0]>0 || PassedTime[1]>0 || PassedTime[2]>2){
						expired = true;
						
					}
					//System.out.println("expired "+expired);
					
					int hi = index + 1;
					out.print("<tr ");
					if (!expired) out.print("class = \"newTag\" ");
					out.print("><td>" + hi + "</td>" + 
							"	<td>" + rc.getEPC(index) + "</td>" + 
							"	<td>" + rc.getLastTimestamp(index) + "</td>" + 
							"	<td>" + rc.getAntNum(index) + "</td>" + 
							"</tr>");
				}
			}
			out.print("</tbody></table></div></div></div></div>");
			out.close();
		}
		
		//Вывод спсика меток из чувствительного списка для регистрации
		if (action.equals("SensListCheck")) {
			rc.checkExpired();
			//System.out.println("Sens List check " + timeStr);
			int i = rc.getRegListCount();
			if (i != 0) {
				String EPCtoReg = rc.getRegEPC(0);
				
				if (LastCheckedEPC.equals("") || !LastCheckedEPC.equals(EPCtoReg)) {
					LastCheckedEPC = EPCtoReg;
					LastSuggestedNum = fc.getSuggestedNumber(EPCtoReg);
				}
				
				out.print("{\"number\":"+LastSuggestedNum+",");

				
				out.print("\"table\":\"<div class=\\\"table-responsive\\\"><table class=\\\"table\\\"><thead class=\\\" text-primary\\\">");
				out.print("<th>№</th>" + 
						"<th>EPC</th>" + 
						"<th>Last seen</th></thead><tbody>");
				for (int index = 0; index < i; index++) {					
					int hi = index + 1;			
					out.print("<tr ");
					if (index==0) out.print("class = \\\"tagToReg\\\"");
					out.print("><td>"+ hi +"</td>" + 
							"<td ");
					if (index==0) out.print("id = \\\"newTag\\\"");
					out.print(">"+ rc.getRegEPC(index) + "</td>" + 
							"<td>" + rc.getRegTimestamp(index)+"</td>" + 
							"</tr>");
				}
				out.print("</tbody></table></div>\"}");
				
			} else out.print("{\"number\":-1,\"table\":\"<div class=\\\"table-responsive\\\"><table class=\\\"table\\\">"
					+ "<thead class=\\\" text-primary\\\"><th>№</th>" 
					+ "<th>EPC</th>" 
					+ "<th>Last seen</th></thead><tbody></tbody></table></div>\"}");
			out.close();
		}
	}
}
