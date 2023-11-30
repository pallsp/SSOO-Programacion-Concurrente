#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>


int main(){
    int fd1[2], fd2[2]; //dos descriptores
    char buffer[50]; //para almacenar los mensajes
    pid_t pid1, pid2; 

    pipe(fd1);
    pipe(fd2);

    pid1 = fork(); //creamos el hijo
    if(pid1 == -1){
        printf("Error. No se ha podido crear el proceso hijo..");
        exit(-1);
    }
    if(pid1 == 0){
        pid2 = fork(); //creamos el nieto
        if(pid2 == -1){
            printf("Error. No se ha podido crear el proceso hijo..");
            exit(-1);
        }
        if(pid2 == 0){ //proceso nieto
            read(fd2[0],buffer,sizeof(buffer));
            printf("Soy el nieto. Mensaje del hijo leído: %s\n",buffer);
            //close(fd2[0]);
            write(fd1[1],"Soy el nieto, tu hijo", sizeof("Soy el nieto, tu hijo"));
            //close(fd1[1]);
        }
        else{ //proceso hijo
            write(fd2[1],"Soy el hijo, tu padre",sizeof("Soy el hijo, tu padre"));
            //close(fd2[1]);
            read(fd1[0],buffer,sizeof(buffer));
            printf("Soy el hijo. Mensaje del abuelo leído: %s\n",buffer);
            //close(fd1[0]);
            wait(NULL); //espera a que finalice el proceso nieto para leer
            read(fd1[0],buffer,sizeof(buffer));
            printf("Soy el hijo. Mensaje del nieto leído: %s\n",buffer);
            //close(fd1[0]);
            write(fd2[1],"Soy el hijo, tu hijo",sizeof("Soy el hijo, tu hijo"));
            //close(fd2[1]);
        }
    }
    else{ //proceso abuelo
        write(fd1[1],"Soy el abuelo, tu padre",sizeof("Soy el abuelo, tu padre"));
        //close(fd1[1]);
        wait(NULL); //espera a que finalice el proceso hijo para leer 
        read(fd2[0],buffer,sizeof(buffer));
        printf("Soy el abuelo. Mensaje del hijo leído: %s\n", buffer);
    }



    return 0;
}