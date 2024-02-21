package Modelo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteHandler extends Thread{
	private Socket cliente;
	private Integer nCliente;
	private Juego j;
	
	public ClienteHandler(Socket cliente, Juego j, Integer nCliente) {
		super();
		this.cliente = cliente;
		this.j = j;
		this.nCliente = nCliente; 
	}
	
	//1 JUGADOR -> tableroI
	//2 JUGADOR -> tableroD
	public void run() {
		try {
			DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());
			DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
			int n = 0;
			boolean acertado;
			String posiciones;
			
			//PREPARTIDA
			if(nCliente == 0) {
				j.setJug1();
				System.out.println("Jugador 1 conectado: "+cliente.getInetAddress());
			}
			else{
				j.setJug2();
				System.out.println("Jugador 2 conectado: "+cliente.getInetAddress());
			}
			
			j.startPartida();
			System.out.println("COMIENZA LA PARTIDA");
			
			//EMPIEZA LA PARTIDA
			do {
				//MOVIMIENTO IMPAR -> JUEGA 1
				//MOVIMIENTO PAR   -> JUEGA 2
				
				//1-envío número de jugador
				flujoSalida.writeInt(nCliente+1); 
				flujoSalida.flush();
				
				if(nCliente == 0) {
					//2-envío datos tablero I
					flujoSalida.writeUTF(formatear(j.getPosicionesI()));
					flujoSalida.flush();	
				}
				else {
					//2-envío datos tablero D
					flujoSalida.writeUTF(formatear(j.getPosicionesD()));
					flujoSalida.flush();	
				}
				
				if(nCliente == (j.getMovimiento()%2)) { //SI ESTÁ JUGANDO EL CLIENTE
					System.out.println("Num movimiento: "+j.getMovimiento());
					
					//3-envío si te toca jugar (juega)
					flujoSalida.writeBoolean(true);
					flujoSalida.flush();
					
					//4-recibo posicion marcada por el jugador, que será de la forma -> x,y
					posiciones = flujoEntrada.readUTF(); 
					//j.setPosicionMarcada(Integer.parseInt(posiciones.split(",")[0]), Integer.parseInt(posiciones.split(",")[1]));
					System.out.println("Posicion marcada por el rival: "+ posiciones);
					
					//actualizo las posiciones 				
					acertado = j.updatePosiciones(Integer.parseInt(posiciones.split(",")[0]), 
								Integer.parseInt(posiciones.split(",")[1]), nCliente);
					
					//5-envío si ha acertado la posición
					flujoSalida.writeBoolean(acertado);
					flujoSalida.flush();
					System.out.println("Posicion que marca el rival 2222222: "+posiciones);
					j.setPosiciones3(posiciones);
					
					//6-envío la posición
					flujoSalida.writeUTF(posiciones);
					flujoSalida.flush();
					
					j.nextMovimiento();
				}
				else {
					System.out.println("Num movimiento: "+j.getMovimiento());
					//3-envío si te toca jugar (espera)
					flujoSalida.writeBoolean(false);
					flujoSalida.flush();
					//Integer[] posicionRival = j.getPosicionMarcada();
					//String posicionDisparada = posicionRival[0]+","+posicionRival[1]; 
					
					//4-envío si el rival ha acertado
					flujoSalida.writeBoolean(j.getJugadaAcertada());
					flujoSalida.flush();
										
					String p = j.getPosiciones3();
					while(p == null) {
						System.out.println("Es nulo, vamos a esperar");
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						p = j.getPosiciones3();
						System.out.println("probamos de nuevo");
					}
					String posicionDisparada = p;
					
					//5-envío posición a la que dispara el rival
					System.out.println("Posición marcada que envío: "+posicionDisparada);
					flujoSalida.writeUTF(posicionDisparada);
					flujoSalida.flush();
				}
				
				n = j.getStatus();
				System.out.println("Estado del juego: [0 - SIGUE 1- GANA JUGADOR 1 2- GANA JUGADOR 2]"+n);
				if(!(nCliente == (j.getMovimiento()%2)))
					j.setJugadaAcertada(null);
				
				//6-envío el estado del juego
				flujoSalida.writeInt(n);
				flujoSalida.flush();
			}while(n == 0);
			
			if((nCliente+1) == n)
				System.out.println("Has ganado!!");
			else
				System.out.println("Has perdido!!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String formatear(ArrayList<Integer[]> pos) {
		String mensaje = "";
		for(Integer[] par: pos) {
			if(!pos.get(pos.size()-1).equals(par))
				mensaje += par[0]+","+par[1]+"-";
			else
				mensaje += par[0]+","+par[1];
		}
		return mensaje;
	}

}
