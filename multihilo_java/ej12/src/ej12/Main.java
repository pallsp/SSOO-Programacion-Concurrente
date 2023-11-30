package ej12;

public class Main {

	public static void main(String[] args) {
		Muneca m = new Muneca();
		Fabrica f = new Fabrica(m,10); //si se ponen de 1 en 1 sólo sacará el trabajador de 5
		Trabajador t1 = new Trabajador(m,7);
		Trabajador t2 = new Trabajador(m,6);
		Trabajador t3 = new Trabajador(m,5);
		
		f.start();
		t1.start();
		t2.start();
		t3.start();
		
	}

}
