package ej6;

public class Acuario {
	
	private int aforo;
	
	public Acuario(Integer aforoInicial) {
		this.aforo = aforoInicial;
	}
	
	public synchronized void addAforo(int n) {
		while(aforo+n>50) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		aforo+=n;
		int disponibles = 50-aforo;
		System.out.println("Hay "+disponibles+" plazas disponibles");
		notifyAll();
	}
	public synchronized void removeAforo(int n) {
		aforo-=n;
		notifyAll();
	}
	
	

}
