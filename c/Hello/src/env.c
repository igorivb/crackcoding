#include <stdio.h>

extern char** environ;

int main(int argc, char **argv) {
	char** p = environ;
	for (; *p != NULL; p++) {
		printf("Var: %s\n", *p);
	}
}
