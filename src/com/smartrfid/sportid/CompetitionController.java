package com.smartrfid.sportid;

public class CompetitionController {
	Competitor[] competitors = new Competitor[40];
	int competCount = 0;
	
	public void addCompetitor(String Name, String Surname, String Patron, int number, int bYear, String Gender, String EPC) {
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
		else return null;
	}
}