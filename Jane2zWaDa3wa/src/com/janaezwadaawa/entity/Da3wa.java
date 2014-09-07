package com.janaezwadaawa.entity;


public class Da3wa extends JDEntity{
	
	private String description;
	private String place;
	private String mosque;
	private String trainer;
	private String startTime;
	private String endTime;
	private String latitude;
	private String longitude;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
		sb.append("Description " + getDescription() + "\n");
		sb.append("Place " + getPlace() + "\n");
		sb.append("Mosque " + getMosque() + "\n");
		sb.append("Trainer " + getTrainer() + "\n");
		sb.append("StartTime " + getStartTime() + "\n");
		sb.append("EndTime " + getEndTime() + "\n");
		sb.append("Latitude " + getLatitude() + "\n");
		sb.append("Longitude " + getLongitude() + "\n");
		return sb.toString();
	}

}
