#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

struct tnode {
	char *word;
	int count;
	struct tnode *left, *right;
};

static struct tnode *root;

struct tnode* talloc(void) {
	return (struct tnode*) malloc(sizeof(struct tnode));
}

//TODO: caller must handle if it returns NULL: it means no memory
struct tnode* createNode(char* word) {
	struct tnode *node = talloc();
	if (node == NULL) {
		printf("ERROR: Failed to allocate memory to create tree node");
		return NULL;
	}

	node->word = (char*) malloc(strlen(word) + 1);
	if (node->word != NULL) {
		strcpy(node->word, word);
	} else {
		printf("ERROR: Failed to allocate memory to copy word: %s", word);
		return NULL;
	}

	node->count = 0;
	node->left = node->right = NULL;

	return node;
}

void addNodeToTree(char* word) {
	struct tnode *node = NULL;
	if (root != NULL) {
		//search node or place to insert it
		struct tnode *cur = root;

		int cmp;
		while (node == NULL) {
			if ((cmp = strcmp(word, cur->word)) == 0) {
				node = cur; //found node
			} else if (cmp < 0) {
				if (cur->left != NULL) {
					cur = cur->left;
				} else {
					node = createNode(word);
					cur->left = node;
				}
			} else {
				if (cur->right != NULL) {
					cur = cur->right;
				} else {
					node = createNode(word);
					cur->right = node;
				}
			}
		}
	} else {
		node = root = createNode(word);
	}

	node->count ++;
}

char* getWord2(void) {
	//add \0 to each word? No
	static char* words[] = {
		"hello", "auto", "world", "char", "auto", "auto", "hello", "char", "char", "char", "1sdsd"
	};
	static int wordIndex = 0;
	static int const wordsCount = sizeof(words) / sizeof(char*);

	return wordIndex < wordsCount ? words[wordIndex ++] : NULL;
}

void printTree(struct tnode* node) {
	if (node != NULL) {
		printTree(node->left);
		printf("%s: %d\n", node->word, node->count);
		printTree(node->right);
	}
}

int main_keyword_counting_any_words(int argc, char **argv) {
	char *word;
	while ((word = getWord2()) != NULL) {
		if (isalpha(word[0])) {
			addNodeToTree(word);
		}
	}
	printTree(root);

	return 0;
}

