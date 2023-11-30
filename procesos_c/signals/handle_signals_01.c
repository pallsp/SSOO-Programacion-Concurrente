#include <signal.h>
#include <unistd.h>
#include <stdio.h>

void signalHandler(int sig){
    printf("La señal recibida es: %d\n",sig);
}

//podemos ignorar señales
//IMPORTANTE: las señales SIGKILL y SIGSTOP no podemos cambiar su comportamiento por defecto
int main(){
    //con SIG_IGN le indicamos que ignore la señal
    signal(SIGALRM, SIG_IGN); 
    
    //en cambio, si recibe la señal SIGUSR1 ejecutará la función signalHandler()
    signal(SIGUSR1, &signalHandler);
    while(1){
        printf("... esperando recibir señales...\n");
        sleep(2);
    }

    return 0;
}