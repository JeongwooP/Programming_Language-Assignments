package me;

public class CallStack2 {

	public static void main(String[] args) {
		System.out.println("main�޼��� ����");
		firstM();
		System.out.println("main�޼��� ����");
	}
	
	static void firstM() {
		System.out.println("firstM() ����");
		secondM();
		System.out.println("firstM() ����");
	}
	static void secondM() {
		System.out.println("secondM() ����");
		System.out.println("secondM() ����");
	}
}
