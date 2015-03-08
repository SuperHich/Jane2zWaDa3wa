package com.janaezwadaawa.entity;


public class Prayer extends JDEntity{
	private int count;
	private int count_men;
	private int count_women;
	private int count_child;

	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount_men() {
		return count_men;
	}

	public void setCount_men(int count_men) {
		this.count_men = count_men;
	}

	public int getCount_women() {
		return count_women;
	}

	public void setCount_women(int count_women) {
		this.count_women = count_women;
	}

	public int getCount_child() {
		return count_child;
	}

	public void setCount_child(int count_child) {
		this.count_child = count_child;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("count " + getCount() + "\n");
		sb.append("count men " + getCount_men() + "\n");
		sb.append("count women " + getCount_women() + "\n");
		sb.append("count child " + getCount_child() + "\n");
		return sb.toString();
	}

}
