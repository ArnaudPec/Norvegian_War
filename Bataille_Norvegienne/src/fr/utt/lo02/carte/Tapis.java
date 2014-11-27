package fr.utt.lo02.carte;

import java.util.LinkedList;

public class Tapis extends Tas {

	private static Tapis instanceTapis;

	private Tapis() {
		this.listeCartes = new LinkedList<Carte>();
	}

	public static Tapis getInstanceTapis() {
		Tapis instance;

		if (instanceTapis == null) {
			instance = new Tapis();
			instanceTapis = instance;
		} else {
			instance = instanceTapis;
		}

		return instance;
	}
	
	public Carte CarteDuDessus(){
		
		return this.listeCartes.getFirst();
	}
	
	@Override
	public String toString(){
		return "Tapis \n" + CarteDuDessus().toString();
	}

	public static void main(String[] args) {

	}
}
