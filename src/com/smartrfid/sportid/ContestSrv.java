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
		
		if (action.equals("createTable")) {

			Competitor[] list = new Competitor[40];
			String listName = request.getParameter("listName");
			list = fc.retrieveCompetitors(listName);
			int CompetCount = fc.getCompetCount();
			System.out.println("Check main");
			out.print("<table><tr>"
					+ "<td><p>Участники</p></td>"
					+ "<td><p>Время прохождения старта</p></td>"
			);
			
			out.print(""		+ "<td><p>Время прохождения финиша</p></td>"
					+ "</tr>");
			for (int index = 0; index < CompetCount; index++) {
				System.out.println("index" + index);
				if (!list[index].Name.equals("")) out.print("<tr><td><p>" + list[index].Surname + " " + list[index].Number + "</p></td><td></td><td></td></tr>");
			}
			out.print("</table>");
			out.close();
			
		}
		
		if (action.equals("getLists")) {
			System.out.println("Getting competitors lists");
			lists = fc.getListofLists(new File("C:\\Work\\savedfiles\\"));
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

}
