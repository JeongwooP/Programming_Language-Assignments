package lexical_analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LexicalA {
	/* �������� private �ڷ������� �����մϴ�.*/
	private int charClass;
	private char []lexeme;
	private char nextChar;
	private int lexLen=0;
	private int token;
	private int nextToken;
	private int singleCh = 0;
	private FileReader filereader;
	
	/* �� ������ Ŭ�������� �з��� �� �ְ� �����߽��ϴ�.*/
	public static final int LETTER = 0;
	public static final int DIGIT = 1;
	public static final int UNKNOWN = 99;
	/* ��ū �ڵ���Դϴ�.*/
	public static final int INT_LIT = 10;
	public static final int IDENT = 11;
	public static final int ASSIGN_OP = 20;
	public static final int ADD_OP = 21;
	public static final int SUB_OP = 22;
	public static final int MULT_OP = 23;
	public static final int DIV_OP = 24;
	public static final int LEFT_PAREN = 25;
	public static final int RIGHT_PAREN = 26;
	public static final int LEFT_BRACE = 27; //�߰�: ���� ��ȣ ({) 
	public static final int RIGHT_BRACE = 28; //�߰�: �ݴ� ��ȣ (})
	public static final int EQUAL = 29; //�߰�: ��ȣ (=)
	public static final int GREATERTHAN = 30; //�߰�: �ε�ȣ (>)
	public static final int LESSTHAN = 31; //�߰�: �ε�ȣ (<)
	public static final int SEMI = 32; //�߰�: �����ݷ� (;)
	public static final int EXCLAMATION = 33; //�߰�: ����ǥ (!)
	public static final int RESERVED = 12; //�߰�: �����
	public static final int EOF = -1;
	
	public LexicalA(){ //�����ڿ��� ���ϰ� ���õ� �۾��� �մϴ�.
		 try{
			  //���� ��ü ���� (�ؽ�Ʈ������ �ִ� ������ ������ ��ü�� �����մϴ�.)
	          File file = new File("D:\\\\workspace\\\\me\\\\src\\\\lexical_analyzer\\\\input.txt");
	          //�Է� ��Ʈ�� ����
	          filereader = new FileReader(file);
	            
	        }catch (FileNotFoundException e) {
	            System.out.println("Error1:" + e);	//������ ������ Error1: ~ ����մϴ�.
	        }catch(IOException e){
	            System.out.println("Error2:" + e);	//���� ������ Error2: ~ ����մϴ�.
	        }
	}
	
	/* lookup - �����ڿ� ��ȣ Ȥ�� �ٸ� ��ȣ���� ã�� �� �ְ� ���ִ� �Լ��� ��ū�� ��ȯ�մϴ�. */
	public int lookup(char ch) {	//�ش� ���� ch�� �˸°� switch������ �� �ش� ���ڸ� ã���ϴ�.
		switch (ch) {	//�׸��� ���ڸ� ��������(addChar), nextToken ������ �ش� ��ū�� �ǹ̸� ���� ���� �ֽ��ϴ�.
		case '(':		//������ ������ break���� switch���� ���� ���ɴϴ�.
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
	
	/* addChar - ���ڸ� lexeme �迭�� �߰��� �� �ִ� �Լ��Դϴ�. */
	public void addChar() {
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;//lexeme �迭�� nextChar�� ������ �� �������� lexLen�� +1 ���ݴϴ�. 
			lexeme[lexLen] = 0;		//������ ���Ҵ� 0�� �����մϴ�.
		}
		else
			System.out.println("Error - lexeme is too long \n");//lexLen�� 99�̻��� �Ǹ� �ʹ� ��ٰ� �޼����� ����մϴ�.
	}
	
	/* getChar - ���� �Է� ���ڸ� �������� ������ ������ ���� ���ִ� �Լ��Դϴ�.*/
	public void getChar() throws IOException{	//�Ʊ� ������ filereader�� read()�Լ��� �ҷ� �� ���ھ� �а� singleCh�� ������ �ִٸ� �����մϴ�.
		if ((singleCh = filereader.read()) != -1) {	//singleCh�� ������ ������ if���� ���ϴ�.
			nextChar = (char)singleCh;			//singleCh�� ���ϱ� ���� ���������� �ٲߴϴ�.
			if (Character.isLetter(nextChar))	//���� nextChar�� ���ĺ��̸� charClass�� LETTER�� �Ҵ�˴ϴ�.
				charClass = LETTER;
			else if (Character.isDigit(nextChar)) //���� nextChar�� ���ڸ� charClass�� DIGIT�� �Ҵ�˴ϴ�.
				charClass = DIGIT;
			else charClass = UNKNOWN; //���� nextChar�� ���ĺ��� �ƴϰ� ���ڵ� �ƴϸ� charClass�� UNKNOWN�� �Ҵ�˴ϴ�.
		}
		else {
			charClass = EOF;	//���� nextChar�� EOF�� charClass�� EOF�� �Ҵ�˴ϴ�.
		}
	}
	
	/* getNonBlank -  ���鹮�ڰ� �ȳ��ö����� getChar�� ȣ���ϴ� �Լ��Դϴ� */
	public void getNonBlank() throws IOException {
		while (Character.isWhitespace(nextChar) && (singleCh != -1))	//isWhitespace()�� nextChar�� �����̸� ����ؼ� getChar()ȣ���մϴ�.
			getChar();					//singCh�� EOF�� �ƴϸ� ��ĭ�� �����ֱ�� �߽��ϴ�. singleCh�� EOF�� ��ĭ�� ��� �ݺ��� ���̳��� �ʽ��ϴ�.
	} 
	
	public boolean isResvword(char buffer[]) {	//isResvword�Լ��� �Ű����� char buffer[]�� �迭�� ������ �� �迭�� ������ ������ �Ǻ��մϴ�. 
		String resvwords[][] = { {"auto"},{"break"},{"case"},{"char"},{"const"},{"continue"},{"default"}, 
				{"do"},{"double"},{"else"},{"enum"},{"extern"},{"float"},{"for"},{"goto"},
				{"if"},{"int"},{"long"},{"register"},{"return"},{"short"},{"signed"},
				{"sizeof"},{"static"},{"struct"},{"switch"},{"typedef"},{"union"},
				{"unsigned"},{"void"},{"volatile"},{"while"} }; //resvwords 2���� �迭�� 32���� ���� �־����ϴ�
		int i;
		boolean flag = false;	//flag ������ ���޵� ���� buffer�� resvwords�迭 �� �ϳ��� ������ ������ flag�� �ٲߴϴ�.
		
		for (i = 0; i < 32; ++i) {
			if (resvwords[i][0].equals(String.valueOf(buffer).trim())) {	//buffer�� string���� �ٲ�� ������ �����մϴ�.
				flag = true;	//for������ resvwords�� 32�� ���� Ž���ؼ� �� �� ���� buffer�� equals�� ���� ������  
				break;		//flag=true; �� ������ flag�� ��ȯ(return)�մϴ�.
			}	
		}

		return flag;
	}
	/* lex - ���ֺм���*/
	int lex() throws IOException {
		lexLen = 0;	//lexeme �迭�� ����Ǵ� ���ڵ��� index 0���� �����մϴ�.
		lexeme = new char[100];	//lex()�Լ��� �����Ҷ� ���� lexeme�迭�� �ʱ�ȭ ������ lex()�� �����Ҷ� ���� �迭�� ���� ����ϴ�.
		getNonBlank();	//������ ������ ��� �Ѱ��ݴϴ�.
		switch (charClass) {	//charClass�� ���� ���� LETTER(�ܾ� + ����) -> �ĺ��� Ȥ�� ������ �����
								//DIGIT -> ����, UNKNOWN -> �׿��� �͵� �� �����ϴ�.
		/* �ĺ��� �� ����� �����м� */
		case LETTER:
			addChar();	//���ڸ� �������� ������ �����մϴ�.
			getChar();	//���ڸ� �з��ϴ� ������ �����մϴ�.
			while (charClass == LETTER || charClass == DIGIT) {	//��� ������ LETTER�� DIGIT�� �� ������ ����
				addChar();										//����ؼ� ���ڸ� �н��ϴ�.
				getChar();
			}
			nextToken = IDENT;	//nextToken �� IDENT �� �Ҵ�˴ϴ�.
			if (isResvword(lexeme))	//���� lexeme�迭 ���� ���ڿ��� ������ ���ٸ� if���� �����մϴ�.
				nextToken = RESERVED;	//nextToken�� ���� ���ϴ� RESERVED�� �Ҵ�˴ϴ�.
			break;
		/* ���� ����� �����м� */
		case DIGIT:
			addChar();
			getChar();
			while (charClass == DIGIT) {	//charClass�� DIGIT�� �ƴҶ����� ��� �ݺ��մϴ�.
				addChar();
				getChar();
			}
			nextToken = INT_LIT; //nextToken �� INT_LIT �� �Ҵ�˴ϴ�.
			break;
		/* �׿� ��ȣ, ������, �����ݷе��� �����м� */
		case UNKNOWN:		//�ĺ��ڳ� ���� �̿��� ���� ���� lookup�Լ��� �� �˸��� ��ȣ�� ã���ϴ�.
			lookup(nextChar);
			getChar();
			break;
			/* EOF */
		case EOF:		//EOF(End Of File), ������ ���̸� nextToken�� EOF�� ���ǵ� ���� �ְ� lexeme��
			nextToken = EOF;		//���ʷ� 'E''O''F'�� �ְ� break���� �����ϴ�. 
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;
			break;
		} /* switch���� �� */
		
		System.out.println("Next token is: "+ nextToken + ", Next lexeme is " +	//nextToken�� ���� ���ڿ� lexeme�� ����մϴ�.
				String.valueOf(lexeme));
		return nextToken;	//nextToken���� ��ȯ�մϴ�.
	} /* lex�Լ��� �� */


	public int getnextToken() {	//��ū�� �ǹ��ϴ� ������ private���� ����Ǿ get�Լ��� nextToken�� ��ȯ�մϴ�.
		return nextToken;
	}
}
