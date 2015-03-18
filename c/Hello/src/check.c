#include <stdio.h>

 /* If this prototype is provided, the compiler will catch the error in
  * int main(). If it is omitted, then the error may go unnoticed.
  */
 //int myfunction1(int n);              /* Prototype */

 //double myfunction2(int);




 int main(void) {             /* Calling function */

	 double myfunction2(int);

     printf("%f\n", myfunction2(12));   /* Error: forgot argument to myfunction */
     return 0;
 }



 double myfunction2(int n) {             /* Called function definition */
//     if (n == 0)
//         return 1;
//     else
//         return n * myfunction1(n - 1);

	 return 1.0;
 }
