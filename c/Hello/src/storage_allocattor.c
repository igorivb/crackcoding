#include <stdio.h>

//TODO #define NALLOC 1024 /* minimum #units to request */
#define NALLOC 4 /* minimum #units to request */

typedef long Align; /* for alignment to long boundary */



union header { /* block header */
	struct {
		union header *ptr; /* next block if on free list */
		unsigned size; /* size of this block */
	} s;
	Align x; /* force alignment of blocks */
};
typedef union header Header;

static Header base; /* empty list to get started */
static Header *freep = NULL; /* start of free list */

/* free: put block ap in free list */
void free(void *ap) {
	Header *blockPtr, *ptr;
	blockPtr = (Header *) ap - 1; /* point to block header */
	for (ptr = freep; !(blockPtr > ptr && blockPtr < ptr->s.ptr); ptr = ptr->s.ptr)
		if (ptr >= ptr->s.ptr && (blockPtr > ptr || blockPtr < ptr->s.ptr))
			break; /* freed block at start or end of arena */
	if (blockPtr + blockPtr->s.size == ptr->s.ptr) { /* join to upper nbr */
		blockPtr->s.size += ptr->s.ptr->s.size;
		blockPtr->s.ptr = ptr->s.ptr->s.ptr;
	} else
		blockPtr->s.ptr = ptr->s.ptr;
	if (ptr + ptr->s.size == blockPtr) { /* join to lower nbr */
		ptr->s.size += blockPtr->s.size;
		ptr->s.ptr = blockPtr->s.ptr;
	} else
		ptr->s.ptr = blockPtr;
	freep = ptr;
}

/* morecore: ask system for more memory */
static Header *morecore(unsigned nu) {
	char *cp, *sbrk(int);
	Header *up;
	if (nu < NALLOC)
		nu = NALLOC;
	cp = sbrk(nu * sizeof(Header));
	if (cp == (char *) -1) /* no space at all */
		return NULL;
	up = (Header *) cp;
	up->s.size = nu;
	free((void *) (up + 1));
	return freep;
}


/* malloc: general-purpose storage allocator */
void *my_malloc(unsigned nbytes) {
	Header *p, *prevp;
	Header *moreroce(unsigned);
	unsigned nunits;
	nunits = (nbytes + sizeof(Header) - 1) / sizeof(Header) + 1;
	if ((prevp = freep) == NULL) { /* no free list yet */
		base.s.ptr = freep = prevp = &base;
		base.s.size = 0;
	}
	for (p = prevp->s.ptr;; prevp = p, p = p->s.ptr) {
		if (p->s.size >= nunits) { /* big enough */
			if (p->s.size == nunits) { /* exactly */
				prevp->s.ptr = p->s.ptr;
			} else { /* allocate tail end */
				p->s.size -= nunits;
				p += p->s.size;
				p->s.size = nunits;
			}
			freep = prevp;
			return (void *) (p + 1);
		}
		if (p == freep) { /* wrapped around free list */
			if ((p = morecore(nunits)) == NULL) {
				return NULL; /* none left */
			}
		}
	}
}

int main_storage_allocattor(int argc, char **argv) {
	my_malloc(3);


	return 0;
}






