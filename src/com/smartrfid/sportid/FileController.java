package com.smartrfid.sportid;

import java.io.*;

public class FileController {
	int listCount = 0;
	int CompetCount = 0;
	Competitor[] list;

	public int getListCount() {
		return listCount;
	}
	
	public int getCompetCount() {
		return CompetCount;
	}


	public String[] getListofLists(final File folder) {
		listCount = 0;
		String[] lists = new String[40];
		String[] data;
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	getListofLists(fileEntry);
	        } else {
	        	String a = fileEntry.getName();
	        	//System.out.println(a);
	        	
	        	data = a.split("\\.");
	        	
	        	//for (String url : data) {
	        	//	  System.out.println("Found URL " + url);
	        	//}
	        	if (data.length != 0)
	        	lists[listCount] = data[0];
	        	//System.out.println(lists[listCount]);
	        	listCount++;
	        }
	    }
	    return lists;
	}

	public void saveList(String listName, Competitor[] list, int compCount) throws Exception {
		
		File file = new File("C:\\Work\\savedfiles\\" + listName + ".csv");
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file,true));
		
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
			reader = new BufferedReader(new FileReader("C:\\Work\\savedfiles\\" + listName + ".csv"));
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
