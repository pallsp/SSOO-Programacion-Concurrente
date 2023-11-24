package ej10;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Camarero[] camareros = new Camarero[50];
		
		Semaphore soda = new Semaphore(1,true);
		Semaphore zumo = new Semaphore(1,true);
		Semaphore tabasco = new Semaphore(1,true);
		String nombre;
		int coctel;
		
		for(int i=0;i<camareros.length;i++) {
			coctel = (int)(Math.random()*3)+1;
			nombre = "camarero "+i;
			camareros[i] = new Camarero(nombre,coctel,soda,tabasco,zumo);
			camareros[i].start();
		}

	}

}
