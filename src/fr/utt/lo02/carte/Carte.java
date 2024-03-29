package fr.utt.lo02.carte;

import java.util.Observable;

public class Carte extends Observable{

	/**
	 * Représente la couleur de la carte [coeur=0, pique=1, trefle=2, carreau=3]
	 */
	private int couleur;

	/**
	 * Représente la valeur de la carte [2=2, .. 10=10, valet=11, dame=12,
	 * roi=13, as=14]
	 */
	private int valeur;

	/**
	 * Représente la visibilité de la carte [true=retournée, false=cachée]
	 */
	private boolean visibilite;
	
	private boolean selectionner;

	/**
	 * Constructeur de carte, permet de créer une carte en lui attribuant une
	 * couleur et une valeur.
	 * 
	 * @param couleur
	 *            de type Integer [coeur=0, pique=1, trefle=2, carreau=3]
	 * @param valeur
	 *            de type Integer [2=2, .. 10=10, valet=11, dame=12, roi=13,
	 *            as=14]
	 */
	public Carte(int couleur, int valeur) {
		this.couleur = couleur;
		this.valeur = valeur;
		this.visibilite = false;
		this.selectionner=false;
	}

	/**
	 * Getter de couleur
	 * 
	 * @return La couleur de la carte
	 */
	public int getCouleur() {
		return this.couleur;
	}

	/**
	 * Getter de valeur
	 * 
	 * @return La valeur de la carte
	 */
	public int getValeur() {
		return this.valeur;
	}

	/**
	 * Getter de visibilite
	 * 
	 * @return Ma visibilite de ma carte [true=retourne, false=cache]
	 */
	public boolean getVisibilite() {
		return this.visibilite;
	}
	
	/**
	 * Getter permettant de savoir si la cart est selectionné ou non
	 * @return boolean [true or false]
	 */
	public boolean getSelectionner(){
		return this.selectionner;
	}

	/**
	 * Setter de couleur
	 * 
	 * @param couleur
	 *            [coeur=0, pique=1, trèfle=2, carreau=3]
	 */
	public void setCouleur(int couleur) {
		this.couleur = couleur;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Setter de valeur
	 * 
	 * @param valeur
	 *            [2=2, .. 10=10, valet=11, dame=12, roi=13, as=14]
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Setter de visibilite
	 * 
	 * @param visibilite
	 *            [true=retourne, false=cache]
	 */
	public void setVisibilite(boolean visibilite) {
		this.visibilite = visibilite;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setSelectionne(){
		if(this.selectionner){
			this.selectionner=false;
		}else{
			this.selectionner=true;
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Méthode permettant de déterminer si la carte es† une carte spéciale
	 * 
	 * @return Un boolean [true=spéciale, false=pas spéciale]
	 */
	public boolean estSpeciale() {
		boolean resultat = false;
		switch (this.valeur) {
		case 2:
			resultat = true;
			break;
		case 7:
			resultat = true;
			break;
		case 8:
			resultat = true;
			break;
		case 10:
			resultat = true;
			break;
		case 14:
			resultat = true;
			break;
		default:
			resultat = false;
			break;
		}
		return resultat;
	}

	/**
	 * Méthode permettant de comparer deux cartes
	 * 
	 * @param carte
	 * @return Un boolean [true=superieur ou egale, false=inferieur]
	 */
	public boolean estSuperieurOuEgale(Carte carte) {
		boolean resultat = false;
		if (this.valeur >= carte.getValeur()) {
			resultat = true;
		}
		return resultat;
	}

	/**
	 * Permet d'avoir la valeur de la carte pour l'affichage
	 * 
	 * @return String contenant la valeur de la carte à afficher.
	 */
	public String getValeurAffichage() {
		String valeur = "";

		switch (this.valeur) {
		case 2:
			valeur = "2";
			break;
		case 3:
			valeur = "3";
			break;
		case 4:
			valeur = "4";
			break;
		case 5:
			valeur = "5";
			break;
		case 6:
			valeur = "6";
			break;
		case 7:
			valeur = "7";
			break;
		case 8:
			valeur = "8";
			break;
		case 9:
			valeur = "9";
			break;
		case 10:
			valeur = "10";
			break;
		case 11:
			valeur = "V";
			break;
		case 12:
			valeur = "D";
			break;
		case 13:
			valeur = "R";
			break;
		case 14:
			valeur = "A";
			break;
		default:
			break;
		}

		return valeur;
	}

	/**
	 * Permet d'avoir la couleur de la carte pour l'affichage
	 * 
	 * @return String contenant la couleur de la carte à afficher.
	 */
	public String getCouleurAffichage() {
		String couleur = "";

		switch (this.couleur) {
		case 0:
			couleur = "\u2665";
			break;
		case 1:
			couleur = "\u2660";
			break;
		case 2:
			couleur = "\u2663";
			break;
		case 3:
			couleur = "\u2666";
			break;
		default:
			break;
		}

		return couleur;
	}

	/**
	 * Méthode permettant de déterminer si la carte est posable en fonction de
	 * la carte qui a été posée avant. On doit gérer chacun des cas en se
	 * référant aux cartes du jeu
	 * 
	 * @param cartePrecedente
	 * @return un booleen
	 */
	public boolean estPosable(Carte cartePrecedente) {
		
		if (cartePrecedente == null)return  true;
		else if (this.valeur == 2)return  true;
		else if (cartePrecedente.getValeur() == 14 && this.valeur == 14)return true;
		else if (cartePrecedente.getValeur() == 7 && this.valeur <= 7)return  true;
		else if (cartePrecedente.getValeur() == 7 && this.valeur > 7)return  false;
		else if (cartePrecedente.getValeur() <= this.valeur) return  true;
		else return  false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String resultat = " —————️ " + "\n";
		resultat += "|" + getCouleurAffichage() + "    |" + "\n";
		resultat += "|  " + getValeurAffichage() + "  |" + "\n";
		resultat += "|    " + getCouleurAffichage() + "|" + "\n";
		resultat += " ————— " + "\n";

		return resultat;
	}
}
