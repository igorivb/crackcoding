#include <stdio.h>


int getLen(int);

void itoa1(int n, char c[], int start, int end) {
	int i, digit;
	for (i = end; i >= start; i --, n = n / 10) {
		digit = n % 10;
		c[i] = '0' + digit;
	}
}

void v1(int n) {
	int len = getLen(n);
	int size = len + 1;
	int start = 0;
	if (n < 0) {
		size ++;
		start = 1;
	}
	char c[size];
	c[size - 1] = '\0';
	if (n < 0) {
		c[0] = '-';
		n = -n;
	}

	itoa1(n, c, start, size - 2);

	printf("Num: %d, len: %d, string: %s", n, len, c);
}

void reverse(char c[]) {
	int len;
	for (len = 0; c[len] != '\0'; len ++);

	int i, m;
	char tmp;
	for (i = 0, m = len >> 1; i < m; i ++) {
		tmp = c[i];
		c[i] = c[len - i - 1];
		c[len - i - 1] = tmp;
	}
}

void itoa2(int n, char c[]) {
	int sign = 1;
	if (n < 0) {
		sign = -1;
		n = -n;
	}

	int i = 0;
	do {
		c[i ++] = '0' + (n % 10);
	} while ((n /= 10) != 0);

	if (sign == -1) {
		c[i ++] = '-';
	}
	c[i] = '\0';

	reverse(c);
}

void v2(int n) {
	int len = getLen(n) + 1;
	if (n < 0) {
		len ++;
	}

	char c[len];
	itoa2(n, c);

	printf("Num: %d, string: %s\n", n, c);
}

int main_itoa(int argc, char **argv) {
	int n = -125;

	//v1(n);
	v2(n);

	//printf("result: %d", strlen("aa"));

	return 0;
}

int getLen(int n) {
	int len;
	for (len = 1; (n = n / 10) != 0; len ++);
	return len;
}
