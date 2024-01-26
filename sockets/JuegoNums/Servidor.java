import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	static final int PUERTO = 5000;
	private Juego j;
	
	public Servidor() {
		j = new Juego();
		try {
			ServerSocket server = new ServerSocket(PUERTO);
			
			System.out.println("Servidor esperando en el puerto "+PUERTO);
			for(int i=0;i<2;i++) {
				Socket cliente = server.accept(); //crea el objeto
				ClienteHandler ch = new ClienteHandler(i,cliente,j);
				ch.start();	
			}
			
			/*
			 if(j.getRes()==1) {
				System.out.println("Gana jugador 1");
			}
			else if(j.getRes()==2) {
				System.out.println("Gana jugador 2");
			}
			else {
				System.out.println("empate");
			}
			 */
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new Servidor();

	}

}
