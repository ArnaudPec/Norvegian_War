package fr.utt.lo02.carte;

import java.util.LinkedList;

public class MainJoueur extends Tas{

	public MainJoueur() {
		this.listeCartes = new LinkedList<Carte>();
	}
	
	public Carte prendreCarte(int position)
	{
		Carte carte = this.listeCartes.get(position);
		this.listeCartes.remove(position);
		
		return carte;
	}
	
	@Override
	public String toString()
	{
		String resultat = "";
		
		for (int i = 0; i < this.listeCartes.size(); i++) {
			
			resultat+= this.listeCartes.toString() + " ";
			
		}
		return resultat;
	}
	
}