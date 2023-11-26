#include <stdio.h>

//kill -SIGSTOP PID --> pausar el proceso
//kill -SIGCONT PID --> reanudar el proceso
//kill -SIGKILL PID --> matar el proceso 

//en vez de introducir la señal en formato texto también se puede introducir su valor numérico
int main(){

    while(1){
        printf("zzzzzzz \n");
        sleep(2);
    }


    return 0;
}