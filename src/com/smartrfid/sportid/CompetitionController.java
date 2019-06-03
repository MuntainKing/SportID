package com.smartrfid.sportid;

public class CompetitionController {
	FileController fc = new FileController();
	Competitor[] competitors = new Competitor[40];
	int competCount = 0;
	
	public void addCompetitor(String Name, String Surname, String Patron, int number, int bYear, String Gender, String EPC) {
		competitors[competCount] = new Competitor();
		competitors[competCount].Name = Name;
		competitors[competCount].Surname = Surname;
		competitors[competCount].Patron = Patron;
		competitors[competCount].Number = number;
		competitors[competCount].BYear = bYear;
		competitors[competCount].Gender = Gender;
		competitors[competCount].EPC = EPC;
		competCount++;
	}
	
	public Competitor getCompetitor(int num) {
		if (num>competCount) {return null;}
		else return competitors[num];
	}
	public int getCompetitorsCount() {
		return competCount;
	}
	
	public void saveCompetitors(String name) {
		if (competCount > 0)
		try {
			fc.saveList(name, competitors, competCount);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
