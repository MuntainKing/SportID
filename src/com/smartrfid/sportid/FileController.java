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
	int ResultListCount = 0;
	int CompetCount = 0;
	int FinalistCompetCount = 0;
	Competitor[] list;
	private static final String Separator = ";";
	private static final String CompetitorsListPath = "/usr/tomcat9/apache-tomcat-9.0.21/webapps/files/reglist/";
	private static final String ResultsPath = "/usr/tomcat9/apache-tomcat-9.0.21/webapps/files/results/";
	//private static final String ResultCodesPath = "/usr/lib/tomcat9/apache-tomcat-9.0.20/codes/results.csv";
	//private static final String ReglistCodesPath = "/usr/lib/tomcat9/apache-tomcat-9.0.20/codes/results.csv";
    
   // private static final String CompetitorsListPath = "C:\\Work\\savedfiles\\";

    public static String getCompetitorslistpath() {
		return CompetitorsListPath;
	}
	public static String getResultspath() {
		return ResultsPath;
	}

	public int getListCount() {
		return listCount;
	}
	
	public int getCompetCount() {
		return CompetCount;
	}

	
	//Сохранить список результатов в файл с именем
	public void SaveResult(String contestNameString, Competitor[] list, String[][] finalistTableStrings, int compCount) throws Exception {
		
		File file = new File(ResultsPath + contestNameString + ".csv");
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file,true),Charset.forName("UTF-8").newEncoder());
		System.out.println("FC Saving " + file.getPath());
		
		os.append("Имя");
		os.append(Separator);
		os.append("Фамилия");
		os.append(Separator);
		os.append("Отчество");
		os.append(Separator);
		os.append("Номер");
		os.append(Separator);
		os.append("Год рождения");
		os.append(Separator);
		os.append("Пол");
		os.append(Separator);
		os.append("Метка");
		os.append(Separator);
		os.append("Результат");
		os.append("\r\n");
		
		for (int index = 0; index < compCount; index++) {
		int FinalistIndex = Integer.parseInt(finalistTableStrings[index][0]);
		os.append(list[FinalistIndex].getName());
		os.append(Separator);
		os.append(list[FinalistIndex].getSurname());
		os.append(Separator);
		os.append(list[FinalistIndex].getPatron());
		os.append(Separator);
		os.append(list[FinalistIndex].getNumber());
		os.append(Separator);
		os.append(list[FinalistIndex].getBYear());
		os.append(Separator);
		os.append(list[FinalistIndex].getGender());
		os.append(Separator);
		os.append(list[FinalistIndex].getEPC());
		os.append(Separator);
		os.append(finalistTableStrings[index][1]);	
		os.append("\r\n");
		}
		
		os.close();
	}
	
	public int getResultListCount() {
		return ResultListCount;
	}

	//не робит на линухе
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
	
	public void deleteFile(String fileName, boolean filetype) {
		File file;
		if (filetype)
		file = new File(ResultsPath + fileName + ".csv");
		else file = new File(CompetitorsListPath + fileName + ".csv");
		
        if(file.delete()){ System.out.println("File deleted");
        }else System.out.println("File doesn't exist");
	}
	
	//Получить список списков участников
	public String[] getListofLists() {
		listCount = 0;
		String[] listsStr = new String[40];
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
			String[] data = str.split("\\.(?=[^\\.]+$)");
        	if (data.length != 0)
        	listsStr[listCount] = data[0];
        	listCount++;
		}
		
		return listsStr;
	}
	
	//Получить список списков результатов 
	public String[] getListofResults() {
		ResultListCount = 0;
		String[] listsStr = new String[40];
		List<String> lists =  new ArrayList<>();
		try (Stream<Path> paths = Files.walk(Paths.get(ResultsPath))) {
			lists = paths
					.skip(1)
					.map(Path -> Path.getFileName().toString())
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listsStr = lists.toArray(new String[lists.size()]);
		
		for (String str : listsStr) 
		{ 
			String[] data = str.split("\\.(?=[^\\.]+$)");
        	if (data.length != 0)
        	listsStr[ResultListCount] = data[0];
        	ResultListCount++;
		}
		
		return listsStr;
	}
	
	public int getFinalistCompetCount() {
		return FinalistCompetCount;
	}
	
	// Сохранить список участников в файл с именем
	public void saveList(String listName, Competitor[] list, int compCount) throws Exception {
	
		File file = new File(CompetitorsListPath + listName + ".csv");
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file,true),Charset.forName("UTF-8").newEncoder());		
		
		
		os.append("Имя");
		os.append(Separator);
		os.append("Фамилия");
		os.append(Separator);
		os.append("Отчество");
		os.append(Separator);
		os.append("Номер");
		os.append(Separator);
		os.append("Год рождения");
		os.append(Separator);
		os.append("Пол");
		os.append(Separator);
		os.append("Метка");
		os.append(Separator);
		os.append("\r\n");
		
		for (int index = 0; index < compCount; index++) {
		os.append(list[index].getName());
		os.append(Separator);
		os.append(list[index].getSurname());
		os.append(Separator);
		os.append(list[index].getPatron());
		os.append(Separator);
		os.append(list[index].getNumber());
		os.append(Separator);
		os.append(list[index].getBYear());
		os.append(Separator);
		os.append(list[index].getGender());
		os.append(Separator);
		os.append(list[index].getEPC());
		os.append(Separator);
		os.append("\r\n");
		}
		
		os.close();
	}
	
	//Получить массив Участников из файла с именем
	public Competitor[] retrieveCompetitors(String listName) {
		CompetCount = 0;
		BufferedReader reader;
		list = new Competitor[40];
		String[] competitor;
		try {
			reader = new BufferedReader(new FileReader(CompetitorsListPath + listName + ".csv"));
			String line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				competitor = line.split(Separator);
				list[CompetCount] = new Competitor(
						competitor[0],
						competitor[1],
						competitor[2],
						competitor[3],
						competitor[4],
						competitor[5],
						competitor[6]);
				CompetCount++;
				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());

		}	
		return list;		
	}
	
	//Получить таблицу результатов из файла с именем
	public String[][] retrieveResult(String resultName) {
		FinalistCompetCount = 0;
		BufferedReader reader;
		String[][] list = new String[40][8];
		String[] Cols;
		try {
			reader = new BufferedReader(new FileReader(ResultsPath + resultName + ".csv"));
			String line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				Cols = line.split(Separator);
				list[FinalistCompetCount][0] = Cols[0];
				list[FinalistCompetCount][1] = Cols[1];
				list[FinalistCompetCount][2] = Cols[2];
				list[FinalistCompetCount][3] = Cols[3];
				list[FinalistCompetCount][4] = Cols[4];
				list[FinalistCompetCount][5] = Cols[5];
				list[FinalistCompetCount][6] = Cols[6];
				list[FinalistCompetCount][7] = Cols[7];
				FinalistCompetCount++;
				line = reader.readLine();
			}
			reader.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());

		}	
		return list;		
	}
}
