package com.janaezwadaawa.entity;

public class Mosque2 extends JDEntity{
	
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("Count " + getCount() + "\n");
		return sb.toString();
	}
}
