#include <stdio.h>

int main3(int argc, char **argv) {
	int c;
	long num;
	for (num = 0;(c = getchar()) != EOF; num ++) {
		//putchar(c);
		if (c != 10) { //ignore line ending
			printf("%ld. %d\n", num, c);
		}
	}

	return 0;
}
