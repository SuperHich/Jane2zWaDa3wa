package com.janaezwadaawa.entity;

import java.util.ArrayList;


public class Mosque extends JDEntity{
	
	private int count;
	private String place;
	private ArrayList<Janeza> jana2z = new ArrayList<Janeza>();

	public ArrayList<Janeza> getJana2z() {
		return jana2z;
	}

	public void setJana2z(ArrayList<Janeza> jana2z) {
		this.jana2z = jana2z;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
