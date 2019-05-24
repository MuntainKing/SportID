package com.smartrfid.sportid;

import java.io.*;

public class FileController {

	public void clearFile() throws Exception{
		File file = new File("../savedfiles/demo.txt");
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file));
		os.write("");
		os.close();
	}

	public void saveCompetitor(Competitor cmp) throws Exception {

		File file = new File("../savedfiles/demo.txt");
		OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file,true));
		os.append(cmp.getName());
		os.append(cmp.getSurname());
		os.append(cmp.getPatron());
		os.append(Integer.toString(cmp.getNumber()));
		os.append(Integer.toString(cmp.getBYear()));
		os.append(cmp.getGender());
		os.append(cmp.getEPC());
		os.close();
	}
}
