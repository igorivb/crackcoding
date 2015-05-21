#include "common.h"
#include <pthread.h>

enum tstate {
	ALIVE, TERMINATED, JOINED
};

static struct {
	pthread_t tid;
	enum tstate state;
	int slp;

} *threads;



pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t threadDiedCondition = PTHREAD_COND_INITIALIZER;


static int numLive; //live or terminated, but not joined. Access with lock
static int totalThreads; //constant
static int numUnjoined = 0; //terminated but not joined. Access with lock

static void* threadFunc(void* param) {
	int idx = (int) param;
	int s;

	printf("Worker %2d sleeping secs %d\n", idx, threads[idx].slp);

	s = sleep(threads[idx].slp); //sleep
	if (s != 0) {
		errExit("Worker: probably was interrupted");
	}

	s = pthread_mutex_lock(&lock); //lock
	if (s != 0) {
		errExit("Worker: pthread_mutex_lock");
	}

	numUnjoined ++;
	threads[idx].state = TERMINATED;

	s = pthread_mutex_unlock(&lock); //unlock
	if (s != 0) {
		errExit("Worker: pthread_mutex_unlock");
	}

	s = pthread_cond_signal(&threadDiedCondition); //notify
	if (s != 0) {
		errExit("Worker: pthread_cond_signal");
	}

	return NULL;
}

int main(int argc, char **argv) {
	int i;

	int params[100];
	int size = 100;
	for (i = 0; i < size; i ++) {
		params[i] = rand() % 5 + 1;
	}

	int s;
	numLive = totalThreads = size;

	//create threads
	printf("Creating threads: %d\n", totalThreads);
	threads = calloc(totalThreads, sizeof(*threads));
	if (threads == NULL) {
		errExit("Failed to allocate threads");
	}
	int slp;
	for (i = 0; i < size; i ++) {
		//slp = atoi(argv[i + 1]);
		slp = params[i];

		threads[i].slp = slp;
		threads[i].state = ALIVE;

		int idx = i;
		/*
		 * TODO: Important: see how parameter is given to func. Otherwise
		 * if I pass pointer, then variable in cycle will be overridden by further values in for-cycle.
		 */
		s = pthread_create(&threads[i].tid, NULL, threadFunc, (void*) idx);
		if (s != 0) {
			errExit("pthread_create");
		}
	}

	//join threads
	while (numLive > 0) {
		s = pthread_mutex_lock(&lock); //lock
		if (s != 0) {
			errExit("pthread_mutex_lock");
		}

		while (numUnjoined == 0) {
			s = pthread_cond_wait(&threadDiedCondition, &lock); //wait for notification
			if (s != 0) {
				errExit("pthread_cond_wait");
			}
		}

		printf("Main notified on thread termination. NumUnjoined: %2d, numLive: %2d\n", numUnjoined, numLive);

		for (i = 0; i < totalThreads; i ++) { //find terminated threads
			if (threads[i].state == TERMINATED) {
				printf("Joining thread: %2d\n", i);
				s = pthread_join(threads[i].tid, NULL);
				if (s != 0) {
					errExit("pthread_join");
				}

				threads[i].state = JOINED;
				numUnjoined --;
				numLive --;
			}
		}

		s = pthread_mutex_unlock(&lock); //lock
		if (s != 0) {
			errExit("pthread_mutex_unlock");
		}
	}

	printf("Done!\n");

	return 0;
}

