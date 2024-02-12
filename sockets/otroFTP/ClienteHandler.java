import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteHandler extends Thread{
	private Socket cliente;
	private InputStream is;
	private OutputStream os;
	
	public ClienteHandler(Socket cliente, InputStream is, OutputStream os) {
		this.cliente = cliente;
		this.is = is;
		this.os = os;
	}
	
	public void run() {
		System.out.println("Cliente conectado: "+cliente.getInetAddress().getHostAddress());
		Scanner sc = new Scanner(System.in);
		String mensaje;
		byte[] contenido;
		int tam, opcion;
		boolean seguir = true;
		try {
			//enviar mensaje bienvenida
			System.out.println("Introduce un mensaje a enviar: ");
			mensaje = sc.nextLine();
			contenido = mensaje.getBytes();
			tam = contenido.length;
			System.out.println("Enviando tamaño: "+tam);
			os.write(tam);
			os.flush();
			System.out.println("Enviando contenido: "+mensaje);
			os.write(contenido);
			os.flush();
			System.out.println("Esperando respuesta...");
			
			while(seguir) {
				opcion = is.read();
				System.out.println("Opción leída: "+opcion);
				switch(opcion) {
					case 0: //cliente elige salir
						System.out.println("El cliente ha elegido cerrar la conexión.");
						System.out.println("Cerrando conexión..");
						seguir = false;
						break;
					case 1: //cliente elige registrarse
						System.out.println("El cliente ha elegido registrarse.");
						System.out.println("Registrando..");
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String user, password;
						user = br.readLine();
						password = br.readLine();
						BufferedWriter bw = new BufferedWriter(new FileWriter("/home/pablo/FTP/datosUsuarios/datos.txt", true));
						String linea = String.format("%s  %s", user, password);
						bw.write(linea);
						System.out.println("Usuario registrado correctamente.");
						bw.close();
						br.close();
						break;
					case 2:
						break;
				}
				/*
				else {
					System.out.println("El cliente ha elegido enviar un archivo.");
					FileOutputStream fos = new FileOutputStream("/home/pablo/FTP/servidorFTP/archivoServidor.txt");
					//recibir
					tam = is.read();
					System.out.println("Tamaño leído: "+tam);
					contenido = new byte[tam];
					is.read(contenido);
					mensaje = new String(contenido);
					System.out.println("Contenido recibido: "+mensaje);
					fos.write(contenido);
					fos.flush();
				} */
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
