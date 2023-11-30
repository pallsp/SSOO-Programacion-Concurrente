package patines;

import java.util.Iterator;
import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Thread[] alumnos = new Thread[15];
		int numero, isNovato;
		String nombre; 
		Semaphore[] patinesD = new Semaphore[10];
		Semaphore[] patinesI = new Semaphore[10];
		
		for(int i=0;i<patinesD.length;i++) {
			patinesD[i] = new Semaphore(2,true);
			patinesI[i] = new Semaphore(2,true);
		}
		
		for(int i = 0; i<alumnos.length;i++) {
			numero =(int) Math.random()*10+34;
			isNovato = (int) Math.random()*2;
			nombre = "alumno "+i;
			if(isNovato == 0) //es novato
				alumnos[i] = new Thread(new hiloAlumno(nombre,numero,isNovato,patinesD[numero-34],null));
			else //es avanzado
				alumnos[i] = new Thread(new hiloAlumno(nombre,numero,isNovato,patinesD[numero-34],patinesI[numero-34]));
		}
		for(int i=0; i<alumnos.length; i++) {
			alumnos[i].start();
		}
	}

}
