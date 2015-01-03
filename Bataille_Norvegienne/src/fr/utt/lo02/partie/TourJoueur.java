package fr.utt.lo02.partie;

import java.util.ArrayList;

public class TourJoueur {

	private ArrayList<Integer> ListeCarte;
	private Partie partie;
	
	public TourJoueur(Partie partie) {
		this.ListeCarte = new ArrayList<Integer>();
		this.partie = partie;
	}

	public ArrayList<Integer> getListeCarte() {
		return ListeCarte;
	}
	
	public void envoyer(){
		this.partie.faireJouerJoueur(this.ListeCarte);//Ici j'envoie les numéros des cartes que le joueur veut jouer, il faut que la méthode se débrouille pour faire jouer ces cartes.
		//Rafraichir la vue ? je sais pas trop si ça se fait avec les updates ou si il faut appeller une méthode.
		
		this.ListeCarte=null;//Je supprime une fois qu'on à jouer pour le prochain joueur.
	}
	
	//AJouter une carte sélectionné à la liste (n'ajoute que la position de la carte dans la main du joueur)
	public void actionCarteListe(int i){
		this.ListeCarte.add(i);
		verifCarteListe(i);
		changementEtatSelectionne(i);

	}
	
	//Change de l'état de la carte, si la carte est sélectionné ou non.
	public void changementEtatSelectionne(int position){
			this.partie.getPioche().getCarte(position).setSelectionne();
	}
	
	public void verifCarteListe(int carte){
		for (int i = 0; i < this.ListeCarte.size(); i++) {
			if(carte == this.ListeCarte.get(i)){ // / ! \ p-e une erreur ici possible a cause du == qui devrait p-e être un equals
				this.ListeCarte.remove(i);
				break;
			}
		}
	}
}
