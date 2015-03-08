package com.janaezwadaawa.entity;

import java.util.ArrayList;


public class Mosque extends JDEntity{
	
	private int count;
	private String place;
	private String placeEn;
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
	
	public String getPlaceEn() {
		return placeEn;
	}

	public void setPlaceEn(String placeEn) {
		this.placeEn = placeEn;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("Count " + getCount() + "\n");
		sb.append("Place " + getPlace() + "\n");
		sb.append("PlaceEn " + getPlaceEn());
		return sb.toString();
	}
}
