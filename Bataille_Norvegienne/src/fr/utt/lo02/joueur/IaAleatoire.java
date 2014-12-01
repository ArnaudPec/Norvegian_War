package fr.utt.lo02.joueur;

import java.util.Random;

import fr.utt.lo02.carte.*;
public class IaAleatoire extends IA {

	public IaAleatoire(String nomJoueur, int numJoueur) {
		super(nomJoueur, numJoueur);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Méthode permettant au joueur IaAleatoire de choisir les cartes à jouer.
	 * Ici tout est choisi aléatoirement. On utilise l'objet random.
	 * @see rand
	 *
	 * @see MainJoueur#calculerNbMaxCarteMemeValeur()
	 * @see MainJoueur#calculerNbOccurenceMemeValeur(Carte)
	 * @see MainJoueur#calculerPositionCarteValeur(int)
	 *
	 */
	public Carte[] choisirCarteAJouer() {

		Random rand = new Random();
		Carte[] listeCartes;
		int numCarte = 0;
		int nombreMaxCarteJouable;

		nombreMaxCarteJouable = this.mainJoueur.calculerNbMaxCarteMemeValeur();
		if (nombreMaxCarteJouable > 3)
			nombreMaxCarteJouable = 3;

		int nb = rand.nextInt(nombreMaxCarteJouable + 1);
		while (nb == 0) {
			nb = rand.nextInt(nombreMaxCarteJouable + 1);
		}

		listeCartes = new Carte[nb];

		numCarte = rand.nextInt(3 + 1);

		while (numCarte > 3
				|| (this.mainJoueur
						.calculerNbOccurenceMemeValeur(this.mainJoueur
								.getCarte(numCarte)) != nombreMaxCarteJouable)) {
			numCarte = rand.nextInt(3 + 1);
		}
		int valeur = this.mainJoueur.getCarte(numCarte).getValeur();
		for (int i = 0; i < listeCartes.length; i++) {
			listeCartes[i] = this.mainJoueur.prendreCarte(this.mainJoueur
					.calculerPositionCarteValeur(valeur));
		}

		return listeCartes;
	}

	/**
	 * Méthode permettant au joueur humain d'échanger ses cartes en début de
	 * partie.
	 * 
	 * @see rand
	 *
	 * @see MainJoueur#ajouterCarte(Carte)
	 * @see MainJoueur#prendreCarte(int)
	 * 
	 * @see TasVisible#ajouterCarte(Carte)
	 * @see TasVisible#prendreCarte(int)
	 */
	public void changerCartes() {

		int numCarteMain, numCarteVis;
		int nbChangement = 3;
		Random rand = new Random();

		boolean changer = rand.nextBoolean();
		System.out.println(changer);
		while (changer && nbChangement > 0) {

			numCarteMain = rand.nextInt(4);
			numCarteVis = rand.nextInt(3);

			this.mainJoueur.ajouterCarte(this.tasVisible
					.prendreCarte(numCarteVis));
			this.tasVisible.ajouterCarte(this.mainJoueur
					.prendreCarte(numCarteMain));
			nbChangement--;
			changer = rand.nextBoolean();
		}

	}

}
