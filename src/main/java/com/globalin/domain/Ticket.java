package com.globalin.domain;

public class Ticket {

	private int tno;
	private String owner, grade;

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Ticket [tno=" + tno + ", owner=" + owner + ", grade=" + grade + "]";
	}

}
