#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TABLE_SIZE 100

struct tnode {
	char *name;
	char *dfn;
	struct tnode *next;
} *buckets[TABLE_SIZE];

typedef struct tnode* pnode;


char* dup(char* s) {
	char *res;
	if ((res = malloc(strlen(s) + 1)) == NULL) {
		fprintf(stderr, "Failed to duplicate string: %s\n", s);
		return NULL;
	}
	strcpy(res, s);
	return res;
}

pnode nodealloc() {
	pnode res;
	if ((res = (pnode) malloc(sizeof(struct tnode))) == NULL) {
		fprintf(stderr, "Failed to allocated node");
	}
	return res;
}

int hash(char *s) {
	unsigned h = 0;
	for (; *s != '\0'; s++) {
		h = *s + 31 * h;
	}
	return h % TABLE_SIZE;
}

pnode lookup(char* name) {
	pnode n;
	for (n = buckets[hash(name)]; n != NULL; n = n->next) {
		if (strcmp(n->name, name) == 0) {
			return n;
		}
	}
	return NULL;
}

pnode insert(char *name, char *dfn) {
	pnode n;
	if ((n = lookup(name)) != NULL) {
		free(n->dfn);
		if ((n->dfn = dup(dfn)) == NULL) {
			return NULL;
		}
	} else {
		if ((n = nodealloc()) == NULL ||
			(n->name = dup(name)) == NULL ||
			(n->dfn = dup(dfn)) == NULL) {
			return NULL;
		}
		int ind = hash(name);
		n->next = buckets[ind];
		buckets[ind] = n;
	}
	return n;
}

int main(int argc, char **argv) {
	insert("a", "a");
	insert("b", "b");
	insert("a", "aa");

	pnode res;
	printf("a = %s\n", (res = lookup("a")) != NULL ? res->dfn : "<null>");
	printf("b = %s\n", (res = lookup("b")) != NULL ? res->dfn : "<null>");

	return 0;
}



