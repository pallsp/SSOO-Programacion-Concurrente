package ej5;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		final int NUM_CLIENTES = 200;
		Cliente[] clientes = new Cliente[NUM_CLIENTES];
		Semaphore peluqueros = new Semaphore(4,true);
		
		//instanciamos los clientes
		for(int i=0;i<NUM_CLIENTES;i++) {
			clientes[i] = new Cliente("cliente "+i,peluqueros);
		}
		
		//arrancamos los clientes
		for(int i=0;i<NUM_CLIENTES;i++) {
			clientes[i].start();
		}

	}

}
