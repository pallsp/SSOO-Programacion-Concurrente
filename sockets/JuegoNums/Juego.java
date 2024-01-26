import java.util.ArrayList;

public class Juego {
	//private ArrayList<Integer> listaGanados;
	private Integer num1;
	private Integer num2; 
	private Integer res;
	
	public Juego() {
		//this.listaGanados = new ArrayList<>();
	}
	
	public synchronized String compare() {
		while(num1 == null || num2 == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Números: "+num1+", "+num2);
		if(num1>num2) {
			return "Ha ganado el jugador 1";
		}
		else if(num1==num2) {
			return "Empate";
		}
		else {
			return "Ha ganado el jugador 2";
		}
	}
	
	public synchronized void setNum1(int n) {
		this.num1 = n;
		notifyAll();
	}
	
	public synchronized void setNum2(int n) {
		this.num2 = n;
		notifyAll();
	}

}
