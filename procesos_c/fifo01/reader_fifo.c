#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){
    int fd, n;
    char buf[1024];

    fd = open("/tmp/mi_fifo",O_RDONLY); //lo abrimos en modo sólo lectura
    n = read(fd, buf, sizeof(buf)); //read nos devuelve el número de bytes leídos
    printf("Mensaje leído: %s\n", buf);
    printf("El número de bytes que hemos leído es: %d", n);
    
    close(fd);
    return 0;
}