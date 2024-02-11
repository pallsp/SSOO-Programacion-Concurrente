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
				BufferedReader entrada = new BufferedReader(new InputStreamReader(is)); OutputStream os = cliente.getOutputStream();
				PrintStream salida = new PrintStream(os);){
			System.out.println("Cliente: "+direccion+" conectado");
			String mensaje = entrada.readLine(); //0-recibo accion
			if(mensaje.equals("REGISTRO")) { //registramos usuario nuevo
				sendMessage("Vamos a registrar un nuevo usuario", salida); //1-envio mensaje
				registrarUsuario(entrada, salida); //2-recibiré credenciales
			}
			else {  //accedemos al servidor con un usuario que ya existe
				sendMessage("Vamos a hacer operaciones sobre el servidor FTP", salida);
				String[] credenciales = (entrada.readLine()).split(" ");
				
				//si el usuario es correcto
				if(autenticar(credenciales)) {
					sendMessage("Usuario "+credenciales[0]+" autenticado correctamente", salida);
					boolean seguir = true;
					String archivo;
					while(seguir) {
						System.out.println("Esperando acción");
						String accion = entrada.readLine();//1-se recibe opción
						while(true) {
							if(accion.equals("1")) { //listar directorio
								System.out.println("listando directorios..");
								sendMessage(listarDirectorio(), salida);
								break;
							}
							else if(accion.equals("2")) { //recibir archivo
								archivo = entrada.readLine(); //2-se recibe el nombre del archivo
								System.out.println("obteniendo "+archivo+" ..");
								String tam = entrada.readLine(); //3-recibo tamaño archivo
								Integer sizeArchivo = Integer.parseInt(tam);
								receiveFile(cliente, sizeArchivo, archivo); //4-recibo el archivo
								break;
							}
							else if(accion.equals("3")) { //enviar archivo
								sendMessage(listarDirectorio(), salida); //se envía la lista de archivos que hay 
								archivo = entrada.readLine(); //2-recibo el nombre del archivo
								System.out.println("Enviando el archivo "+archivo+" ..");
								sendFile(cliente, archivo, salida);
								break;
							}
							else { //salir
								seguir = false;
								System.out.println("Saliendo..");
								break;
							}
								
						}
					}
				}
				else
					sendMessage("El usuario "+credenciales[0]+" no existe", salida);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String listarDirectorio() {
		File directorio = new File("/home/pablo/FTP/servidorFTP"); //ruta de mi directorio servidor
		File[] listaFich = directorio.listFiles();
		String lista = "";
		for(File f: listaFich) 
			lista += f.getName()+"\n";
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
	public void sendFile(Socket cliente, String nombreArchivo, PrintStream salida) {
		File fich = new File("/home/pablo/FTP/servidorFTP/"+nombreArchivo); //archivo a enviar
		try(FileInputStream fis = new FileInputStream(fich);
				BufferedInputStream bis = new BufferedInputStream(fis);OutputStream os = cliente.getOutputStream();){
			byte[] miByteArray = new byte[(int) fich.length()];
			bis.read(miByteArray, 0, miByteArray.length);
			salida.println(miByteArray.length); //3-envío el tamaño del archivo
			salida.flush();
			os.write(miByteArray, 0, miByteArray.length); //4-envío el archivo
			os.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveFile(Socket cliente, Integer sizeArchivo, String nombreArchivo) {
		try(FileOutputStream fos = new FileOutputStream("/home/pablo/FTP/servidorFTP/"+nombreArchivo);
				BufferedOutputStream bos = new BufferedOutputStream(fos);InputStream is = cliente.getInputStream();){
			byte[] miByteArray = new byte[sizeArchivo];
			int bytesLeidos = is.read(miByteArray, 0, miByteArray.length);
			int bytesTotales = bytesLeidos;
			while(bytesTotales<sizeArchivo) {
				bytesLeidos = is.read(miByteArray, bytesTotales, (miByteArray.length-bytesTotales));
				if(bytesLeidos>=0)
					bytesTotales += bytesLeidos;
			}
			bos.write(miByteArray, 0, bytesTotales); //4-recibo el archivo
			bos.flush();
			System.out.println("El archivo "+nombreArchivo+" se ha añadido correctamente.");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//enviar mensaje a cliente
	public void sendMessage(String mensaje, PrintStream salida) {
		salida.println(mensaje);
		salida.flush();
	}
}
