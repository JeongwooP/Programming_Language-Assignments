package lexical_analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyLexical {
	
	public static void main(String[] args) {
		LexicalA a = new LexicalA();
		try{
            //���� ��ü�� �����մϴ�. input.txt������ �о�ɴϴ�.
            File file = new File("D:\\workspace\\me\\src\\lexical_analyzer\\input.txt");
            //�Է� ��Ʈ���� �����մϴ�.
            FileReader filereader = new FileReader(file);
          
            a.getChar();	//���� ���ڸ� �з��մϴ�.
            do {
            	a.lex(); //do while���� lex()�� ���� ����ǰ� a��ü�� nextToken������ EOF�� �ƴҶ����� �ݺ��մϴ�.
            }while(a.getnextToken() != a.EOF);
            filereader.close();	//������ �ݽ��ϴ�.
                  
        }catch (FileNotFoundException e) {
        	System.out.println("Error1:" + e);	//������ ������ Error1 : e ����մϴ�.
        }catch(IOException e){
            System.out.println("Error2:" + e);	//ó���� �߸��Ǹ� Error2 : e ����մϴ�.
        }
	}
}
