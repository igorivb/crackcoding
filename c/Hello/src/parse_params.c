#include <stdio.h>

int main11(int argc, char **argv) {

	int xSetup = 0, nSetup = 0;
	int c;
	while (argc-- > 0) {
		if ((*++argv)[0] == '-') {
		//if (**++argv == '-') {

			//printf("simple: %c\n", *++argv[0]);
			//printf("simple: %s\n", argv[0]);

			while (c = *++argv[0]) {
				//printf("%c\n", *p);

				switch (c) {
				case 'x':
					xSetup = 1;
					printf("set X\n");
					break;
				case 'n':
					nSetup = 1;
					printf("set N\n");
					break;
				default:
					printf("error: unknown flag: %c\n", c);
				}
			}
			//printf("%c\n", *(p + 1));
		}

		//printf("%s\n", (*++argv));
		//printf("%c\n", (*++argv)[0]);
	}

	return 0;
}


int main_parse_params(int argc, char **argv) {
	int xSetup = 0, nSetup = 0;
	int c;

	printf("count: %d\n", argc);

	while (--argc > 0) {
		//printf("check: %s\n", *(argv+1));
		if ((*++argv)[0] == '-') {
			while (c = *++argv[0]) {
				switch (c) {
				case 'x':
					xSetup = 1;
					printf("set X\n");
					break;
				case 'n':
					nSetup = 1;
					printf("set N\n");
					break;
				default:
					printf("error: unknown flag: %c\n", c);
				}
			}
		} else {
			printf("Ignore param: %s\n", *argv);
		}
	}

	return 0;
}
