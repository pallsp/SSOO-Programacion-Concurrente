package ej18;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Buzon b = new Buzon();
		Thread[] ninos = new Thread[52];
		Semaphore[] reyes = new Semaphore[4];
		reyes[0] = new Semaphore(1,true); //si es true es un fifo 
		reyes[1] = new Semaphore(1,true);
		reyes[2] = new Semaphore(1,true);
		reyes[3] = null;
		int rey; //sortea la fila en la que se coloca el ñiño, si es 3 va directo al buzon
		
		//aqui divido en dos bucles la creación e inicialización de hilos ninos porque los guardo en un array, pero no tendría por qué guardarlos 
		//y podría perfectamente hacerse e iniciarse en el momento 
		for(int i=0;i<ninos.length;i++) {
			rey = (int)(Math.random()*4);
			ninos[i] = new Thread(new hiloNino("niño "+i,b,reyes[rey],rey));
			
		}
		
		for(int i=0;i<ninos.length;i++) {
			ninos[i].start();
		}

		
	}

}
