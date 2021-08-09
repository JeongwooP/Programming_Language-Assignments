package me;
import java.io.*;
import java.util.Scanner;
//자바 프로그램
//입력 : int형 length, length은 100보다 작으며 정수형 길이의 값이 뒤따름
//출력 : 모든 입력 값의 평균보다 큰 입력 데이터 수
public class IntSort {

	public static void main(String[] args) throws IOException{
		Scanner s = new Scanner(System.in);
		int length,
			count,
			sum = 0,
			average,
			result = 0;
		int[] intlist = new int[99];
		length = s.nextInt();
		if((length > 0) && (length < 100)) {
			//입력을 배열에 넣고 합산을 한다
			for(count = 0; count < length; count++) {
				intlist[count] = s.nextInt();
				sum += intlist[count];
			}
			//평균을 연산
			average = sum / length;
			//평균보다 큰 입력값을 셈
			for(count = 0; count < length; count++)
				if(intlist[count] > average) result++;
			//결과를 출력
			System.out.println("평균보다 큰 값의 숫자는: " + result);
		}	//if문의 끝
		else System.out.println("에러, 리스트 입력 길이 문제");
	}//메인 메소드 끝
}//클래스 IntSort의 끝
