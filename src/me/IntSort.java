package me;
import java.io.*;
import java.util.Scanner;
//�ڹ� ���α׷�
//�Է� : int�� length, length�� 100���� ������ ������ ������ ���� �ڵ���
//��� : ��� �Է� ���� ��պ��� ū �Է� ������ ��
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
			//�Է��� �迭�� �ְ� �ջ��� �Ѵ�
			for(count = 0; count < length; count++) {
				intlist[count] = s.nextInt();
				sum += intlist[count];
			}
			//����� ����
			average = sum / length;
			//��պ��� ū �Է°��� ��
			for(count = 0; count < length; count++)
				if(intlist[count] > average) result++;
			//����� ���
			System.out.println("��պ��� ū ���� ���ڴ�: " + result);
		}	//if���� ��
		else System.out.println("����, ����Ʈ �Է� ���� ����");
	}//���� �޼ҵ� ��
}//Ŭ���� IntSort�� ��
