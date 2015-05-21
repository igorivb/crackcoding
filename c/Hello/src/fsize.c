
#include <stdio.h>
#include <sys/stat.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <dirent.h>

//stat file

void dirwalk(char* dirname) {
	int fd;
	if ((fd = open(dirname, O_RDONLY, 0)) == -1) {
		fprintf(stderr, "Failed to open dir: %s\n", dirname);
		return;
	}

	//char buf[1000];
	//read(fd, buf, 1000);

//	struct direct dirbuf;
//	read(fd, (char *) &dirbuf, sizeof(dirbuf));
//
//	printf("Dir content: %s\n", buf);
//	if (dirbuf.d_ino != 0) { /* slot not in use */
//		printf(dirbuf.d_ino);
//		printf(dirbuf.d_name);
//	}


	//struct dirent *entry;
	//int readdir_r(DIR *dirp, struct dirent *entry, struct dirent **result);



	DIR *dirp = opendir(dirname);
	struct dirent *entry;
	while ((entry = readdir(dirp)) != NULL) {
		printf("directory. name: %s\n", entry->d_name);
	}


	if (fd) {
		close(fd);
	}
}

void fsize(char *name) {
	struct stat buf;
	if (stat(name, &buf)) {
		fprintf(stderr, "failed stat file: %s\n", name);
		return;
	}

	if ((buf.st_mode & S_IFMT) == S_IFDIR) { //directory
		dirwalk(name);
	} else {
		printf("File: %s\n", name);
		printf("size: %d\n", buf.st_size);
		printf("mode: %s\n", (buf.st_mode & S_IFMT) == S_IFDIR ? "directory" : "file");
	}
}

int main_fsize(int argc, char **argv) {
	//fsize("/home/hduser/tmp.sh");
	fsize("/home/hduser");

	return 0;
}



