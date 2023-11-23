package ej18;

import java.util.concurrent.Semaphore;

public class hiloNino implements Runnable{
	private String nombre;
	private Buzon b;
	private Semaphore rey;
	private Integer marcaRey;
	
	public hiloNino(String nombre, Buzon b, Semaphore rey, Integer marcaRey) {
		this.nombre=nombre;
		this.b=b;
		this.rey=rey;
		this.marcaRey=marcaRey;
	}
	
	@Override
	public void run() {
		if(rey == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			b.depositaCarta("SinRey",nombre);	
		}
		else {
			String nombreRey="";
			if(marcaRey == 0) nombreRey = "Gaspar";
			if(marcaRey == 1) nombreRey = "Melchor";
			if(marcaRey == 2) nombreRey = "Baltasar";
			
			try {
				rey.acquire();
				System.out.println(nombre+" está hablando con el rey "+nombreRey);
				Thread.sleep(100);
				b.depositaCarta(nombreRey,nombre);
				rey.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
		
	}
	

}
