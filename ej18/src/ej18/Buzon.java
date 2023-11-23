package ej18;

import java.util.ArrayList;

public class Buzon {
	private Boolean vacio;
	private Integer contador;
	private ArrayList <String> cartas;
	public Buzon() {
		this.vacio = true;
		this.contador = 0;
		this.cartas = new ArrayList<>();
	}
	
	public synchronized void depositaCarta(String rey, String nino) {	
		String s = String.format("Recibida carta: %s - %s",rey,nino);
		System.out.println(s);
		contador++;
		cartas.add(s);
		
		if(contador == 5) {
			procesaCarta(rey,nino);
		}
		
		procesaCarta(rey,nino);
	}
	
	public synchronized void procesaCarta(String rey, String nino) {
		for(int i =0; i<cartas.size();i++) {
			System.out.println(String.format("Procesada carta: %s - %s",rey,nino));
		}
		contador = 0;
		cartas.clear();
	}

}
