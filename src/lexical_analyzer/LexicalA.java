package lexical_analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LexicalA {
	/* 변수들을 private 자료형으로 선언합니다.*/
	private int charClass;
	private char []lexeme;
	private char nextChar;
	private int lexLen=0;
	private int token;
	private int nextToken;
	private int singleCh = 0;
	private FileReader filereader;
	
	/* 각 문자의 클래스들을 분류할 수 있게 지정했습니다.*/
	public static final int LETTER = 0;
	public static final int DIGIT = 1;
	public static final int UNKNOWN = 99;
	/* 토큰 코드들입니다.*/
	public static final int INT_LIT = 10;
	public static final int IDENT = 11;
	public static final int ASSIGN_OP = 20;
	public static final int ADD_OP = 21;
	public static final int SUB_OP = 22;
	public static final int MULT_OP = 23;
	public static final int DIV_OP = 24;
	public static final int LEFT_PAREN = 25;
	public static final int RIGHT_PAREN = 26;
	public static final int LEFT_BRACE = 27; //추가: 여는 괄호 ({) 
	public static final int RIGHT_BRACE = 28; //추가: 닫는 괄호 (})
	public static final int EQUAL = 29; //추가: 등호 (=)
	public static final int GREATERTHAN = 30; //추가: 부등호 (>)
	public static final int LESSTHAN = 31; //추가: 부등호 (<)
	public static final int SEMI = 32; //추가: 세미콜론 (;)
	public static final int EXCLAMATION = 33; //추가: 느낌표 (!)
	public static final int RESERVED = 12; //추가: 예약어
	public static final int EOF = -1;
	
	public LexicalA(){ //생성자에서 파일과 관련된 작업을 합니다.
		 try{
			  //파일 객체 생성 (텍스트파일이 있는 폴더를 지정해 객체를 생성합니다.)
	          File file = new File("D:\\\\workspace\\\\me\\\\src\\\\lexical_analyzer\\\\input.txt");
	          //입력 스트림 생성
	          filereader = new FileReader(file);
	            
	        }catch (FileNotFoundException e) {
	            System.out.println("Error1:" + e);	//파일이 없으면 Error1: ~ 출력합니다.
	        }catch(IOException e){
	            System.out.println("Error2:" + e);	//내용 오류시 Error2: ~ 출력합니다.
	        }
	}
	
	/* lookup - 연산자와 괄호 혹은 다른 기호들을 찾을 수 있게 해주는 함수며 토큰을 반환합니다. */
	public int lookup(char ch) {	//해당 문자 ch에 알맞게 switch문으로 들어가 해당 문자를 찾습니다.
		switch (ch) {	//그리고 문자를 가져오고(addChar), nextToken 변수에 해당 토큰의 의미를 지닌 값을 넣습니다.
		case '(':		//과정이 끝나면 break으로 switch문을 빠져 나옵니다.
			addChar();
			nextToken = LEFT_PAREN;
			break;
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
		case '{':
			addChar();
			nextToken = LEFT_BRACE;
			break;
		case '}':
			addChar();
			nextToken = RIGHT_BRACE;
			break;
		case '=':
			addChar();
			nextToken = EQUAL;
			break;
		case '>':
			addChar();
			nextToken = GREATERTHAN;
			break;
		case '<':
			addChar();
			nextToken = LESSTHAN;
			break;
		case ';':
			addChar();
			nextToken = SEMI;
			break; 
		case '!':
			addChar();
			nextToken = EXCLAMATION;
			break;
		default:
			addChar();
			nextToken = EOF;
			System.out.println("EOF");
			break;
		}
		return nextToken;
	}
	
	/* addChar - 문자를 lexeme 배열에 추가할 수 있는 함수입니다. */
	public void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;//lexeme 배열에 nextChar을 대입한 후 전역변수 lexLen을 +1 해줍니다. 
			lexeme[lexLen] = 0;		//마지막 원소는 0을 대입합니다.
		}
		else
			System.out.println("Error - lexeme is too long \n");//lexLen이 99이상이 되면 너무 길다고 메세지를 출력합니다.
	}
	
	/* getChar - 다음 입력 문자를 가져오고 문자의 성질을 구별 해주는 함수입니다.*/
	public void getChar() throws IOException{	//아까 선언한 filereader로 read()함수를 불러 한 문자씩 읽고 singleCh에 내용이 있다면 대입합니다.
		if ((singleCh = filereader.read()) != -1) {	//singleCh에 내용이 있으면 if문에 들어갑니다.
			nextChar = (char)singleCh;			//singleCh를 비교하기 쉽게 문자형으로 바꿉니다.
			if (Character.isLetter(nextChar))	//만약 nextChar이 알파벳이면 charClass는 LETTER가 할당됩니다.
				charClass = LETTER;
			else if (Character.isDigit(nextChar)) //만약 nextChar이 숫자면 charClass는 DIGIT이 할당됩니다.
				charClass = DIGIT;
			else charClass = UNKNOWN; //만약 nextChar이 알파벳도 아니고 숫자도 아니면 charClass는 UNKNOWN이 할당됩니다.
		}
		else {
			charClass = EOF;	//만약 nextChar이 EOF면 charClass에 EOF이 할당됩니다.
		}
	}
	
	/* getNonBlank -  공백문자가 안나올때까지 getChar를 호출하는 함수입니다 */
	public void getNonBlank() throws IOException {
		while (Character.isWhitespace(nextChar) && (singleCh != -1))	//isWhitespace()로 nextChar가 공백이면 계속해서 getChar()호출합니다.
			getChar();					//singCh가 EOF가 아니면 빈칸을 없애주기로 했습니다. singleCh가 EOF면 빈칸을 계속 반복해 끝이나지 않습니다.
	} 
	
	public boolean isResvword(char buffer[]) {	//isResvword함수는 매개변수 char buffer[]가 배열을 받으면 그 배열이 예약어와 같은지 판별합니다. 
		String resvwords[][] = { {"auto"},{"break"},{"case"},{"char"},{"const"},{"continue"},{"default"}, 
				{"do"},{"double"},{"else"},{"enum"},{"extern"},{"float"},{"for"},{"goto"},
				{"if"},{"int"},{"long"},{"register"},{"return"},{"short"},{"signed"},
				{"sizeof"},{"static"},{"struct"},{"switch"},{"typedef"},{"union"},
				{"unsigned"},{"void"},{"volatile"},{"while"} }; //resvwords 2차원 배열에 32개의 예약어를 넣었습니다
		int i;
		boolean flag = false;	//flag 변수로 전달된 인자 buffer와 resvwords배열 중 하나의 예약어와 같으면 flag를 바꿉니다.
		
		for (i = 0; i < 32; ++i) {
			if (resvwords[i][0].equals(String.valueOf(buffer).trim())) {	//buffer가 string으로 바뀌고 공백을 제거합니다.
				flag = true;	//for문으로 resvwords의 32개 예약어를 탐색해서 한 후 만약 buffer와 equals로 비교해 같으면  
				break;		//flag=true; 로 설정해 flag를 반환(return)합니다.
			}	
		}

		return flag;
	}
	/* lex - 어휘분석기*/
	int lex() throws IOException {
		lexLen = 0;	//lexeme 배열에 저장되는 문자들은 index 0부터 시작합니다.
		lexeme = new char[100];	//lex()함수를 시작할때 마다 lexeme배열이 초기화 됨으로 lex()가 시작할때 마다 배열을 새로 만듭니다.
		getNonBlank();	//공백이 있으면 모두 넘겨줍니다.
		switch (charClass) {	//charClass의 값에 따라 LETTER(단어 + 숫자) -> 식별자 혹은 설정한 예약어
								//DIGIT -> 숫자, UNKNOWN -> 그외의 것들 로 나뉩니다.
		/* 식별자 및 예약어 구문분석 */
		case LETTER:
			addChar();	//문자를 가져오는 역할을 수행합니다.
			getChar();	//문자를 분류하는 역할을 수행합니다.
			while (charClass == LETTER || charClass == DIGIT) {	//계속 읽으며 LETTER나 DIGIT이 그 다음에 오면
				addChar();										//계속해서 문자를 읽습니다.
				getChar();
			}
			nextToken = IDENT;	//nextToken 은 IDENT 로 할당됩니다.
			if (isResvword(lexeme))	//만약 lexeme배열 안의 문자열이 예약어와 같다면 if문을 실행합니다.
				nextToken = RESERVED;	//nextToken은 예약어를 뜻하는 RESERVED로 할당됩니다.
			break;
		/* 정수 상수들 구문분석 */
		case DIGIT:
			addChar();
			getChar();
			while (charClass == DIGIT) {	//charClass가 DIGIT이 아닐때까지 계속 반복합니다.
				addChar();
				getChar();
			}
			nextToken = INT_LIT; //nextToken 은 INT_LIT 로 할당됩니다.
			break;
		/* 그외 괄호, 연산자, 세미콜론등을 구문분석 */
		case UNKNOWN:		//식별자나 숫자 이외의 것이 오면 lookup함수로 들어가 알맞은 기호를 찾습니다.
			lookup(nextChar);
			getChar();
			break;
			/* EOF */
		case EOF:		//EOF(End Of File), 파일의 끝이면 nextToken에 EOF로 정의된 값을 넣고 lexeme에
			nextToken = EOF;		//차례로 'E''O''F'를 넣고 break문을 만납니다. 
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;
			break;
		} /* switch문의 끝 */
		
		System.out.println("Next token is: "+ nextToken + ", Next lexeme is " +	//nextToken의 값과 문자열 lexeme를 출력합니다.
				String.valueOf(lexeme));
		return nextToken;	//nextToken값을 반환합니다.
	} /* lex함수의 끝 */


	public int getnextToken() {	//토큰을 의미하는 변수가 private으로 선언되어서 get함수로 nextToken을 반환합니다.
		return nextToken;
	}
}
