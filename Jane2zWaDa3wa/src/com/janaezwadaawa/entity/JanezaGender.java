package com.janaezwadaawa.entity;

import java.util.ArrayList;


public class JanezaGender extends JDEntity{
	private int count;
	private ArrayList<JanezaPerson> names = new ArrayList<JanezaPerson>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<JanezaPerson> getNames() {
		return names;
	}

	public void setNames(ArrayList<JanezaPerson> names) {
		this.names = names;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("count " + getCount() + "\n");
		sb.append("title " + getTitle() + "\n");
		sb.append("names " + getNames().size() + "\n");
		return sb.toString();
	}

}
