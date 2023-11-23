package ej18;

public class hiloRey extends Thread{
	private Buzon b;
	private Boolean ocupado;
	private String nombreNino; //nombre del niño que atiende
	
	public hiloRey(String nombre, Buzon b) {
		super(nombre);
		this.b = b;
		this.ocupado = false;
	}
	
	public void esOcupado(String nombre) {
		ocupado = true;
		this.nombreNino = nombre;
	}
	
	public void run() {
		
		while(ocupado) {
			
			try {
				b.depositaCarta(getName(),nombreNino);
				sleep(100);
				b.procesaCarta(getName(), nombreNino);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ocupado = false;
		}
		
	}
}
