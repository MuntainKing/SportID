package com.smartrfid.sportid;

public class Competitor {
	public String Name = "";
	public String Surname = "";
	public String Patron = "";
	public int Number = 0;
	public int BYear = 0;
	public String Gender = "";
	public String EPC = "";
	
	public String getEPC() {
		return EPC;
	}
	public void setEPC(String ePC) {
		EPC = ePC;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public String getPatron() {
		return Patron;
	}
	public void setPatron(String patron) {
		Patron = patron;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public int getBYear() {
		return BYear;
	}
	public void setBYear(int bYear) {
		BYear = bYear;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}

}
