#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
/* 전역 선언 */
/* 변수들 */
int charClass;
char lexeme[100];
char nextChar;
int lexLen;
int token;
int nextToken;
FILE *in_fp, *fopen();

/* 함수 선언 */
void addChar();
void getChar();
void getNonBlank();
int lex();
/* 캐릭터 클래스들 */
#define LETTER 0
#define DIGIT 1
#define UNKNOWN 99
/* 토큰 코드들 */
#define INT_LIT 10
#define IDENT 11
#define ASSIGN_OP 20
#define ADD_OP 21
#define SUB_OP 22
#define MULT_OP 23
#define DIV_OP 24
#define LEFT_PAREN 25
#define RIGHT_PAREN 26
#define LEFT_BRACE 27 //추가: 여는 괄호 ({)
#define RIGHT_BRACE 28 //추가: 닫는 괄호 (})
#define EQUAL 29 //추가: 등호 (=)
#define GREATERTHAN 30 //추가: 부등호 (>)
#define LESSTHAN 31 //추가: 부등호 (<)
#define SEMI 32 //추가: 세미콜론 (;)
#define EXCLAMATION 33 //추가: 느낌표 (!)
#define RESERVED 12 //추가: 예약어

/******************************************************/
/* 메인 */
int main() {
	/* 제가 만들고 지정한 input.txt 텍스트 파일을 열고 내용을 처리합니다. */
	if ((in_fp = fopen("input.txt", "r")) == NULL)
		printf("ERROR - cannot open front.in \n");	//파일에 값이 없다면 에러를 출력합니다.
	else {
		getChar();	//getChar()함수를 불러옵니다.
		do {
			lex();		//lex()를 부릅니다.
		} while (nextToken != EOF); //nextToken 이 EOF가 아닐때까지 계속해서 반복합니다.
	}
}
/*****************************************************/
/* lookup - 연산자와 괄호 혹은 다른 기호들을 찾을 수 있게 해주는 함수며 토큰을 반환합니다. */
int lookup(char ch) {	//해당 문자 ch에 알맞게 switch문으로 들어가 해당 문자를 찾습니다.
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
		break;
	}
	return nextToken;
}
/*****************************************************/
/* addChar - 문자를 lexeme 배열에 추가할 수 있는 함수입니다. */
void addChar() {
	if (lexLen <= 98) {
		lexeme[lexLen++] = nextChar;	//lexeme 배열에 nextChar을 대입한 후 전역변수 lexLen을 +1 해줍니다.
		lexeme[lexLen] = 0;
	}
	else
		printf("Error - lexeme is too long \n");	//lexLen이 99이상이 되면 너무 길다고 메세지를 출력합니다.
}
/*****************************************************/
/* getChar - 다음 입력 문자를 가져오고 문자의 성질을 구별 해주는 함수입니다.*/
void getChar() {
	if ((nextChar = getc(in_fp)) != EOF) {
		if (isalpha(nextChar))	//만약 nextChar이 알파벳이면 charClass는 LETTER가 할당됩니다.
			charClass = LETTER;
		else if (isdigit(nextChar)) //만약 nextChar이 숫자면 charClass는 DIGIT이 할당됩니다.
			charClass = DIGIT;
		else charClass = UNKNOWN; //만약 nextChar이 알파벳도 아니고 숫자도 아니면 charClass는 UNKNOWN이 할당됩니다.
	}
	else
		charClass = EOF;	//만약 nextChar이 EOF면 charClass에 EOF이 할당됩니다.
}
/*****************************************************/
/* getNonBlank -  공백문자가 안나올때까지 getChar를 호출하는 함수입니다 */
void getNonBlank() {
	while (isspace(nextChar))	//isspace()로 nextChar가 공백이면 계속해서 getChar()호출합니다.
		getChar();
}
int isResvword(char buffer[]) {	//isResvword함수는 매개변수 char buffer[]가 배열을 받으면 그 배열이 예약어와 같은지 판별합니다.
	char resvwords[32][10] = { "auto","break","case","char","const","continue","default",
		"do","double","else","enum","extern","float","for","goto",
		"if","int","long","register","return","short","signed",
		"sizeof","static","struct","switch","typedef","union",
		"unsigned","void","volatile","while" };	//resvwords 배열에 32개의 예약어를 넣었습니다
	int i, flag = 0;	//flag 변수로 전달된 인자 buffer와 resvwords배열 중 하나의 예약어와 같으면 flag를 바꿉니다.

	for (i = 0; i < 32; ++i) {
		if (strcmp(resvwords[i], buffer) == 0) {
			flag = 1;	//for문으로 resvwords의 32개 예약어를 탐색해서 한 후 만약 buffer와 strcmp로 비교해 같으면
			break;		//flag=1;로 설정해 반환(return)합니다.
		}
	}

	return flag;
}
/***************************************************** /
/* lex - 어휘분석기*/
int lex() {
	lexLen = 0;	//lexeme 배열에 저장되는 문자들은 index 0부터 시작합니다.
	getNonBlank();	//공백이 있으면 모두 넘겨줍니다.
	switch (charClass) {	//charClass의 값에 따라 LETTER(단어 + 숫자) -> 식별자 혹은 설정한 예약어
							//DIGIT -> 숫자, UNKNOWN -> 그외의 것들 로 나뉩니다.
		/* 식별자 및 예약어 구문분석 */
	case LETTER:
		addChar();	//문자를 가져오는 역할을 수행합니다.
		getChar();	//문자를 분류하는 역할을 수행합니다.
		while (charClass == LETTER || charClass == DIGIT) {	//계속 읽되 LETTER나 DIGIT이 그 다음에 오면
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
	case UNKNOWN:		//식별자나 숫자 이외의 것이 오면 lookup함수로 알맞은 기호를 찾습니다.
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
	printf("Next token is: %d, Next lexeme is %s\n",	//nextToken의 값과 문자열 lexeme를 출력합니다.
		nextToken, lexeme);
	return nextToken;	//nextToken값을 반환합니다.
} /* lex함수의 끝 */
