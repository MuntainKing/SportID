package com.smartrfid.sportid;

import java.io.*;

public class FileController {
	int listCount = 0;

	public int getListCount() {
		return listCount;
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
	        	System.out.println(a);
	        	
	        	data = a.split("\\.");
	        	
	        	for (String url : data) {
	        		  System.out.println("Found URL " + url);
	        	}
	        	if (data.length != 0)
	        	lists[listCount] = data[0];
	        	System.out.println(lists[listCount]);
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
	
}
