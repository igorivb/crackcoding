#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "keyword_counting.h"


extern struct key keytab[];

struct key* binarySearchP(char *, struct key*, int);


int main(int argc, char **argv) {

	char word[MAX_WORD];
	struct key* ind;

	while ((getWord(word, MAX_WORD)) != EOF) {
		if (isalpha(word[0])) {
			printf("searching for: %s\n", word);

			if ((ind = binarySearchP(word, keytab, NKEYS)) != NULL) {
				ind->count ++;
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

struct key* binarySearchP(char *word, struct key* tab, int n) {
	struct key *s = &tab[0];
	struct key *e = &tab[n - 1];
	struct key* mid;

	while (s <= e) {
		mid = s + (e - s) / 2;
		int cmp = strcmp(word, mid->word);
		if (cmp < 0) {
			e = mid - 1;
		} else if (cmp > 0) {
			s = mid + 1;
		} else {
			return mid; //found
		}
	}

	return NULL; //not found
}
