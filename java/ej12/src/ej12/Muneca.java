package ej12;

public class Muneca {
	
	private Integer numero=0;
	
	public synchronized void poner(int valor) {
		while(numero+valor>20) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numero+=valor;
		System.out.println("Fabrica añade "+valor+" muñecas, cantidad total de muñecas: "+numero);
		notifyAll();
	}
	
	public synchronized void sacar(int valor) {
		System.out.println("Intentando sacar "+valor+" muñecas");
		while(numero-valor<0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numero-=valor;
		System.out.println("Trabajador saca "+valor+" muñecas, cantidad total de muñecas: "+numero);
		notifyAll();
	}

}
