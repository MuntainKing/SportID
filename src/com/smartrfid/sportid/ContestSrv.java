package com.smartrfid.sportid;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ContestSrv")
public class ContestSrv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FileController fc = new FileController();
    String[] lists = new String[40];  
    ReaderController rc;
    String[][] ContestTimestamps = new String[40][10];
    int ControlPoints;
    int CompetCount;
    String listName;
    Competitor[] list = new Competitor[40];
    String[][] Finalists = new String[40][2];
    //private static final String CompetitorsListPath = "C:\\Work\\savedfiles\\";
    private static final String CompetitorsListPath = "/usr/lib/tomcat9/apache-tomcat-9.0.20/files/reglist/";
	
    public ContestSrv() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.response.getWriter().append("Served at: ").append(request.getContextPath());
		 rc = (ReaderController) request.getSession().getAttribute("ReadCtr");
		 PrintWriter out = response.getWriter();
		 int q = rc.getUniqueTagCount();
		 out.write(Integer.toString(q));
		 out.close();

	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.setContentType("text/html; charset=utf-8");
		
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		if (action.equals("StartCompet")) {
			// кнопка старт - отсчет пошёл, отдать ?старт таймера?
			rc.initChP();
			rc.startCompetition();
			System.out.println("Competition started");
		}
		
		if (action.equals("StopCompet")) {
			rc.stopCompetition();
			Finalists = rc.getFinalists();
			int FinCount = rc.getFinalistCount();
			
			out.print("<table><tr>"
					+ "<td><p>Место</p></td>"
					+ "<td><p>Участник</p></td>"
					+ "<td><p>Время</p></td></tr>");

			
			// тело таблицы
			for (int index = 0; index < FinCount; index++) { // строки, финалисты
				//mesto
				out.print("<tr><td>"+ (index + 1) + "</td>");
				//imya + nomer
				int FinalistIndex = Integer.parseInt(Finalists[index][0]);
				if (!list[FinalistIndex].Name.equals("")) out.print("<td><p>" + list[FinalistIndex].Surname + " " + list[FinalistIndex].Number + "</p></td>");
	
				out.print("<td>");
				if (Finalists[index][1] != null) out.print(Finalists[index][1]);
				out.print("</td>");
					
				out.print("</tr>");
			}
			out.print("</table>");

			out.close();	
			
			
		}
		
		if (action.equals("NewCompet")) {
			rc.newCompet();	
			createCompetTable(listName,ControlPoints,out);
		}
		
		if (action.equals("UpdateCompet")) {
			// обновляем таблицу. Берём ТСы у ридер контроллера, формируем хтмл таблицу и отправляем
			
				ContestTimestamps = rc.getTimeStamps();
				
				//Выводим таймер
				out.print("<div class=\"TimerContainer\">");
				if (rc.isCompetitionStarted()) {
				String TimerStr = rc.getTimerString();
				out.print("<div class = \"Timer\">"+TimerStr+"</div>");}
				else out.print("<div class = \"Timer\">00:00:00:000</div>");
				out.print("</div>");
				
				//формируем шапку таблицы
				out.print("<table><tr>"
						+ "<td><p>Участники</p></td>"
						+ "<td><p>Время прохождения старта</p></td>");
				
				for (int index = 0; index < ControlPoints-2; index++) 
					out.print("<td><p>Время прохождения "+ (index + 1) +" точки</p></td>");
						
				out.print("<td><p>Время прохождения финиша</p></td></tr>");
				
				// тело таблицы
				for (int index = 0; index < CompetCount; index++) { // строки, участнки
					
					if (!list[index].Name.equals("")) out.print("<tr><td><p>" + list[index].Surname + " " + list[index].Number + "</p></td>");
				
					
					for (int index2 = 0; index2 < ControlPoints; index2++) { // столбцы, контрольные точки
						out.print("<td>");
						//System.out.println("ContestTimestamps " + ContestTimestamps[index][index2]);
						if (ContestTimestamps[index][index2] != null) out.print(ContestTimestamps[index][index2]);
						else out.print("");
						out.print("</td>");
						}
					out.print("</tr>");
				}
				out.print("</table>");
				out.close();	
		}
		
		if (action.equals("createTable")) {
			listName = request.getParameter("listName");
			ControlPoints = Integer.parseInt(request.getParameter("lapsCount"));		
			createCompetTable(listName,ControlPoints,out);
			
		}
		
		if (action.equals("getLists")) {
			System.out.println("Getting competitors lists");
			lists = fc.getListofLists();
			int listCount = fc.getListCount();
			out.write("{\"count\":"+ listCount + ",");
			out.write("\"data\":[");
			for (int index = 0; index < listCount; index++) {
				out.write("\"" + lists[index] + "\"");
				if (index != listCount-1) out.write(",");
			}
			out.write("]}");
			out.close();
		}
	}

	public void createCompetTable(String listName, int ControlPoints, PrintWriter out) {
		list = fc.retrieveCompetitors(listName);	
		CompetCount = fc.getCompetCount();
		rc.setCheckPoints(ControlPoints);
		rc.setCompetitors(list);
		rc.setCompetCount(CompetCount);
		out.print("<table><tr>"
				+ "<td><p>Участники</p></td>"
				+ "<td><p>Время прохождения старта</p></td>"
		);
		for (int index = 0; index < ControlPoints-2; index++) {
		out.print("<td><p>Время прохождения "+ (index + 1) +" точки</p></td>");
		}
		out.print("<td><p>Время прохождения финиша</p></td></tr>");
		for (int index = 0; index < CompetCount; index++) {
			
			if (!list[index].Name.equals("")) out.print("<tr><td><p>" + list[index].Surname + " " + list[index].Number + "</p></td><td></td>");
			for (int index2 = 0; index2 < ControlPoints-2; index2++) {
				out.print("<td></td>");
				}
			out.print("<td></td></tr>");
		}
		out.print("</table>");
		out.close();
	}
	
}
