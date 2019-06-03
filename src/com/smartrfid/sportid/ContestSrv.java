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
	
    public ContestSrv() {

        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.setContentType("text/html; charset=utf-8");
		
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
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
