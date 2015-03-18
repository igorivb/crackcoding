#include <stdio.h>

float power(int x, int y) {
	if (y == 0) {
		return 1.0;
	} else if (y == 1) {
		return (float) x;
	} else if (y > 1) {
		int res = power(x * x, y >> 1);
		if ((y & 1) == 1) {
			res *= x;
		}
		return (float) res;
	} else { //y is negative
		return 1.0 / power(x, -y);
	}
}

int main_power(int argc, char **argv) {
	int i;
	for (i = 0; i <= 10; i ++) {
		printf("%2d. %.5f\n", i, power(2, -i));
	}
	return 0;
}
