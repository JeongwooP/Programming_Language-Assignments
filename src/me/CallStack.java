package me;

public class CallStack {

	public static void main(String[] args) {
		firstMethod();
	}

	static void firstMethod() {
		secondMethod();
	}
	
	static void secondMethod() {
		System.out.println("I am second");
	}
}
