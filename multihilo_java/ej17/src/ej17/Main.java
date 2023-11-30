package ej17;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Thread[] clientes = new Thread[15];
		//Semaphore moviles = new Semaphore(10);
		Campana c = new Campana(10);
		
		for(int i=0;i<clientes.length;i++) {
			clientes[i] = new Thread(new Cliente("cliente "+i,c));
		}
		
		for(int i=0;i<clientes.length;i++) {
			clientes[i].start();
		}

	}

}
