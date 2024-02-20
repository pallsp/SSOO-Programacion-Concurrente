package Modelo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private static final int PUERTO = 5000;
	private ServerSocket server;
	private Socket cliente;
	private Juego j; //recurso compartido
	
	public Servidor() {

		try {
			server = new ServerSocket(PUERTO);
			System.out.println("Arrancando juego..");
			j = new Juego(5,3); //dimension del tablero y numero de barcos 
			System.out.println("Servidor esperando en el puerto "+PUERTO);
			
			//aceptamos sólo dos clientes
			//EMPEZARÁ SIEMPRE EL JUGADOR 1
			for(int i=0;i<2;i++) {
				cliente = server.accept(); 
				ClienteHandler ch = new ClienteHandler(cliente, j, i); //le envío el socket, el juego y el id del cliente
				ch.start();	
			}
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new Servidor();

	}

}
