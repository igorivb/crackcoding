/*
 * keyword_counting.h
 *
 *  Created on: Apr 10, 2015
 *      Author: hduser
 */

#ifndef KEYWORD_COUNTING_H_
#define KEYWORD_COUNTING_H_

#define MAX_WORD 100
//#define NKEYS sizeof(keytab) / sizeof(struct key)

struct key {
	char *word;
	int count;
};

#define NKEYS 11

int getWord(char*, int);

#endif /* KEYWORD_COUNTING_H_ */
