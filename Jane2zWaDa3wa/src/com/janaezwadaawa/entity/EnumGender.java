package com.janaezwadaawa.entity;

public class EnumGender{

	public enum Gender {

		MEN(0),
		WOMEN(1),
		CHILD_MALE(2),
		CHILD_FEMALE(3);


		private final int id;
		Gender (int id) { this.id = id; }
		public int getValue() { return id;}
		
		public static Gender valueToGender (int value) {
			
			for (Gender a : Gender.values()) {
				if (value == a.getValue())
					return a;
			}
			return null;
		}

	}
}
