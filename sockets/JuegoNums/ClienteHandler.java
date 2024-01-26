import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClienteHandler extends Thread{
	private Socket cliente;
	private Integer nCliente;
	private Juego j;
	
	public ClienteHandler(Integer nCliente, Socket cliente, Juego j) {
		super();
		this.nCliente = nCliente;
		this.cliente = cliente;
		this.j = j;
	}
	
	public void run() {
		try(DataOutputStream out = new DataOutputStream(cliente.getOutputStream()); 
				DataInputStream in = new DataInputStream(cliente.getInputStream());){
			int numero = in.readInt();
			
			if(nCliente == 0) {
				System.out.println("Número leído de jugador 1: "+numero);
				j.setNum1(numero);
			}
			else {
				System.out.println("Número leído de jugador 2: "+numero);
				j.setNum2(numero);
			}
			
			System.out.println(j.compare());
			//out.writeUTF(j.compare());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
