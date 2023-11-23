package ej10;

import java.util.concurrent.Semaphore;

public class Camarero extends Thread{
	private Integer coctel; //1 soda y tabasco, 2 soda y zumo, 3 soda tabasco y zumo
	private Semaphore soda;
	private Semaphore tabasco;
	private Semaphore zumo;
	
	public Camarero(String nombre, Integer coctel, Semaphore soda, Semaphore tabasco, Semaphore zumo) {
		super(nombre);
		this.coctel = coctel;
		this.soda = soda;
		this.tabasco = tabasco;
		this.zumo = zumo;
	}
	
	public void run() {
		if(coctel == 1) {
			try {
				System.out.println("Soy "+getName()+" y quiero preparar un coctel de soda y tabasco");
				soda.acquire();
				tabasco.acquire();
				System.out.println("Soy "+getName()+" y estoy preparando un coctel de soda y tabasco");
				sleep(100);
				soda.release();
				tabasco.release();
				System.out.println("Soy "+getName()+" y he acabado el cóctel de soda y tabasco");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		if(coctel == 2) {
			try {
				System.out.println("Soy "+getName()+" y quiero preparar un coctel de soda y zumo");
				soda.acquire();
				zumo.acquire();
				System.out.println("Soy "+getName()+" y estoy preparando un coctel de soda y zumo");
				sleep(100);
				soda.release();
				zumo.release();
				System.out.println("Soy "+getName()+" y he acabado el cóctel de soda y zumo");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		if(coctel == 3) {
			try {
				System.out.println("Soy "+getName()+" y quiero preparar un coctel de soda, tabasco y zumo");
				soda.acquire();
				zumo.acquire();
				tabasco.acquire();
				System.out.println("Soy "+getName()+" y estoy preparando un coctel de soda, tabasco y zumo");
				sleep(100);
				soda.release();
				zumo.release();
				tabasco.release();
				System.out.println("Soy "+getName()+" y he acabado el cóctel de soda, tabasco y zumo");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
			
	}
}
