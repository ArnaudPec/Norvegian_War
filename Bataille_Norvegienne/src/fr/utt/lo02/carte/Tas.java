package fr.utt.lo02.carte;

import java.util.LinkedList;

/**
 * Classe Tas, permet de généraliser un Tas
 */
public abstract class Tas {

	/**
	 * Représente l'ensemble des cartes contenu dans un Tas
	 */
	protected LinkedList<Carte> listeCartes;

	/**
	 * Getter permettant de récupérer une carte
	 * 
	 * @param position
	 * @return une carte
	 */
	public Carte getCarte(int position) {

		Carte carte = null;

		if (position >= 0 && position <= this.listeCartes.size()) {
			carte = this.listeCartes.get(position);
		}
		return carte;

	}

	/**
	 * Getter de listeCartes
	 * 
	 * @return la liste de carte courante.
	 */
	public LinkedList<Carte> getListeCartes() {
		return this.listeCartes;
	}

	/**
	 * Setter de liste de carte.
	 * 
	 * @param listeCartes
	 */
	public void setListeCartes(LinkedList<Carte> listeCartes) {
		this.listeCartes = listeCartes;
	}

	/**
	 * Cette méthode permet d'ajouter une carte à la liste de Carte
	 * 
	 * @param carte
	 */
	public void ajouterCarte(Carte carte) {
		this.listeCartes.add(carte);
	}

	public void ajouterPlusieursCartes(LinkedList<Carte> carte) {
		this.listeCartes.addAll(carte);
	}

	public void ajouterPlusieursCartes(Carte[] carte) {
		for (int i = 0; i < carte.length; i++) {
			this.listeCartes.add(carte[i]);
		}
	}
	
	public void viderTas(){
		this.listeCartes.clear();
	}

}
