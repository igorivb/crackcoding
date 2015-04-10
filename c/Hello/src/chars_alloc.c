#include <stdlib.h>

#define MAX_SIZE 1000


char buf[MAX_SIZE];
char* bufPointer = buf;

char* alloc(int n) {
	if (buf + MAX_SIZE - bufPointer >= n) {
		bufPointer += n;
		return bufPointer - n;
	} else {
		return NULL; //no space: error
	}
}

void afree(char *p) {
	if (p >= buf && p <= bufPointer) {
		bufPointer = p;
	}
}
