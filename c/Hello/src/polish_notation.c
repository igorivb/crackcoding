#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>

#define MAXOP 10 /* max size of operand or operator */
#define NUMBER 0
#define BUF_SIZE 10
#define STACK_SIZE 100

int getOp(char c[]);

//buffer
char getInputChar(void);
void putInputChar(char);

//stack
void push(double);
double pop(void);

int main(int argc, char **argv) {
	int type;
	char s[MAXOP];
	double op2;

	while ((type = getOp(s)) != EOF) {
		if (type == NUMBER) { //operand
			push(atof(s));
		} else if (type == '\n') { //show result
			printf("Result:\t%f\n", pop());
		} else if (type == '+') {
			op2 = pop();
			push(pop() + op2);
		} else if (type == '-') {
			op2 = pop();
			push(pop() - op2);
		} else if (type == '*') {
			op2 = pop();
			push(pop() * op2);
		} else if (type == '/') {
			op2 = pop();
			push(pop() / op2);
		}
	}

	return 0;
}

int getOp(char s[]) {
	int type;
	int c;
	int i = 0;

	for (c = getInputChar(); c == ' ' || c == '\t'; c = getInputChar()); //skip spaces

	if (isdigit(c)) { //number
		type = NUMBER;

		for (; isdigit(c); c = getInputChar()) { //read digits
			s[i ++] = c;
		}

		if (c == '.') {
			s[i ++] = '.';

			for (; isdigit(c); c = getInputChar()) { //read digits
				s[i ++] = c;
			}
		}

		putInputChar(c); // return char to input
	} else {
		type = c;
	}

	s[i] = '\0';
	return type;
}


//--- input buffer
char buf[BUF_SIZE];
int bufPos = 0;

char getInputChar(void) {
	if (bufPos - 1 >= 0) {
		return buf[-- bufPos];
	} else {
		return getchar(); //read from stdin
	}
}

void putInputChar(char c) {
	if (bufPos < BUF_SIZE) {
		buf[bufPos ++] = c;
	} else {
		printf("error: buffer overflow. Char: %x\n", c);
	}
}

//--- stack
double stack[STACK_SIZE];
int stackPos = 0;

void push(double num) {
	if (stackPos < STACK_SIZE) {
		printf("debug: push %.2f\n", num);
		stack[stackPos ++] = num;
	} else {
		printf("error: stack overflow. Num: %f\n", num);
	}
}

double pop(void) {
	if (stackPos -1 >= 0) {
		double num = stack[-- stackPos];
		printf("debug: pop %.2f\n", num);
		return num;
	} else {
		printf("error: stack underflow\n");
		return -1.0;
	}
}
