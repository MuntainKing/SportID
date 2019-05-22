package com.smartrfid.sportid;

import java.io.*;

public class FileController {
	 public void newFile() throws Exception {
		 
		 File file = new File("demo.txt");
		 OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file));
		 
	            for(int i = 2; i <= 9; i++) {
	                    os.write(Integer.toString(i)); 
	                }
	        os.close();
	    }
}
