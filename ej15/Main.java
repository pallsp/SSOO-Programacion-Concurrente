package ej15;

public class Main {

	public static void main(String[] args) {
		final int NUM_ELTOS = 20000;
		Integer[] m1 = new Integer[NUM_ELTOS], m2 = new Integer[NUM_ELTOS], m3 = new Integer[NUM_ELTOS];
		Thread hilo1 = new Thread(new hiloArray(m1,m2,1)), hilo2 = new Thread(new hiloArray(m1,m2,2));
		long tiempoI, tiempoF;		
		
		//rellenamos arrays
		for(int i=0;i<m1.length;i++) {
			m1[i] = (int)(Math.random()*5000)+1;
			m2[i] = (int)(Math.random()*5000)+1;
		}
		tiempoI = System.currentTimeMillis();
		//multiplicamos
		hilo1.start();
		hilo2.start();
		try {
			hilo1.join();
			hilo2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tiempoF = System.currentTimeMillis();
		
		System.out.println(tiempoF - tiempoI);

	}

}
