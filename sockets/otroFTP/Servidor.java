import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private static final int PUERTO = 5000;
	private ServerSocket server;
	private Socket cliente;

	public Servidor() {
		try {
			server = new ServerSocket(PUERTO);
			
			System.out.println("Servidor esperando en el puerto "+PUERTO);
			while(true) {
				cliente = server.accept(); //crea el objeto
				InputStream is = cliente.getInputStream();
				OutputStream os = cliente.getOutputStream();
				ClienteHandler ch = new ClienteHandler(cliente, is, os);
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
