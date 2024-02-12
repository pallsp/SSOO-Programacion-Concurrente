import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	private static final int PUERTO = 5000;
	private static final String HOST = "localhost";
	
	public Cliente() {
		Scanner sc = new Scanner(System.in);
		try {
			Socket server = new Socket(HOST, PUERTO);
			InputStream is = server.getInputStream();
			OutputStream os = server.getOutputStream();
			//recibir
			int tam = is.read();
			System.out.println("Tamaño leido: "+tam);
			byte[]contenido = new byte[tam];
			is.read(contenido);
			String mensaje = new String(contenido), enviar;
			System.out.println("Mensaje recibido del servidor: "+mensaje);
			int option;
			do {
				System.out.println("-------------------------");
				System.out.println("0 - SALIR");
				System.out.println("1 - REGISTRAR USUARIO");
				System.out.println("2 - ENTRAR SERVIDOR FTP");
				System.out.println("-------------------------");
				System.out.println("Qué quieres hacer?");
				option = sc.nextInt();
				sc.nextLine();
				switch(option) {
					case 0:
						System.out.println("HAS ELEGIDO SALIR");
						//envío opción al servidor
						os.write(0); 
						os.flush();
						System.out.println("Saliendo..");
						break;
					case 1:
						System.out.println("HAS ELEGIDO REGISTRAR UN USUARIO");
						//envío opción al servidor
						os.write(1);
						os.flush();
						//registro usuario
						registrarUsuario(os);
						break;
					case 2:
						break;
					default:
						System.out.println("Opción incorrecta. Pruebe otra vez");
				}
			}while(option!=0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void registrarUsuario(OutputStream os) {
		PrintStream salida = new PrintStream(os);
		Scanner sc = new Scanner(System.in);
		//introducción de credenciales
		System.out.println("Vamos a registrar un nuevo usuario: ");
		String user, password;
		System.out.print("Introduce un nuevo usuario: ");
		user = sc.nextLine();
		System.out.print("Introduce su contraseña: ");
		password = sc.nextLine();
		byte[] contenido;
		
		//envío de credenciales a servidor
		
			//usuario
			contenido = user.getBytes();
			//os.write(contenido.length); //envío tamaño
			//os.flush();
			//os.write(contenido); //envío contenido
			//os.flush();
			salida.println(user);
			salida.flush();
			//password
			contenido = password.getBytes();
			//os.write(contenido.length); //envío tamaño
			//os.flush();
			//os.write(contenido); //envío contenido
			//os.flush();
			salida.println(password);
			salida.flush();	
		System.out.println("Usuario "+user+" registrado correctamente.");
		//salida.close();
	}

	public static void main(String[] args) {
		new Cliente();

	}

}




/*
 
 System.out.println("HAS ELEGIDO MANDAR UN ARCHIVO");
						//envío opción al servidor
						os.write(1);
						os.flush();
						//enviar 
						File f = new File("/home/pablo/FTP/clienteFTP/archivo.txt");
						FileInputStream fis = new FileInputStream(f);
						byte[] contenidoBinario = new byte[(int) f.length()];
						fis.read(contenidoBinario);
						tam = (int) f.length();
						System.out.println("Enviando tamaño");
						os.write(tam);
						os.flush();
						System.out.println("Enviando contenido");
						os.write(contenidoBinario);
						os.flush();
 
 
 
 */
