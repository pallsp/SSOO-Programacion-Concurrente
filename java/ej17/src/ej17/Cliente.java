package ej17;

import java.util.concurrent.Semaphore;

public class Cliente implements Runnable{
	private String nombre;
	private Campana c;
	//private Semaphore moviles;
	
	public Cliente(String nombre, Campana c) {
		this.nombre = nombre;
		//this.moviles = moviles;
		this.c=c;
	}

	@Override
	public void run() {
		//moviles.acquire();
		System.out.println("Voy a intentar comprar un móvil");
		if(c.venderMovil(1))
			System.out.println("Soy "+nombre+" y he comprado un móvil");
		else
			System.out.println("Soy "+nombre+" y no he comprado un móvil");
		
	}

}
