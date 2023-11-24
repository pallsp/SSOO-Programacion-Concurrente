#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(){
    pid_t pid, otroPid, pidNieto, pidHijo;
    pid = fork(); //creamos a padre

    if(pid == -1){
        printf("Se ha producido un error. No se ha podido crear el proceso hijo...");
        exit(-1);
    }
    if(pid == 0){
        otroPid = fork(); //creamos a nieto
        if(otroPid == -1){
            printf("No se ha podido crear el proceso nieto...");
            exit(-1);
        }
        if(otroPid == 0){ //proceso nieto
            printf("Soy el proceso nieto\n\t Mi PID es: %d, el pid de mi padre es: %d\n",getpid(),getppid());
        }
        else{ //proceso padre
            pidNieto = wait(NULL); //esperamos a que finalice el proceso nieto
            printf("Soy el proceso padre\n\t Mi PID es: %d, el pid de mi padre es: %d y el de mi hijo: %d\n",getpid(),getppid(),otroPid);
        }
    }
    else{ //proceso abuelo
        pidHijo = wait(NULL); //esperamos a que finalice el proceso padre
        printf("Soy el proceso abuelo\n\t Mi PID es: %d, el pid de mi hijo es: %d", getpid(), pid);
    }

    return 0;
}