package com.smartrfid.sportid;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileController {
	int listCount = 0;
	int CompetCount = 0;
	Competitor[] list;
    private static final String CompetitorsListPath = "/usr/lib/tomcat9/apache-tomcat-9.0.20/webapps/files/reglist/";
   // private static final String CompetitorsListPath = "C:\\Work\\savedfiles\\";

	public int getListCount() {
		return listCount;
	}
	
	public int getCompetCount() {
		return CompetCount;
	}


	public String[] getListofListsDeprecated(final File folder) {
		System.out.println("Cheking for lists");
		listCount = 0;
		String[] lists = new String[40];
		String[] data;
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	getListofListsDeprecated(fileEntry);
	        } else {
	        	String a = fileEntry.getName();
	        	System.out.println(a);
	        	
	        	data = a.split("\\.");
	        	
	        	for (String url : data) {
	        		  System.out.println("Found dir " + url);
	        	}
	        	if (data.length != 0)
	        	lists[listCount] = data[0];
	        	System.out.println("lists[listCount]" + lists[listCount]);
	        	listCount++;
	        }
	    }
	    return lists;
	}
	
	public String[] getListofLists() {
		listCount = 0;
		String[] listsStr = new String[40];
		String[] data;
		List<String> lists =  new ArrayList<>();
		try (Stream<Path> paths = Files.walk(Paths.get(CompetitorsListPath))) {
			lists = paths
					.skip(1)
					.map(Path -> Path.getFileName().toString())
					//.map(if true System.out.println(""))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listsStr = lists.toArray(new String[lists.size()]);
		
		for (String str : listsStr) 
		{ 
			data = str.split("\\.");
        	
        	for (String url : data) {
        		  System.out.println("Found dir " + url);
        	}
        	if (data.length != 0)
        	listsStr[listCount] = data[0];
        	System.out.println("listsStr[listCount]" + listsStr[listCount]);
        	listCount++;
		}
		
		return listsStr;
	}

	public void saveList(String listName, Competitor[] list, int compCount) throws Exception {
	
		File file = new File(CompetitorsListPath + listName + ".csv");
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file,true),Charset.forName("UTF-8").newEncoder());		
		
		for (int index = 0; index < compCount; index++) {
		os.append(list[index].getName());
		os.append(',');
		os.append(list[index].getSurname());
		os.append(',');
		os.append(list[index].getPatron());
		os.append(',');
		os.append(Integer.toString(list[index].getNumber()));
		os.append(',');
		os.append(Integer.toString(list[index].getBYear()));
		os.append(',');
		os.append(list[index].getGender());
		os.append(',');
		os.append(list[index].getEPC());
		os.append(',');
		os.append("\r\n");
		}
		
		os.close();
	}
	
	
	public Competitor[] retrieveCompetitors(String listName) {
		CompetCount = 0;
		BufferedReader reader;
		list = new Competitor[40];
		String[] competitor;
		try {
			reader = new BufferedReader(new FileReader(CompetitorsListPath + listName + ".csv"));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				competitor = line.split(",");
				list[CompetCount] = new Competitor();
				list[CompetCount].Name = competitor[0];
				list[CompetCount].Surname = competitor[1];
				list[CompetCount].Patron = competitor[2];
				list[CompetCount].Number = Integer.parseInt(competitor[3]);
				list[CompetCount].BYear = Integer.parseInt(competitor[4]);
				list[CompetCount].Gender = competitor[5];
				list[CompetCount].EPC = competitor[6];
				CompetCount++;
				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());

		}	
		return list;		
	}
}
