import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClienteHandler extends Thread{
	private Socket cliente;
	private String direccion;
	
	public ClienteHandler(Socket cliente, String direccion) {
		super();
		this.cliente = cliente;
		this.direccion = direccion;
	}
	
	public void run() {
		try(InputStream is = cliente.getInputStream(); 
			BufferedReader entrada = new BufferedReader(new InputStreamReader(is)); 
			OutputStream os = cliente.getOutputStream();
			PrintStream salida = new PrintStream(os);){
			
			System.out.println("Cliente: "+direccion+" conectado");
			String mensaje;
			do {
				mensaje = entrada.readLine(); //0-recibo accion
				//REGISTRAR USUARIO
				if(mensaje.equals("REGISTRO")) { 
					System.out.println("El cliente ha elegido registrarse como usuario..");
					sendMessage("Vamos a registrar un nuevo usuario", salida); //1-envio mensaje
					registrarUsuario(entrada, salida); //2-recibiré credenciales
				}
				//ACCEDER AL SERVIDOR
				else { 
					System.out.println("El cliente ha elegido entrar al servidor FTP..");
					sendMessage("Vamos a hacer operaciones sobre el servidor FTP", salida);
					String[] credenciales = (entrada.readLine()).split(" ");
					
					//USUARIO INCORRECTO
					if(!autenticar(credenciales)) {
						System.out.println("El cliente no se ha podido autenticar correctamente..");
						sendMessage("El usuario "+credenciales[0]+" no existe", salida);
					}
					
					//USUARIO CORRECTO
					else {
						System.out.println("El cliente se ha autenticado correctamente..");
						sendMessage("Usuario "+credenciales[0]+" autenticado correctamente", salida);
						boolean seguir = true;
						String archivo, accion;
						while(seguir) {
							System.out.println("Esperando acción");
							accion = entrada.readLine();//1-se recibe opción general
							while(true) {
								if(accion.equals("1")) { //listar directorio
									System.out.println("listando directorios..");
									String lista = listarDirectorio();
									DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
									dos.writeUTF(lista);
									dos.flush();
									break;
								}
								else if(accion.equals("2")) { //recibir archivo
									System.out.println("El cliente ha elegido subir un archivo..");
									DataInputStream dis = new DataInputStream(cliente.getInputStream());
									System.out.println("Leyendo nombre..");
									String nombre = dis.readUTF().toString(); //2-se recibe el nombre del archivo
									System.out.println("Leyendo tamaño..");
									int tam = dis.readInt(); //3-recibo tamaño archivo
									receiveFile(cliente, tam, nombre); //4-recibo el archivo
									break;
								}
								else if(accion.equals("3")) { //enviar archivo
									System.out.println("El cliente ha elegido bajar un archivo..");
									System.out.println("Enviando lista de archivos..");
									String lista = listarDirectorio();
									DataOutputStream dos = new DataOutputStream(cliente.getOutputStream()); //1.5 envio la lista de archivos
									dos.writeUTF(lista);
									dos.flush();
									archivo = entrada.readLine(); //2-recibo el nombre del archivo
									System.out.println("Enviando el archivo "+archivo+" ..");
									sendFile(cliente, archivo);
									break;
								}
								else { //salir
									seguir = false;
									System.out.println("Saliendo del servidor FTP..");
									break;
								}
									
							}
						}
					}
				}
				
			}while(!mensaje.equals("SALIR"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String listarDirectorio() {
		File directorio = new File("/home/pablo/FTP/servidorFTP"); // ruta de mi directorio servidor
		File[] listaFich = directorio.listFiles();
		int i = 1;
		String lista = "";
		for (File f : listaFich) { // solo nos interesan los ficheros que no sean directorios
			if (f.isFile())
				lista += (i++) + " - " + f.getName()+"\n";
		}
		return lista;
	}

	//registrar usuario nuevo
	private void registrarUsuario(BufferedReader entrada, PrintStream salida) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("/home/pablo/FTP/datosUsuarios/datos.txt", true));){
			String credenciales = entrada.readLine();
			String[] datos = null;
			if(credenciales!=null)
				datos = credenciales.split(" ");
			String linea = String.format("%s  %s", datos[0], datos[1]);
			bw.write(linea);
			salida.println("Usuario registrado correctamente");
			salida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean autenticar(String[] credenciales) {
		return true;
	}
	public void sendFile(Socket cliente, String nombreArchivo) {
		File miArchivo = new File("/home/pablo/FTP/servidorFTP/" + nombreArchivo); // archivo a enviar
		try(
			FileInputStream fis = new FileInputStream("/home/pablo/FTP/servidorFTP/"+nombreArchivo);
			BufferedInputStream bis = new BufferedInputStream(fis);){
			DataOutputStream dout = new DataOutputStream(cliente.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(cliente.getOutputStream());
			//enviamos el nombre del archivo
			//dout.writeUTF(miArchivo.getName());
			System.out.println("Enviando tamaño..");
			//3-enviamos el tamaño del archivo
			int tam = (int) miArchivo.length();
			dout.writeInt(tam);
			byte[] buffer = new byte[tam];
			//leemos el archivo y lo introducimos en el array de bytes
			bis.read(buffer);
			System.out.println("Enviando contenido..");
			//4-se realiza el envío del archivo
			for(int i=0;i<buffer.length;i++) {
				bos.write(buffer[i]);
			}
			bos.flush();
			System.out.println("Archivo enviado: "+miArchivo.getName());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveFile(Socket cliente, Integer sizeArchivo, String nombreArchivo) {
		try{
			FileOutputStream fos = new FileOutputStream("/home/pablo/FTP/servidorFTP/"+nombreArchivo);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			BufferedInputStream in = new BufferedInputStream(cliente.getInputStream());
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
	
	//enviar mensaje a cliente
	public void sendMessage(String mensaje, PrintStream salida) {
		salida.println(mensaje);
		salida.flush();
	}
}

 
