package ej6;

public class Visitantes implements Runnable{
	private Integer numero;
	private Acuario a;
	private String nombre;
	
	public Visitantes(String nombre, Acuario a) {
		this.nombre = nombre;
		this.numero = (int)(Math.random()*7)+1;
		this.a = a;
	}
	@Override
	public void run() {
		System.out.println("Somos "+numero+" visitantes y queremos entrar al acuario");
		a.addAforo(numero);
		System.out.println("Somos "+numero+" visitantes y hemos entrado al acuario");
		int tiempo = (int)(Math.random()*50)+1;
		try {
			Thread.sleep(tiempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		a.removeAforo(numero);
		System.out.println("Nos hemos ido "+numero+" visitantes");
	}

}
