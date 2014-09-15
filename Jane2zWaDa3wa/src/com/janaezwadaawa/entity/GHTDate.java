package com.janaezwadaawa.entity;

public class GHTDate {
	
	private int DayG, DayH, DayT;
	private int YearG, YearH, YearT;
	private int MonthG, MonthH, MonthT;
	private String DayNameG, DayNameH, DayNameT;
	private String MonthNameG, MonthNameH, MonthNameT;
	
	
	public String getDayNameG() {
		return DayNameG;
	}
	public void setDayNameG(String dayNameG) {
		DayNameG = dayNameG;
	}
	public String getDayNameH() {
		return DayNameH;
	}
	public void setDayNameH(String dayNameH) {
		DayNameH = dayNameH;
	}
	public String getDayNameT() {
		return DayNameT;
	}
	public void setDayNameT(String dayNameT) {
		DayNameT = dayNameT;
	}
	public int getMonthG() {
		return MonthG;
	}
	public void setMonthG(int monthG) {
		MonthG = monthG;
	}
	public int getMonthH() {
		return MonthH;
	}
	public void setMonthH(int monthH) {
		MonthH = monthH;
	}
	public int getMonthT() {
		return MonthT;
	}
	public void setMonthT(int monthT) {
		MonthT = monthT;
	}
	public String getMonthNameG() {
		return MonthNameG;
	}
	public void setMonthNameG(String monthNameG) {
		MonthNameG = monthNameG;
	}
	public String getMonthNameH() {
		return MonthNameH;
	}
	public void setMonthNameH(String monthNameH) {
		MonthNameH = monthNameH;
	}
	public String getMonthNameT() {
		return MonthNameT;
	}
	public void setMonthNameT(String monthNameT) {
		MonthNameT = monthNameT;
	}
	public int getDayG() {
		return DayG;
	}
	public void setDayG(int dayG) {
		DayG = dayG;
	}
	public int getDayH() {
		return DayH;
	}
	public void setDayH(int dayH) {
		DayH = dayH;
	}
	public int getDayT() {
		return DayT;
	}
	public void setDayT(int dayT) {
		DayT = dayT;
	}
	public int getYearG() {
		return YearG;
	}
	public void setYearG(int yearG) {
		YearG = yearG;
	}
	public int getYearH() {
		return YearH;
	}
	public void setYearH(int yearH) {
		YearH = yearH;
	}
	public int getYearT() {
		return YearT;
	}
	public void setYearT(int yearT) {
		YearT = yearT;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("dayG " + DayG + " , ");
		sb.append("MonthG " + MonthG + " , ");
		sb.append("YearG " + YearG + " , ");
		sb.append("DayNameG " + DayNameG + " , ");
		sb.append("MonthNameG " + MonthNameG + " , ");
		
		sb.append("dayH " + DayH + " , ");
		sb.append("MonthH " + MonthH + " , ");
		sb.append("YearH " + YearH + " , ");
		sb.append("DayNameH " + DayNameH + " , ");
		sb.append("MonthNameH " + MonthNameH + " , ");
		
		sb.append("dayT " + DayT + " , ");
		sb.append("MonthT " + MonthT + " , ");
		sb.append("YearT " + YearT + " , ");
		sb.append("DayNameT " + DayNameT + " , ");
		sb.append("MonthNameT " + MonthNameT + " , ");
		return sb.toString();
	}
}
