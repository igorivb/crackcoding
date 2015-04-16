#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "keyword_counting.h"


struct key keytab[] = {
	"auto", 0,
	"break", 0,
	"case", 0,
	"char", 0,
	"const", 0,
	"continue", 0,
	"default", 0,
	/* ... */
	"unsigned", 0,
	"void", 0,
	"volatile", 0,
	"while", 0
};


int binarySearch(struct key[], char *);


int main_keyword_counting(int argc, char **argv) {

	char word[MAX_WORD];
	int ind;

	while ((getWord(word, MAX_WORD)) != EOF) {
		if (isalpha(word[0])) {
			printf("searching for: %s\n", word);

			if ((ind = binarySearch(keytab, word)) >= 0) {
				keytab[ind].count ++;
			}
		}
	}

	//print
	int i;
	for (i = 0; i < NKEYS; i ++) {
		if (keytab[i].count > 0) {
			printf("%s: %d\n", keytab[i].word, keytab[i].count);
		}

	}

	return 0;
}


int getWord(char* word, int len) {
	static char* words[] = {
		"hello", "auto", "world", "char", "auto", "auto"
	};

	static int num = sizeof(words) / sizeof(char*);

	static int wordCount = 0;

	if (wordCount < num) {

		//TODO: does it copy correctly? (issue could be because I don't use '\0')
		strcpy(word, words[wordCount]);

		return wordCount++;
	} else {
		return EOF;
	}
}

int binarySearch(struct key tab[], char *word) {
	int s = 0;
	int e = NKEYS;
	int mid;

	while (e - s > 0) {
		mid = (s + e) >> 1;
		int cmp = strcmp(word, tab[mid].word);
		if (cmp < 0) {
			e = mid;
		} else if (cmp > 0) {
			s = mid + 1;
		} else {
			return mid; //found
		}
	}

	return -1; //not found
}
