#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){
    int fd;  
    char buf[] = "Esto es el mensaje que escribo en el fifo";

    //el primer argumento que le pasamos es una ruta donde situar el fifo
    //el segundo argumento que le pasamos es el modo o permisos que queremos darle
    mkfifo("/tmp/mi_fifo",0666); //permisos escritura y lectura, 0 para formato octal
    // mkfifo() devuelve un entero, si es -1 ha ocurrido un error

    //open nos devuelve un descriptor
    fd = open("/tmp/mi_fifo", O_WRONLY); //lo abrimos en modo escritura
    write(fd,buf,sizeof(buf));

    close(fd);
    return 0;
}