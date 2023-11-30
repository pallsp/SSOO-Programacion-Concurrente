package patines;

import java.util.concurrent.Semaphore;

public class hiloAlumno  implements Runnable {
	private Integer numero;
	private Integer isNovato;
	private String nombre; 
	private Semaphore pd;
	private Semaphore pi;
	
	public hiloAlumno(String nombre, Integer numero, Integer isNovato, Semaphore pd, Semaphore pi) {
		this.nombre = nombre;
		this.numero = numero;
		this.isNovato = isNovato;
		this.pd = pd;
		this.pi = pi;
	}

	@Override
	public void run() {
		if(isNovato == 1) { //avanzado
			try {
				pd.acquire();
				pi.acquire();
				System.out.println("Soy "+nombre+", soy avanzado y estoy patinando");
				Thread.sleep((int)Math.random()*200);
				System.out.println("Soy "+nombre+" y he terminado");
				pd.release();
				pi.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else { //novato
			try {
				pd.acquire();
				System.out.println("Soy "+nombre+", soy novato y estoy patinando");
				Thread.sleep((int)Math.random()*200);
				System.out.println("Soy "+nombre+" y he terminado");
				pd.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
