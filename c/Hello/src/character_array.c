#include <stdio.h>

#define MAX_SIZE 1000

int readLine(char str[], int limit);
void copy(char src[], char dest[]);

int main_character_array() {
	int maxLen = 0;
	int len;
	char maxStr[MAX_SIZE];
	char str[MAX_SIZE];

	printf("Enter strings\n");

	while ((len = readLine(str, MAX_SIZE)) != 0) {
		if (len > maxLen) {
			maxLen = len;
			copy(str, maxStr);
		}
	}

	if (maxLen > 0) {
		printf("max str: %s, len: %d\n", maxStr, maxLen);
	}

	return 0;
}


int readLine(char str[], int limit) {
	char c;
	int i;

	for (i = 0; i < (limit - 1) && (c = getchar()) != EOF && c != '\n'; i ++) {
		str[i] = c;
	}
	str[i + 1] = '\0';

	return i;
}


void copy(char src[], char dest[]) {
	int i = 0;
	while ((dest[i] = src[i]) != '\0') {
		i ++;
	}
}
