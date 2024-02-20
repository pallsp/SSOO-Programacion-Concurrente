import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Modelo.Juego;

public class pruebas {

	public static void main(String[] args) {
		Juego j = new Juego(5,3);
		Scanner sc = new Scanner(System.in);
		int i,k;
		ArrayList<Integer[]> posicionesI = j.getPosicionesI();
		ArrayList<Integer[]> posicionesD = j.getPosicionesD();
		
		System.out.println("POSICIONES I");
		for(Integer[] par: posicionesI) {
			System.out.println(par[0]+","+par[1]);
		}
		System.out.println("POSICIONES D");
		for(Integer[] par: posicionesD) {
			System.out.println(par[0]+","+par[1]);
		}
		System.out.println(formatear(posicionesI));
		/*
		System.out.println("Introduce unas coords I: ");
		i = sc.nextInt();
		sc.nextLine();
		k = sc.nextInt();
		sc.nextLine();
		j.updatePosiciones(i, k, 0);
		System.out.println("Introduce unas coords D: ");
		i = sc.nextInt();
		sc.nextLine();
		k = sc.nextInt();
		sc.nextLine();
		j.updatePosiciones(i, k, 1);
		
		
		ArrayList<Integer[]> posiciones2I = j.getPosicionesI();
		ArrayList<Integer[]> posiciones2D = j.getPosicionesD();
		
		System.out.println("POSICIONES I");
		for(Integer[] par: posiciones2I) {
			System.out.println(par[0]+","+par[1]);
		}
		System.out.println("POSICIONES D");
		for(Integer[] par: posiciones2D) {
			System.out.println(par[0]+","+par[1]);
		}
		*/
		
		
		
		/*
		 Integer[] posicion = new Integer[2];
		int n1, n2;
		ArrayList<String> posiciones = new ArrayList<>();
		ArrayList<Integer[]> posicionesEnteras = new ArrayList<>();
		int n = 3;
		while(n>0) {
			n1 = (int)(Math.random()*5);
			n2 = (int)(Math.random()*5);
			System.out.println(" ");
			System.out.println("Mi posición");
			System.out.println(n1+","+n2);
			if(checkPosiciones(posicionesEnteras,n1,n2)) {
				posicion[0] = n1;
				posicion[1] = n2;
				posicionesEnteras.add(posicion.clone());
				n--;
			}
		}
		
		for(Integer [] par: posicionesEnteras) {
			System.out.println("("+par[0]+","+par[1]+")");
		}
		 */
		
		/*Integer[] posicion = new Integer[2];
		ArrayList<String> posiciones = new ArrayList<>();
		int n = 3;
		while(n>0) {
			posicion[0] = (int)(Math.random()*5);
			posicion[1] = (int)(Math.random()*5);
			String mensaje = posicion[0]+","+posicion[1];
			if(!posiciones.contains(mensaje)) {
				posiciones.add(mensaje);
				n--;
			}
		}
		for(String p: posiciones) {
			System.out.println(p);
		}
		String mensaje = "";
		for(String p: posiciones) {
			if(!posiciones.get(posiciones.size()-1).equals(p))
				mensaje += p+" - ";
			else
				mensaje += p;
		}
		System.out.println(mensaje);*/
		/*
		Integer[] posicion = new Integer[2];
		ArrayList<String> posiciones = new ArrayList<>();
		int n = 3;
		while(n>0) {
			posicion[0] = (int)(Math.random()*5);
			posicion[1] = (int)(Math.random()*5);
			System.out.println("coords["+n+"]: ("+posicion[0]+","+posicion[1]+")");
			String mensaje = posicion[0]+","+posicion[1];
			if(!posiciones.contains(mensaje)) {
				posiciones.add(mensaje);
				n--;
			}
		}
		
		for(String p: posiciones) {
			System.out.println("("+p+")");
		}
		*/
	}

	private static String formatear(ArrayList<Integer[]> pos) {
		String mensaje = "";
		for(Integer[] par: pos) {
			if(!pos.get(pos.size()-1).equals(par))
				mensaje += par[0]+","+par[1]+" - ";
			else
				mensaje += par[0]+","+par[1];
		}
		return mensaje;
	}
	
	private static boolean checkPosiciones(ArrayList<Integer[]> posiciones, int n1, int n2) {
		for(Integer[] par: posiciones) {
			if((n1 == par[0]) && (n2 == par[1]))
				return false;
		}
		return true;
	}
	
}
