package fr.utt.lo02.carte;

import java.util.LinkedList;

/**
 * Classe Tas, permet de généraliser un Tas
 */
public abstract class Tas {

	
	/**
	 *Représente l'ensemble des cartes contenu dans un Tas 
	 */
	protected LinkedList<Carte> listeCartes;
	
	
	/**
	 *Nombre de cartes dans le tas.
	 */
	protected int nbCartes;

	
	public void ajouterCarte(Carte carte){
		this.listeCartes.add(carte);
	}
	
	public void setListeCartes(LinkedList<Carte> listeCartes){
		this.listeCartes = listeCartes;
	}
	
	public LinkedList<Carte> getListeCartes(){
		return this.listeCartes;
	}
}