package lexical_analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyLexical {
	
	public static void main(String[] args) {
		LexicalA a = new LexicalA();
		try{
            //파일 객체를 생성합니다. input.txt파일을 읽어옵니다.
            File file = new File("D:\\workspace\\me\\src\\lexical_analyzer\\input.txt");
            //입력 스트림을 생성합니다.
            FileReader filereader = new FileReader(file);
          
            a.getChar();	//먼저 문자를 분류합니다.
            do {
            	a.lex(); //do while문에 lex()가 먼저 실행되고 a객체의 nextToken변수가 EOF가 아닐때까지 반복합니다.
            }while(a.getnextToken() != a.EOF);
            filereader.close();	//파일을 닫습니다.
                  
        }catch (FileNotFoundException e) {
        	System.out.println("Error1:" + e);	//파일이 없으면 Error1 : e 출력합니다.
        }catch(IOException e){
            System.out.println("Error2:" + e);	//처리가 잘못되면 Error2 : e 출력합니다.
        }
	}
}
