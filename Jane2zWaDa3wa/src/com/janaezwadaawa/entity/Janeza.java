package com.janaezwadaawa.entity;


public class Janeza extends JDEntity{
	
	private String place;
	private String mosque;
	private String prayerTime;
	private String latitude;
	private String longitude;
	private String gender;
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	public String getMosque() {
		return mosque;
	}

	public void setMosque(String mosque) {
		this.mosque = mosque;
	}

	public String getPrayerTime() {
		return prayerTime;
	}

	public void setPrayerTime(String prayerTime) {
		this.prayerTime = prayerTime;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("Place " + getPlace() + "\n");
		sb.append("Mosque " + getMosque() + "\n");
		sb.append("PrayerTime " + getPrayerTime() + "\n");
		sb.append("Latitude " + getLatitude() + "\n");
		sb.append("Longitude " + getLongitude() + "\n");
		return sb.toString();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
