#include <stdio.h>
#define N 10
char str2[N]={"Hello"};

struct s {
	int a;
	char v;
} s_val = {1, 'c'}, *sp;

int main_size(){
    printf("sizeof(str2): %d bytes\n", sizeof(str2));
    printf("sizeof(&str2): %d bytes\n", sizeof(&str2));

    printf("sizeof(&str2): %d bytes\n", sizeof(struct s));
    printf("sizeof(&str2): %d bytes\n", sizeof(s_val));
    printf("sizeof(&str2): %d bytes\n", sizeof(sp));
    return 0;
}
