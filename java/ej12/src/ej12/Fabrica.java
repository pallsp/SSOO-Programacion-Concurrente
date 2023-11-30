package ej12;

public class Fabrica extends Thread{
	
	private Muneca m;
	private Integer n;
	
	public Fabrica(Muneca m, Integer n) {
		this.m=m;
		this.n=n;
	}
	
	public void run() {
		while(true) {
			m.poner(n);
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
