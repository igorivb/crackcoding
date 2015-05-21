#include <stdio.h>

void errExit(char* msg) {
	fprintf(stderr, "%s\n", msg);
	exit(1);
}

void errExitN(int error, char* msg) {
	fprintf(stderr, "Error: %d, msg: %s\n", error, msg);
	exit(error);
}

