package me;

public class CallStack2 {

	public static void main(String[] args) {
		System.out.println("main메서드 시작");
		firstM();
		System.out.println("main메서드 종료");
	}
	
	static void firstM() {
		System.out.println("firstM() 시작");
		secondM();
		System.out.println("firstM() 종료");
	}
	static void secondM() {
		System.out.println("secondM() 시작");
		System.out.println("secondM() 종료");
	}
}
