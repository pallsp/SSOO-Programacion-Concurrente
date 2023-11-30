package ej5;

import java.util.concurrent.Semaphore;

public class Cliente extends Thread{
	private String nombre;
	private Semaphore peluqueros;
	
	public Cliente(String nombre, Semaphore peluqueros) {
		this.peluqueros = peluqueros;
		this.nombre = nombre;
	}
	
	public void run() {
		int tiempo = (int)(Math.random()*150)+50;
		try {
			System.out.println("Soy "+nombre+" y voy a la peluquería");
			peluqueros.acquire();
			sleep(tiempo);
			System.out.println("Soy "+nombre+ " y me están cortanto el pelo");
			peluqueros.release();
			System.out.println("Soy "+nombre+" y me han cortado el pelo");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
