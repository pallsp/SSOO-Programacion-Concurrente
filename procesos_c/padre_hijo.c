#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(){
    pid_t pid, pidHijo;
    pid = fork();

    if(pid == -1){
        printf("No se ha podido crear proceso hijo...");
        exit(-1);
    }
    
    if(pid == 0){
        printf("Soy el proceso hijo: \n\t Mi PID es: %d, el PID de mi padre es: %d\n",getpid(),getppid());
    }

    else{
        pidHijo = wait(NULL); //esperamos a que termine el proceso hijo
        printf("Soy el proceso padre:\n\t Mi PID es: %d, el pid de mi hijo es: %d",getpid(),pid);
    }



    return 0;
}