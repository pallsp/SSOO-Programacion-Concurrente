package Modelo;
import java.util.ArrayList;

public class Juego {
	private Integer dimension;
	private Integer numBarcos; 
	private Integer movimiento;
	private ArrayList<Integer[]> posicionesI;
	private ArrayList<Integer[]> posicionesD;
	private Boolean jug1;
	private Boolean jug2;
	private Integer[] posicionMarcada;
	private Boolean jugadaAcertada;
	private String posiciones3;
	
	//Juego(dimension, numeroBarcos)
	public Juego(Integer n, Integer numBarcos) {
		this.dimension = n;
		this.numBarcos = numBarcos;
		this.movimiento = 0; //movimiento desde el que parte cada partida
		this.jug1 = false;
		this.jug2 = false;
		this.posicionesI = new ArrayList<>();
		this.posicionesD = new ArrayList<>();
		this.posicionMarcada = new Integer[2];
		generatePosicionesI();
		generatePosicionesD();
	}
	public void setPosiciones3(String p) {
		this.posiciones3 = p;
	}
	
	public String getPosiciones3() {
		return posiciones3;
	}
	public void setJugadaAcertada(Boolean b) {
		this.jugadaAcertada = b;
	}
	
	public synchronized Boolean getJugadaAcertada() {
		while(jugadaAcertada == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return jugadaAcertada;
	}
	
	public synchronized void setPosicionMarcada(int i, int j) {
		this.posicionMarcada[0] = i;
		this.posicionMarcada[1] = j;
		//notifyAll();
	}
	
	public synchronized Integer[] getPosicionMarcada() {
		/*
		 while(posicionMarcada == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		 */
		return posicionMarcada;
	}
	
	public synchronized void startPartida() {
		while(!jug1 || !jug2) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void setJug1() {
		this.jug1 = true;
		notifyAll();
	}
	
	public synchronized void setJug2() {
		this.jug2 = true;
		notifyAll();
	}
	
	private void generatePosicionesI() {
		Integer[] posicion = new Integer[2];
		int n1, n2;
		ArrayList<Integer[]> posicionesEnteras = new ArrayList<>();
		int n = numBarcos;
		while(n>0) {
			n1 = (int)(Math.random()*dimension);
			n2 = (int)(Math.random()*dimension);
			if(checkPosiciones(posicionesEnteras,n1,n2)) {
				posicion[0] = n1;
				posicion[1] = n2;
				posicionesEnteras.add(posicion.clone());
				n--;
			}
		}
		this.posicionesI = posicionesEnteras;
	}
	
	private void generatePosicionesD() {
		Integer[] posicion = new Integer[2];
		int n1, n2;
		ArrayList<String> posiciones = new ArrayList<>();
		ArrayList<Integer[]> posicionesEnteras = new ArrayList<>();
		int n = numBarcos;
		while(n>0) {
			n1 = (int)(Math.random()*dimension);
			n2 = (int)(Math.random()*dimension);
			if(checkPosiciones(posicionesEnteras,n1,n2)) {
				posicion[0] = n1;
				posicion[1] = n2;
				posicionesEnteras.add(posicion.clone());
				n--;
			}
		}
		this.posicionesD = posicionesEnteras;
	}
	
	private static boolean checkPosiciones(ArrayList<Integer[]> posiciones, int n1, int n2) {
		for(Integer[] par: posiciones) {
			if((n1 == par[0]) && (n2 == par[1]))
				return false;
		}
		return true;
	}
	
	public ArrayList<Integer[]> getPosicionesI(){
		return posicionesI;
	}
	
	public ArrayList<Integer[]> getPosicionesD(){
		return posicionesD;
	}
	
	public void nextMovimiento() {
		this.movimiento++;
	}
	
	public Integer getMovimiento() {
		return movimiento;
	}
	
	private boolean checkTableroD() {
		if(posicionesD.isEmpty())
			return true;
		return false;
	}

	private boolean checkTableroI() {
		if(posicionesI.isEmpty())
			return true;
		return false;
	}
	
	//le paso las coordenadas y el jugador: 0 -> tablero I, 1 -> tablero D
	//devuelve true si ha acertado y false si ha fallado
	public synchronized Boolean updatePosiciones(int i, int j, int jug) {
		setPosicionMarcada(i,j);
		if(jug == 0) { //tablero I
			for(Integer[] par: posicionesI) {
				if((par[0] == i) && (par[1] == j)) {
					posicionesI.remove(par);
					this.jugadaAcertada = true;
					notifyAll();
					return true;
				}
			}
		}
		else { //tablero D
			for(Integer[] par: posicionesD) {
				if((par[0] == i) && (par[1] == j)) {
					posicionesD.remove(par);
					this.jugadaAcertada = true;
					notifyAll();
					return true;
				}
			}
		}
		this.jugadaAcertada = false;
		notifyAll();
		return false;
	}
	
	public Integer getStatus() {
		if(checkTableroI())
			return 1; //gana jugador 1
		else if(checkTableroD())
			return 2; //gana jugador 2
		else
			return 0; //sigue la partida
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//EXTRA 	
	private void generatePosiciones(ArrayList<Integer[]> posiciones) {	
		Integer[] posicion = new Integer[2];
		int n1, n2;
		ArrayList<Integer[]> posicionesEnteras = new ArrayList<>();
		int n = numBarcos;
		while(n>0) {
			n1 = (int)(Math.random()*dimension);
			n2 = (int)(Math.random()*dimension);
			if(checkPosiciones(posicionesEnteras,n1,n2)) {
				posicion[0] = n1;
				posicion[1] = n2;
				posicionesEnteras.add(posicion.clone());
				n--;
			}
		}
		posiciones = posicionesEnteras;
	}
	
	
	
	
	/*
	 
	  private void generatePosicionesI() {
		Integer[] posicion = new Integer[2];
		ArrayList<String> posiciones = new ArrayList<>();
		ArrayList<Integer[]> posicionesEnteras = new ArrayList<>();
		int n = 3;
		while(n>0) {
			posicion[0] = (int)(Math.random()*5);
			posicion[1] = (int)(Math.random()*5);
			String mensaje = posicion[0]+","+posicion[1];
			if(!posiciones.contains(mensaje)) {
				posiciones.add(mensaje);
				n--;
			}
		}
		for(String p: posiciones) {
			System.out.println(p);
		}
		String mensaje = "";
		for(String p: posiciones) {
			if(!posiciones.get(posiciones.size()-1).equals(p))
				mensaje += p+" - ";
			else
				mensaje += p;
		}
		
		this.posicionesSI = mensaje;
	}
	  
	 */
	
	
	
	
	
	
	/*	 
	 private void generateTableros() {
		int n, nBarcosI = numBarcos, nBarcosD = numBarcos;
		Integer[] posicionI = new Integer[2], posicionD = new Integer[2];
		for(int i=0;i<this.tableroI.length;i++) {
			for(int j=0;j<this.tableroI[0].length;j++) {
				n = (int)(Math.random()*2);
				if(n == 1 && nBarcosI != 0) {
					tableroI[i][j] = true;	
					posicionI[0] = i;
					posicionI[1] = j;
					posicionesI.add(posicionI);
					nBarcosI--;
				}
				else
					tableroI[i][j] = false;
			}			
		}
		
		for(int i=0;i<this.tableroD.length;i++) {
			for(int j=0;j<this.tableroD[0].length;j++) {
				n = (int)(Math.random()*2);
				if(n == 1 && nBarcosD != 0) {
					tableroD[i][j] = true;	
					posicionD[0] = i;
					posicionD[1] = j;
					posicionesD.add(posicionD);
					nBarcosD--;
				}
				else
					tableroD[i][j] = false;
			}			
		}
	}
	 
	 */

}
