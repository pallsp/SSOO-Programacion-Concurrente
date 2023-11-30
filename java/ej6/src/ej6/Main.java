package ej6;

public class Main {

	public static void main(String[] args) {
		final int NUM_PERSONAS = 100;
		Thread[] personas = new Thread[NUM_PERSONAS];
		Acuario acuario = new Acuario(0);
		
		//instanciamos las personas
		for(int i=0;i<NUM_PERSONAS;i++) {
			personas[i] = new Thread(new Visitantes("grupo"+i, acuario));
		}

		//arrancamos las personas
		for(int i=0;i<NUM_PERSONAS;i++) {
			personas[i].start();
		}
	}

}
