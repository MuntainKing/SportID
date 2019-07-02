package com.smartrfid.sportid;

public class Competitor {
	public String Name;
	public String Surname;
	public String Patron;
	public String Number;
	public String BYear;
	public String Gender;
	public String EPC;
	
	public Competitor(String Name, String Surname, String Patron, String Number, String BYear, String Gender, String EPC) {
		this.Name = Name;
		this.Surname = Surname;
		this.Patron = Patron;
		this.Number = Number;
		this.BYear = BYear;
		this.Gender = Gender;
		this.EPC = EPC;
	}
	
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
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getBYear() {
		return BYear;
	}
	public void setBYear(String bYear) {
		BYear = bYear;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}

}
