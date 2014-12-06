package fr.utt.lo02.partie;

import java.util.LinkedList;
import java.util.Scanner;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.carte.Tapis;
import fr.utt.lo02.joueur.Joueur;

public class ActionSpeciale {

	private Tapis tapis;
	private Joueur joueur;
	private Partie partie;
	private LinkedList<Carte> carteDuTourPrecedent;
	private LinkedList<Carte> carteDuTourCourant;
	
	
	public ActionSpeciale(Partie partie, int JoueurCourant)
	{
		this.partie = partie;
		this.tapis = partie.getTapis();
		this.joueur = partie.getJoueur(JoueurCourant);
	}
	
	
	public void appelerBonneMethode(Carte carte)
	{
		switch (carte.getValeur()) {
		case 2:
			effectuerAction2();
			break;
		case 7:
			effectuerAction7();
			break;
		case 8:
			effectuerAction8();
			break;
		case 10:
			effectuerAction10();
			break;
		case 14:
			effectuerActionA();
			break;
		}
	}
	
	private void effectuerActionA() {
		
		if(!this.partie.getJoueurSuivant().peutJouer(this.tapis.carteDuDessus()))
		{
			int choixDuJoueur = this.joueur.interfaceDemandeChoisirUnJoueur();
			LinkedList<Carte> tapis = this.tapis.getListeCartes();
			Joueur joueur = this.partie.getListeJoueurs().get(choixDuJoueur);
			
			joueur.getMainJoueur().ajouterPlusieursCartes(tapis);
		}
	}


	private void effectuerAction10() {
		this.partie.getTapis().viderTapis();		
	}

	private void effectuerAction8() {		
		this.partie.setJoueurCourant(this.partie.getJoueurCourant()+2);
	}
}
