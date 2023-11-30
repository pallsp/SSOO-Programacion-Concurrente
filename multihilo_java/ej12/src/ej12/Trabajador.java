package ej12;

public class Trabajador extends Thread{
	
	private Muneca m;
	private Integer n;
	
	public Trabajador(Muneca m, Integer n) {
		this.m=m;
		this.n=n;
	}
	
	public void run() {
		
		while(true) {
			m.sacar(n);
		}
		
	}

}
