package ej17;

public class Campana {
	private Integer moviles;
	
	public Campana(Integer moviles) {
		this.moviles = moviles;
	}
	
	public synchronized Boolean venderMovil(int valor) {
		if(moviles-valor>=0) {
			moviles-=valor;
			return true;
		}
		return false;
	}

}
