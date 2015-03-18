#include <stdio.h>
#include <ctype.h>

//TODO: how to handle error ?
int atoi(char str[]);

int main_atoi(int argc, char **argv) {

	char str[] = " -  123 a ";
	printf("result: %d", atoi(str));

	return 0;
}

int skipWhiteSpaces(char c[], int i) {
	for (; isspace(c[i]); i ++);
	return i;
}

int atoi(char c[]) {
	int i = 0;
	int sign = 1;
	int res = 0;

	i = skipWhiteSpaces(c, i);

	if (c[i] == '\0') { //empty: error
		return 0;
	}

	if (c[i] == '+' || c[i] == '-') {
		if (c[i] == '-') {
			sign = -1;
		}
		i ++;
	}

	i = skipWhiteSpaces(c, i);

	if (c[i] == '\0') { //empty: error
		return 0;
	}

	for (; isdigit(c[i]); i ++) {
		res = 10 * res + (c[i] - '0');
	}

	return res * sign;
}

