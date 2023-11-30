import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) {
		final int NUM_CI = 20;
		Integer suma = 0;
		
		for(int i = 0; i<NUM_CI; i++) {
			suma+=launchProcess(String.valueOf((int)(Math.random()*51)+50));
		}
		
		System.out.println("La media es: "+(double)(suma)/NUM_CI);
	}
	
	public static Integer launchProcess(String ciudad) {
		File f = new File(".\\bin");
		ProcessBuilder b = new ProcessBuilder("java","Temperatura",ciudad);
		b.directory(f);
		int letra;
		String resultado = "";
		
		try {
			Process p = b.start();
			InputStream fin = p.getInputStream();
			letra = fin.read();
			
			while(letra != 13) {
				resultado+=(char)letra;
				letra = fin.read();
			}
			System.out.println("La ciudad "+ciudad+" tiene una temperatura de "+resultado);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(resultado);
	}

}
