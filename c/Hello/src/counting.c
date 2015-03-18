#include <stdio.h>

#define OUT 0
#define IN 1

/* count lines, words, and characters in input */
int main4(int argc, char **argv) {
	int chars = 0;
	int words = 0;
	int lines = 0;
	int state = OUT;

	int c;

	while ((c = getchar()) != EOF) {
		chars ++;

		if (c == '\n') {
			lines ++;
		}

		if (c == ' ' || c == '\t' || c == '\n') {
			state = OUT;
		} else if (state == OUT) {
			state = IN;
			words ++;
		}
	}

	printf("chars: %d, words: %d, lines: %d", chars, words, lines);

	return 0;
}
