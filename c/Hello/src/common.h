/*
 * common.h
 *
 *  Created on: Apr 20, 2015
 *      Author: hduser
 */

#ifndef COMMON_H_
#define COMMON_H_

//use null like in Java
#define null NULL

//common includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

//allows to use String instead of char*
typedef char* String;

void errExit(char* msg);

void errExitN(int error, char* msg);


#endif /* COMMON_H_ */
