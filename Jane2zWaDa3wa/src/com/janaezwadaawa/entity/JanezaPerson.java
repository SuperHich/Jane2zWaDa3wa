package com.janaezwadaawa.entity;

public class JanezaPerson extends JDEntity{
	
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("date " + getDate());
		return sb.toString();
	}
}
