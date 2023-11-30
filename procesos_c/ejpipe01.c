#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(){
    int fd[2]; //descriptores
    char buffer[30]; //buffer con 30 carácteres
    pid_t pid;

    pipe(fd); //creamos el pipe
    pid = fork();

    if(pid == -1){
        printf("Error. No se ha podido crear el proceso hijo..");
        exit(-1);
    }
    if(pid == 0){
        printf("Soy el proceso hijo y escribo en el pipe\n");
        write(fd[1],"Hola papa",sizeof("Hola papa"));
        
    }
    else{
        close(fd[1]); //cerramos el descriptor de escritura
        wait(NULL); //esperamos a que finalice el proceso hijo
        printf("Soy el proceso padre y leo del pipe\n");
        read(fd[0],buffer,sizeof(buffer));
        printf("Mensaje leído: %s\n", buffer);
        close(fd[0]); //cerramos el descriptor de lectura
    }

    return 0;
}