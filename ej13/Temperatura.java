
public class Temperatura {

	public static void main(String[] args) {
		if(args.length !=1)
			System.out.println("Error");
			
		else {
			int temperatura = (int)(Math.random()*51)-10;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(temperatura);
		}

	}

}
