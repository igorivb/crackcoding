#include "common.h"
#include <pthread.h>

static void* threadFunc(void* param) {
	int* sl = (int*) param;
	printf("Hello World from thread: %d\n", *sl);

	int s = sleep(*sl);
	printf("Slept. Left seconds: %d\n", s);

	return param;
}

int main_thread_start(int argc, char **argv) {
	int s;

	printf("Before thread start\n");

	int sleep = 3;

	pthread_t t1;
	s = pthread_create(&t1, NULL, threadFunc, (void*) &sleep);
	if (s != 0) {
		errExitN(s, "pthread_create");
	}

	printf("After thread start\n");

	void* res;
	s = pthread_join(t1, &res); //join thread
	if (s != 0) {
		errExitN(s, "pthread_join");
	}

	printf("Successfully finished with result: %d\n", *((int*) res));

	return 0;
}

