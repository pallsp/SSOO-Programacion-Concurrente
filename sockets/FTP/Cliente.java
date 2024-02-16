import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	private static final int PUERTO = 5000;
	private static final String HOST = "localhost"; //192.168.0.22
	private String mensaje;

	public Cliente() {
		Scanner sc = new Scanner(System.in);
		try (Socket s = new Socket(HOST, PUERTO);
				InputStream is = s.getInputStream();
				BufferedReader entrada = new BufferedReader(new InputStreamReader(is));
				OutputStream os = s.getOutputStream();
				PrintStream salida = new PrintStream(os);) {
			int option;
			do {
				System.out.println("------------------------------");
				System.out.println("0 - SALIR");
				System.out.println("1 - REGISTRAR USUARIO");
				System.out.println("2 - ENTRAR SERVIDOR FTP");
				System.out.println("------------------------------");
				System.out.println("Qué quieres hacer?");
				option = sc.nextInt();
				sc.nextLine();
				switch (option) {
				case 0:
					System.out.println("Saliendo..");
					break;
				case 1:
					System.out.println("Has elegido registrar un nuevo usuario");
					salida.println("REGISTRO"); // 0-envío registro
					salida.flush();
					// salida.close();
					mensaje = entrada.readLine(); // 1-recibo mensaje de registrar usuario
					System.out.println(mensaje);
					registrarUsuario(salida); // 2-envío credenciales
					break;
				case 2:
					System.out.println("Has elegido acceder al servidor");
					salida.println("SERVIDOR");
					salida.flush();
					mensaje = entrada.readLine();
					System.out.println(mensaje);
					autenticarUsuario(salida);
					mensaje = entrada.readLine();
					System.out.println(mensaje);
					acciones(s, entrada, salida);
					break;
				default:
					System.out.println("Opción no válida.");
				}
			} while (option != 0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void acciones(Socket s, BufferedReader entrada, PrintStream salida) {
		int option;
		String archivo;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("------------------------------");
			System.out.println("0 - SALIR");
			System.out.println("1 - LISTAR DIRECTORIO");
			System.out.println("2 - SUBIR ARCHIVO");
			System.out.println("3 - BAJAR ARCHIVO");
			System.out.println("------------------------------");
			System.out.println("Qué quieres hacer?");
			option = sc.nextInt();
			sc.nextLine();
			switch (option) {
			case 0:
				System.out.println("Saliendo..");
				break;
			case 1: // listar archivos
				System.out.println("LOS ARCHIVOS DEL SERVIDOR: ");
				salida.println("1");
				salida.flush();
				try {
					mensaje = entrada.readLine();
					System.out.println(mensaje);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2: // enviar un archivo
				System.out.println("SE SUBIRÁ UN ARCHIVO: ");
				salida.println("2"); // 1-envío opción
				salida.flush();
				System.out.println("Seleccione un archivo a subir: (introduzca el nombre)");
				listarDirectorio();
				archivo = sc.nextLine();
				sendFile(s, archivo);
				break;
			case 3: // recibir un archivo
				System.out.println("SE BAJARÁ UN ARCHIVO: ");
				salida.println("3"); // 1-envío opción
				salida.flush();
				try {
					mensaje = entrada.readLine(); //1.5-recibimos los archivos que hay en el servidor
					System.out.println(mensaje);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Seleccione un archivo a descargar: (introduzca el nombre)");
				archivo = sc.nextLine();
				salida.print(archivo); // 2-envío nombre archivo a descargar
				salida.flush();
				try {
					mensaje = entrada.readLine(); // 3-recibo el tamaño del archivo, que parsearé a un entero
					receiveFile(s, Integer.parseInt(mensaje), archivo); // 4-recibo el archivo
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Opción no válida. Pruebe de nuevo");
			}
		} while (option != 0);
	}

	private void receiveFile(Socket server, Integer sizeArchivo, String nombreArchivo) {
		try{
			FileOutputStream fos = new FileOutputStream("/home/pablo/FTP/servidorFTP/"+nombreArchivo);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			BufferedInputStream in = new BufferedInputStream(server.getInputStream());
			byte[] buffer = new byte[sizeArchivo];
			System.out.println("Leyendo contenido..");
			for(int i=0;i<buffer.length;i++) {
				buffer[i] = (byte) in.read();
			}
			System.out.println("Escribiendo el contenido..");
			out.write(buffer);
			out.flush();
			System.out.println("Archivo recibido: "+nombreArchivo);
			out.close();
			fos.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void sendFile(Socket server, String archivo) {
		File miArchivo = new File("/home/pablo/FTP/clienteFTP/" + archivo); // archivo a enviar
		try(
			FileInputStream fis = new FileInputStream("/home/pablo/FTP/clienteFTP/"+archivo);
			BufferedInputStream bis = new BufferedInputStream(fis);){
			DataOutputStream dout = new DataOutputStream(server.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(server.getOutputStream());
			//enviamos el nombre del archivo
			dout.writeUTF(miArchivo.getName());
			//enviamos el tamaño del archivo
			int tam = (int) miArchivo.length();
			dout.writeInt(tam);
			//enviamos el archivo
			byte[] buffer = new byte[tam];
			//leemos el archivo y lo introducimos en el array de bytes
			bis.read(buffer);
			//se realiza el envío
			for(int i=0;i<buffer.length;i++) {
				bos.write(buffer[i]);
			}
			bos.flush();
			System.out.println("Archivo enviado: "+miArchivo.getName());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void listarDirectorio() {
		File directorio = new File("/home/pablo/FTP/clienteFTP"); // ruta de mi directorio cliente
		File[] listaFich = directorio.listFiles();
		int i = 1;
		for (File f : listaFich) { // solo nos interesan los ficheros que no sean directorios
			if (f.isFile())
				System.out.println((i++) + " - " + f.getName());
		}
	}

	private void autenticarUsuario(PrintStream salida) {
		String user, password;
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce tus credenciales");
		System.out.print("Usuario: ");
		user = sc.nextLine();
		System.out.print("Contraseña: ");
		password = sc.nextLine();
		salida.println(user + " " + password);
		salida.flush();

	}

	private void registrarUsuario(PrintStream salida) {
		String user, password;
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce un nuevo usuario: ");
		System.out.print("Usuario: ");
		user = sc.nextLine();
		System.out.print("Contraseña: ");
		password = sc.nextLine();
		salida.println(user + " " + password); // 2-envío credenciales
		salida.flush();
		salida.close();
	}

	public static void main(String[] args) {
		new Cliente();
	}

}








/*
antiguo receiveFile()
try (FileOutputStream fos = new FileOutputStream("/home/pablo/FTP/clienteFTP/" + nombreArchivo);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				InputStream is = server.getInputStream();) {
			byte[] miByteArray = new byte[sizeArchivo];
			int bytesLeidos = is.read(miByteArray, 0, miByteArray.length);
			int bytesTotales = bytesLeidos;
			while (bytesTotales < sizeArchivo) {
				bytesLeidos = is.read(miByteArray, bytesTotales, (miByteArray.length - bytesTotales));
				if (bytesLeidos >= 0)
					bytesTotales += bytesLeidos;
			}
			bos.write(miByteArray, 0, bytesTotales);
			bos.flush();
			System.out.println(nombreArchivo + " descargado con éxito.");
		} catch (IOException e) {
			e.printStackTrace();
		}




*/
