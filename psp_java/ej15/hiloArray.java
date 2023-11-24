package ej15;

public class hiloArray implements Runnable{
	private Integer[] m;
	private Integer[] n;
	private Integer mitad; //si 1 calcula los primeros 10000 elementos y si 2 calcula los segundos 10000 elementos
	
	public hiloArray(Integer[] m, Integer[] n, Integer mitad) {
		this.m = m;
		this.n = n;
		this.mitad = mitad;
	}
	
	public long multiplica(int j) {
		long resultado = 0;
		for(int i=0;i<j;i++) {
			resultado += m[i]*n[i];
		}
		
		return resultado;
	}
	
	@Override
	public void run() {
		Long[] p = new Long[10000];
		if(mitad == 1) {
			for(int i=0;i<10000;i++) {
				//p[i] = m[i] * n[i];
				p[i] = multiplica(i);
			}
		}
		
		if(mitad == 2) {
			for(int i=10000;i<m.length;i++) {
				//p[i-10000] = m[i] * n[i];
				p[i-10000] = multiplica(i);
			}
		}
		
	}

}
