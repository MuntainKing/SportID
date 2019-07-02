package com.smartrfid.sportid;

public class CompetitionController {
	FileController fc = new FileController();
	Competitor[] competitors = new Competitor[40];
	int competCount = 0;
	
	public void addCompetitor(String Name, String Surname, String Patron, String number, String bYear, String Gender, String EPC) {
		competitors[competCount] = new Competitor(Name,Surname,Patron,number,bYear,Gender,EPC);
		competCount++;
	}
	
	public Competitor getCompetitor(int num) {
		if (num>competCount) {return null;}
		else return competitors[num];
	}
	public int getCompetitorsCount() {
		return competCount;
	}
	
	public void resetCompetitors() {
		for (int i = 0; i < competCount; i++) {
			competitors[i] = null;
		}
		competCount = 0;
	}
	
	public void editCompetitor(String Name, String Surname, String Patron, String number, String bYear, String Gender, int index) {
		competitors[index].Name = Name;
		competitors[index].Surname = Surname;
		competitors[index].Patron = Patron;
		competitors[index].Number = number;
		competitors[index].BYear = bYear;
		competitors[index].Gender = Gender;
	}

	
	public String saveCompetitors(String name) {
		if (competCount > 0)
		try {
			fc.saveList(name, competitors, competCount);
			return "Saved successfully";
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		} else return "Nothing to save";
	}
}
