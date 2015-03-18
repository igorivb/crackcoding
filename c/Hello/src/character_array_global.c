#include <stdio.h>
#include "character_array_global.h"

//#define MAX_SIZE 1000
//
//int maxLen = 0;
//char str[MAX_SIZE];
//char maxStr[MAX_SIZE];
//
//int readLine_global(void);
//void copy_global(void);


int main_character_array_global() {
	extern int maxLen;
	extern char maxStr[];
	int len;

	printf("Enter strings for global\n");

	while ((len = readLine_global()) != 0) {
		if (len > maxLen) {
			maxLen = len;
			copy_global();
		}
	}

	if (maxLen > 0) {
		printf("max str: %s, len: %d\n", maxStr, maxLen);
	}

	return 0;
}


int readLine_global() {
	extern char str[];

	char c;
	int i;

	for (i = 0; i < (MAX_SIZE - 1) && (c = getchar()) != EOF && c != '\n'; i ++) {
		str[i] = c;
	}
	str[i + 1] = '\0';

	return i;
}


void copy_global(void) {
	extern char str[], maxStr[];

	int i = 0;
	while ((maxStr[i] = str[i]) != '\0') {
		i ++;
	}
}
