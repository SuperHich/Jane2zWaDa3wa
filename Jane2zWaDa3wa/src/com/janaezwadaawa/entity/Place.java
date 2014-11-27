package com.janaezwadaawa.entity;


public class Place extends JDEntity{
	
	private String placeEn;

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
		sb.append("placeEn " + getPlaceEn());
		return sb.toString();
	}

}
