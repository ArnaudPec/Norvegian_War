package fr.utt.lo02.partie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.carte.Pioche;
import fr.utt.lo02.carte.Tapis;
import fr.utt.lo02.joueur.Humain;
import fr.utt.lo02.joueur.IaAleatoire;
import fr.utt.lo02.joueur.IaEquilibree;
import fr.utt.lo02.joueur.IaOffensive;
import fr.utt.lo02.joueur.Joueur;

/**
 * Classe Matérialisant une partie. Dans MVC, il s'agit du modèle.
 *
 */

public class Partie extends Observable{

	private int nbJoueurs;
	private int joueurCourant;
	private ArrayList<Joueur> listeJoueurs;
	private Pioche pioche;
	private Tapis tapis;
	private static Partie instancePartie;
	private boolean terminee;

	private Partie() {
		
		this.listeJoueurs = new ArrayList<Joueur>();
		this.pioche = Pioche.getInstancePioche();
		this.tapis = Tapis.getInstanceTapis();
		this.joueurCourant = 0;
		this.terminee=false;
	}

	public static Partie getInstancePartie() {

		Partie instance;

		if (instancePartie == null) {
			instance = new Partie();
			instancePartie = instance;
		} else {
			instance = instancePartie;
		}
		return instance;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}
	

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.setChanged();
		this.notifyObservers();
	}

	public int getJoueurCourant() {
		return this.joueurCourant;
	}

	public Joueur getJoueur(int position) {
		Joueur joueur = null;
		try {
			joueur = this.listeJoueurs.get(position);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return joueur;
	}


	public void setJoueurCourant(int joueurCourant) {
		this.joueurCourant = joueurCourant;
		this.setChanged();
		this.notifyObservers();
	}

	public Pioche getPioche() {
		return pioche;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
		this.setChanged();
		this.notifyObservers();
	}

	public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
		this.setChanged();
		this.notifyObservers();
	}

	public void setTapis(Tapis tapis) {
		this.tapis = tapis;
		this.setChanged();
		this.notifyObservers();
	}
	
	public ArrayList<Joueur> getListeJoueurs() {
		return this.listeJoueurs;
	}
	
	public int getJoueurInt(String nom){
		
		for (int i = 0; i < this.listeJoueurs.size() ; i++) {
			if(this.listeJoueurs.get(i).getNom().equals(nom)){
				return i;
			}
		}
		 return -1;
	}
	
	public String[] getListeNomsJoueurs(){
		String[] listeNoms = new String[this.nbJoueurs];
		int i=0;
		for (Iterator<Joueur> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			listeNoms[i] = joueur.getNom();
			i++;
			
		}
		
		return listeNoms;
	}

	public Tapis getTapis() {
		return this.tapis;
	}

	public static void setInstancePartie(Partie instancePartie) {
		Partie.instancePartie = instancePartie;
	}

	/**
	 * Methode permettant de récupérer le joueur suivant
	 * @return retourne le joueur suivant.
	 */
	public Joueur getJoueurSuivant() {
		return this.listeJoueurs.get((joueurCourant + 1)%this.nbJoueurs);
	}

	/**
	 * Méthode permettant d'ajouter un joueur à la la collection listeJoueurs.
	 * @param joueur
	 */
	public void ajouterJoueur(Joueur joueur) {
		this.listeJoueurs.add(joueur);
		this.nbJoueurs++;
	}
	

	
	/**
	 * Méthode permettant l'ajout d'un joueur humain à la partie
	 * @param nom du joueur
	 */
	public void creationHumain(String nom){
		Humain joueur = new Humain(nom, 0);
		this.ajouterJoueur(joueur);
	}
	
	
	/**
	 * Méthode permettant l'ajout de joueurs d'intelligence artificielle à la partie. Le paramètre stratégie permet
	 * de définir la stratégie de celui-ci.
	 * 
	 * @param numJoueur
	 * @param strategie
	 */
	public void creationIA(int numJoueur, int strategie){
		Joueur joueurIA ;
		switch (strategie) {
		case 0:
			joueurIA = new IaAleatoire("ia_" + numJoueur +"_Ale", numJoueur);
			this.ajouterJoueur(joueurIA);
			break;
		case 1:
			joueurIA = new IaOffensive("ia_" + numJoueur +"_Off", numJoueur);
			this.ajouterJoueur(joueurIA);
			break;
		case 2:
			joueurIA = new IaEquilibree("ia_" + numJoueur +"_Equ", numJoueur);
			this.ajouterJoueur(joueurIA);
			break;		
		}

	}
	


	/**
	 * Méthode permettant de vérifier si un joueur offensif est présent dans la liste des joueurs
	 * @return un booleen 
	 */
	public boolean verifierPresenceIaOffensive(){
		
		for (Iterator<Joueur> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			if (joueur instanceof IaOffensive) return true;
			}
		
		return false;
			
	
	}
	
	/**
	 * Méthode qui retourne true si la partie est gagnée en vérifiant que le joueur
	 * courant a gagné ou non.
	 * 
	 * @return [true=la partie est gagnée, false=la partie continue]
	 */
	public boolean estGagnee() {
		return this.listeJoueurs.get(joueurCourant).estGagnant();
	}

	/**
	 * Methode permettant d'initialiser le jeu. -Melanger les cartes
	 * -Distribution des cartes -lancer la boucle de jeu
	 */
	public void initialisationPartie() {

		this.pioche.melanger();
		this.pioche.distribuerCarte(this);
	}

	/**
	 * Méthode permettant de gérer le joueur courant au sein de la boucle de
	 * jeu. Incrémente le joueur s'il y'a un joueur après lui sinon repasse au
	 * premier joueur.
	 */
	public void incrementerJoueur() {
		
		if (this.joueurCourant == this.nbJoueurs - 1) this.joueurCourant = 0;
		else joueurCourant++;
	}

	/**
	 * Méthode permettant de gérer l'action d'un joueur sur la partie pour un
	 * tour
	 * 
	 * @param joueur
	 */
	public int faireJouerJoueur(Joueur joueur) {

		System.out.println("C'est " + joueur.getNom() + " qui joue ! \n" /*+this.getTapis()*/);

		Carte[] carteJouees = joueur.choisirCarteAJouer(this.getTapis().getCarteDuDessus());
		this.getTapis().ajouterPlusieursCartes(carteJouees);
		return carteJouees.length;

	}

	/**
	 * Méthode permettant de faire piocher un joueur pour compléter sa main. On
	 * pioche tout d'abord dans le tas Pioche puis dans les TasVisible puis
	 * TasCaché. Le nombre de carte piochée dépend du nombre de carte restantes
	 * dans la main du joueur ainsi que du nombre de cartes restantes dans la
	 * pioche. Le mode de piochage pour les TasVisible et TasCaché est différent
	 * de celui de la pioche. Dans TasVisible on pioche les 3 cartes d'un coup
	 * une fois la pioche vidée et la main de nouveau vide. Le piochage dans le
	 * TasCaché s'effectue carte par carte à chaque fois que la main ne contient
	 * plus de carte.
	 * 
	 * @param joueur
	 * 
	 */
	public void fairePiocherJoueur(Joueur joueur) {

		int nbMax;

		if (!this.pioche.getListeCartes().isEmpty()	&& joueur.getMainJoueur().getListeCartes().size() < 3) { 
			nbMax = 3 - joueur.getMainJoueur().getListeCartes().size();

			if (this.pioche.getListeCartes().size() < nbMax)
				nbMax = this.pioche.getListeCartes().size();

			for (int i = 0; i < nbMax; i++) {
				joueur.getMainJoueur().ajouterCarte(
				this.pioche.prendreCarteDuDessus());
			}
			System.out.println(nbMax + " carte(s) a(ont) ete piochee(s) par "	+ joueur.getNom() + "\n");

		}

		else if (this.pioche.getListeCartes().isEmpty() && joueur.getMainJoueur().getListeCartes().isEmpty() && (!joueur.getTasVisible().getListeCartes().isEmpty())) {
			joueur.getMainJoueur().ajouterPlusieursCartes(joueur.getTasVisible().prendreTasVisible());
			System.out.println(joueur.getNom()	+ " vient de prendre les cartes de son TasVisible.\n");

		}

		else if (this.pioche.getListeCartes().isEmpty() && joueur.getTasVisible().getListeCartes().isEmpty() && joueur.getMainJoueur().getListeCartes().isEmpty()) 
		{
			joueur.getMainJoueur().ajouterCarte(joueur.getTasCache().prendreCarte());
			System.out.println(joueur.getNom()+ " vient de prendre une carte de son TasCache.\nIl contient encore " +joueur.getTasCache().getListeCartes().size() + " cartes.\n");
		}

		setChanged();
		this.notifyObservers();
		System.out.println("AAA");
	}

	/**
	 * Methode permettant de lancer la boucle de jeu.
	 */

	public void lancerPartie() {

		boolean estDanish = false;
		Joueur gagnant = null;
		int nbtour = 0;

		while (!estDanish) {
			Joueur joueur = this.listeJoueurs.get(joueurCourant);
			
			if (joueur.peutJouer(this.getTapis().getCarteDuDessus())) {

				System.out.println("num joueur : " + joueur.getNumJoueur());
				
				int nbCartesPosees=this.faireJouerJoueur(joueur);
				
				if (joueur.estGagnant()) {
					estDanish = true; 
					gagnant = joueur;
				} else this.fairePiocherJoueur(joueur);
				
				if (this.tapis.getCarteDuDessus().estSpeciale()) {ActionSpeciale actionSpeciale = new ActionSpeciale(this,this.joueurCourant, nbCartesPosees);actionSpeciale.appelerBonneMethode();}

				System.out.println("La pioche contient "+ this.pioche.getListeCartes().size() + " cartes. \nLe tapis contient "+ this.tapis.getListeCartes().size() + " cartes. \n");
			}

			else {
				System.out.println("Le joueur "+ joueur.getNom()+ " ne peut pas jouer, il ramasse le tapis.\n");
				joueur.getMainJoueur().getListeCartes().addAll(this.tapis.prendreTapis());// Je donne le
			}
			
			this.incrementerJoueur();
			
			nbtour++;
		}
		System.out.println("Partie terminee, gagnant : " + gagnant.getNom()	+ " en " + nbtour + " tours");
	}
	
	
	
	/**
	 * Méthode permettant de vérifier que la sélection de carte effectuée par le joueur est juste et cohérente.
	 * @param liste
	 * @return un boolean vrai ou faux
	 */
	public boolean verifierSelection(ArrayList<Carte> liste){
		
		int nbCarte = liste.size();
		if (nbCarte ==0 || nbCarte>3) {
			return false;
			
		}
		else if (nbCarte>1){ //Si plus de une carte sélectionnées, on vérifie qu'elles ont la même valeur
			
			Carte c = liste.get(0); // on récupère le premier élément pour comparer sa valeur
			int compteur = 0;
			for (Iterator<Carte> iterator = liste.iterator(); iterator.hasNext();) {
					Carte carte = (Carte) iterator.next();
					if (c.getValeur() == carte.getValeur())
					compteur++;
				}
			
			if(compteur==nbCarte) return true;
			else {
				return false;
			}
		}
		else return true;
		
	}
	
	/**
	 * Méthode permettant de vérifier la sélection de carte d'un joueur lors de l'échange de carte en début de partie.
	 * En effet, la sélection doit être cohérente vis à vis des règles du jeu. On n'autorise pas un échange faux.
	 * 
	 * @param liste
	 * @return un boolean vrai ou faux
	 */
	public boolean verifierSelectionEchange(ArrayList<Carte> liste){
		
		if (liste.size()==3 ) return true;
		else return false;
		
	}
	
	public Humain getHumain(){
		
		for (Iterator<Joueur> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			if(joueur instanceof Humain) return (Humain) joueur;
		}
		
		 return null;
	}
	

	public boolean isTerminee() {
		return terminee;
	}

	public void setTerminee(boolean terminee) {
		this.terminee = terminee;
	}
	
	/** Méthode permettant le calcul du nombre total de carte en jeu.
	 * @return int
	 */
	public int calculerNombreCarteTotal(){
		int nb;
		
		nb=this.getPioche().getListeCartes().size()+this.getTapis().getListeCartes().size();
		for (Iterator<Joueur> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			nb += joueur.getMainJoueur().getListeCartes().size();
			nb += joueur.getTasVisible().getListeCartes().size(); 
			nb += joueur.getTasCache().getListeCartes().size(); 
		}
		return nb;
	}
}