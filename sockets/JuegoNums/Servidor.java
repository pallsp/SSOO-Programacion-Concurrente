
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
				ClienteHandler ch = new ClienteHandler(i+1,cliente,j);
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
