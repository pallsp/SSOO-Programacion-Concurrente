import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	static final String HOST = "localhost";
	static final int NUM_PARTIDAS = 5;
	static final int PUERTO = 5000;
	
	public Cliente() {
		try(Socket s = new Socket(HOST, PUERTO);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream is = new DataInputStream(s.getInputStream());) {
			Scanner sc = new Scanner(System.in);
			String resultado;
			
			
			System.out.println("Vamos a jugar ");
			int numero = (int)(Math.random()*10+1);
			System.out.println("Número generado: "+numero);
			out.writeInt(numero);
			resultado = is.readUTF();
			
			
			/*
			for(int i=0;i<NUM_PARTIDAS;i++) {
				
				
				
				 while((resultado = is.readUTF()) == null) {
					resultado = is.readUTF();
				}
				 
				
				
				//System.out.println(resultado);
			} 
			*/
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Cliente();

	}

}
