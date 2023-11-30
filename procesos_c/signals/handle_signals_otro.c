#include <signal.h>
#include <unistd.h>
#include <stdio.h>

void MySignalHandler(int sig){
    printf("Número de señal: %d\n", sig);
}

int main(){

    //el primer argumento es un entero que indica el número de la señal
    //el segundo argumento es una función, el handler
    signal(SIGIO, &MySignalHandler);
    //cuando reciba esa señal (SIGIO) voy a ejecutar esa función

    while(1){
        printf("zzzzzzz\n");
        sleep(2);
    }

    return 0;
}