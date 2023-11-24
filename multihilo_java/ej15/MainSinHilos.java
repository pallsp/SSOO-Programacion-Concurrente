package ej15;

public class MainSinHilos {

	public static void main(String[] args) {
		final int NUM_ELTOS = 20000;
		Integer[] m1 = new Integer[NUM_ELTOS], m2 = new Integer[NUM_ELTOS];
		Long[] m3 = new Long[NUM_ELTOS];
		long tiempoI, tiempoF;
		
		//rellenamos arrays
		for(int i=0;i<m1.length;i++) {
			m1[i] = (int)(Math.random()*5000)+1;
			m2[i] = (int)(Math.random()*5000)+1;
		}
		tiempoI = System.currentTimeMillis();
		for(int i=0;i<m1.length;i++) {
			//m3[i] = m1[i]*m2[i];
			m3[i] = multiplica(i,m1,m2);
		}
		tiempoF = System.currentTimeMillis();
		
		System.out.println(tiempoF-tiempoI);
	}

	private static Long multiplica(int j, Integer[] m1, Integer[] m2) {
		long resultado = 0;
		
		for(int i=0;i<j;i++) {
			resultado += m1[i]*m2[i];
		}
		
		return resultado;
	}

}
