#include "common.h"

#define TABLE_SIZE 100

struct tnode {
	char* name;
	char* dfn;
	struct tnode *next;
} *buckets[TABLE_SIZE];

typedef struct tnode* pnode;

char* dup_my(char* s) {
	char* res;
	if ((res = malloc(strlen(s) + 1)) == null) {
		fprintf(stderr, "Failed to duplicate string: %s\n", s);
		return null;
	}
	strcpy(res, s);
	return res;
}

static pnode nodealloc() {
	pnode res;
	if ((res = (pnode) malloc(sizeof(struct tnode))) == null) {
		fprintf(stderr, "Failed to allocated node");
	}
	return res;
}

int hash(char* s) {
	unsigned h = 0;
	for (; *s != '\0'; s++) {
		h = *s + 31 * h;
	}
	return h % TABLE_SIZE;
}

pnode lookup(char* name) {
	pnode n;
	for (n = buckets[hash(name)]; n != null; n = n->next) {
		if (strcmp(n->name, name) == 0) {
			return n;
		}
	}
	return null;
}

pnode insert(char* name, char* dfn) {
	pnode n;
	if ((n = lookup(name)) != null) {
		free(n->dfn);
		if ((n->dfn = dup_my(dfn)) == null) {
			return null;
		}
	} else {
		if ((n = nodealloc()) == null ||
			(n->name = dup_my(name)) == null ||
			(n->dfn = dup_my(dfn)) == null) {
			return null;
		}
		int ind = hash(name);
		n->next = buckets[ind];
		buckets[ind] = n;
	}
	return n;
}

int main_table_lookup(int argc, char **argv) {
	insert("a", "a");
	insert("b", "b");
	insert("a", "aa");

	pnode res;
	printf("a = %s\n", (res = lookup("a")) != null ? res->dfn : "<null>");
	printf("b = %s\n", (res = lookup("b")) != null ? res->dfn : "<null>");

	return 0;
}



