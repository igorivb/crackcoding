#include <stdio.h>

#define LOWER 0
#define UPPER 300
#define STEP 20

int main2() {

	float i;
	float celsius;
	printf("------------\n");
	for (i = LOWER; i < UPPER; i += STEP) {
		//float celsius = 5.0 * (i-32.0) / 9.0;
		celsius = (5.0/9.0) * (i-32.0);
		printf("%3.0f | %3.1f\n", i, celsius);

	}
	printf("------------\n");

	return 0;
}
