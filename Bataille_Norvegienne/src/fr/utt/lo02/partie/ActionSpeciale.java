package fr.utt.lo02.partie;

import fr.utt.lo02.carte.Tapis;
import fr.utt.lo02.joueur.Joueur;

public class ActionSpeciale {

	private Tapis tapis;
	private Joueur joueur;
	private Partie partie;
	
	public ActionSpeciale(Partie partie, int JoueurCourant)
	{
		this.partie = partie;
		this.tapis = partie.getTapis();
		this.joueur = partie.getJoueur(JoueurCourant);
	}
	
}