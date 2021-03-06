package com.janaezwadaawa.entity;

public class JDEntity {

	private int id;
	private String title;
	
	public JDEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public JDEntity(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Id " + getId() + "\n");
		sb.append("Title " + getTitle() + "\n");
		return sb.toString();
	}

}
