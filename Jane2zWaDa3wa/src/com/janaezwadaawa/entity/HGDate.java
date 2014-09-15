package com.janaezwadaawa.entity;

public class HGDate extends Object {
	public final static int G = 0;
	public final static int J = 1;
	public final static int HG = 2;
	public final static int HH = 3;

	private int day;
	private int month;
	private int year;
	private int gregorian;
	
	public HGDate() {
	
	}
	
	public HGDate(int d, int m, int y) {
		day = d;
		month = m;
		year = y;
		gregorian = G;
	}
	
	public HGDate(int d, int m, int y, int g) {
		day = d;
		month = m;
		year = y;
		gregorian = g;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getGregorian() {
		return gregorian;
	}

	public void setGregorian(int gregorian) {
		this.gregorian = gregorian;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(day + " ");
		sb.append(month + " ");
		sb.append(year + " ");
		return sb.toString();
	}

}
