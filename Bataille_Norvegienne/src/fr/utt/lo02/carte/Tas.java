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
	protected int nbCartes = this.listeCartes.size();
	
	
	/**
	 * Méthode abstraite à implémenter, permet de déplacer une carte 
	 */
	public abstract void deplacerCarte(int position, Tas main);
	
	
	
	/**
	 * Méthode abstraite à implémenter, permet de choisir une carte à déplacer 
	 */
	public abstract void choisirCarteADeplacer();


	public void ajouterCarte(Carte carte) {
		// TODO Auto-generated method stub
		
	}
}
