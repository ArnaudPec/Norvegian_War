package fr.utt.lo02.partie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.carte.Pioche;
import fr.utt.lo02.carte.Tapis;
import fr.utt.lo02.joueur.Humain;
import fr.utt.lo02.joueur.IaAleatoire;
import fr.utt.lo02.joueur.IaEquilibree;
import fr.utt.lo02.joueur.IaOffensive;
import fr.utt.lo02.joueur.Joueur;

public class Partie implements Runnable {

	private int nbJoueurs;
	private int joueurCourant;
	private ArrayList<Joueur> listeJoueurs;
	private Pioche pioche;
	private Tapis tapis;
	private static Partie instancePartie;

	private Partie() {
		this.listeJoueurs = new ArrayList<Joueur>();
		this.pioche = Pioche.getInstancePioche();
		this.tapis = Tapis.getInstanceTapis();
		this.joueurCourant = 0;
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
	}

	public Pioche getPioche() {
		return pioche;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	public void setTapis(Tapis tapis) {
		this.tapis = tapis;
	}
	
	public ArrayList<Joueur> getListeJoueurs() {
		return this.listeJoueurs;
	}
	
	public Joueur getJoueurNom(String nom){
		for (Iterator<Joueur> iterator = listeJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			if(joueur.getNom().equals(nom)) return joueur;
			
		}
		 return null;
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
	 * Méthode permettant de gérer le mécanisme d'ajout des joueurs en début de partie
	 */
	@SuppressWarnings("resource")
	public void interfaceAjouterJoueur() {
		
		int nbJoueursHumain = 0;
		int nbJoueursIA = 0;
		Scanner scanner = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);

		do{
			System.out.println("Combien de joueur humain souhaitez vous ajouter ? (0-11 joueurs)");
			nbJoueursHumain = scanner.nextInt();
		}while(nbJoueursHumain>11 || nbJoueursHumain <0);
			
		System.out.println("Souhaitez vous ajouter des IA ?(Oui/Non)");
		String reponse = scanner2.nextLine();
		
		if (reponse.toUpperCase().equals("OUI") && nbJoueursHumain<11){
			do{
				System.out.println("Combien de joueur IA souhaitez vous ajouter ? (1-" + (11-nbJoueursHumain) + ")");
				nbJoueursIA= scanner.nextInt();
			}while(nbJoueursHumain + nbJoueursIA >11);
			if(nbJoueursIA==1 && nbJoueursHumain==0){
				System.out.println("Vous ne pouvez pas lancer de partie avec une seule IA, une IA a ete ajoutee !");
				nbJoueursIA ++;
			}
		} 
		else if(reponse.toUpperCase().equals("OUI")){
			System.out.println("Nombre maximum de joueurs atteint. Impossible d'ajouter des IAs");	
		}
		else if (nbJoueursHumain ==1) {
			System.out.println("Vous ne pouvez pas lancer de partie seul, une IA a ete ajoutee !");
			nbJoueursIA = 1;
		}
		else if(nbJoueursIA==0 && nbJoueursHumain==0){
			System.out.println("Vous ne pouvez pas lancer de partie sans joueurs, un joueur humain et une IA ont ete ajoutees !");
			nbJoueursIA = 1;
			nbJoueursHumain =1;
		}	
		this.creationHumain(nbJoueursHumain);
		this.creationIA(nbJoueursIA);
		
		if(this.nbJoueurs>5)this.pioche.ajouterUnSecondJeuDeCarte();
	}
//	
//	
//	public void interfaceAjouterJoueur() {
//		
//		boolean conditionIA = false;
//		boolean conditionJ = false;
//
//		int nbJoueursHumain = 0;
//		int nbJoueursIA = 0;
//
////		int niveau = 0;
//
//		Scanner sc = new Scanner(System.in);
//
//		System.out.println("Combien de joueur humain voulez vous ajouter");
//		String demandeNbJoueurs = sc.nextLine();
//
//		nbJoueursHumain = Integer.parseInt(demandeNbJoueurs);
//
//		do {
//			if (nbJoueursHumain > 0 && nbJoueursHumain <= 11) {
//				conditionJ = true;
//			}
//		} while (!conditionJ);
//
//		System.out.println("Voulez vous ajouter des IA ?(Oui/Non)");
//		String demandeIA = sc.nextLine();
//
//		if ("OUI".equals(demandeIA.toUpperCase())) {
//
//			System.out.println("Combien d'IA voulez vous ajouter ?");
//			nbJoueursIA = sc.nextInt();
//			do {
//				if (nbJoueursHumain + nbJoueursIA > 0 && nbJoueursHumain + nbJoueursIA <= 11) { 
//					conditionIA = true;
//				}
//			} while (!conditionIA);
//
////			System.out.println("Quel niveau d'IA désirez vous (0=aléatoire, 1=offensive)");
////			niveau = sc.nextInt();
//		} else {
//			if (nbJoueursHumain < 2) {
//				System.out.println("Vous ne pouvez pas lancer de partie seul, une IA aléatoire a été crée ");
//				nbJoueursIA = 1;
////				niveau = 0;
//			}
//		}
//		this.creationHumain(nbJoueursHumain);
//		this.creationIA(nbJoueursIA);
////		this.creationIA(nbJoueursIA, niveau);
//		
//		if(this.nbJoueurs>5)this.pioche.ajouterUnSecondJeuDeCarte();
//
//	}

	@SuppressWarnings("resource")
	public void creationHumain(int nbJoueur) {
		Scanner sc = new Scanner(System.in);

		for (int i = 0; i < nbJoueur; i++) {
			System.out.println("\nEntrez le nom du joueur");
			String nomJoueur = sc.nextLine();
			Humain joueur = new Humain(nomJoueur, this.nbJoueurs);
			this.ajouterJoueur(joueur);
		}

	}

	public void creationHumain(int numJoueur, String nom){
		Humain joueur = new Humain(nom, numJoueur);
		this.ajouterJoueur(joueur);
	}
	
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
	

	/**Méthode permettant de créer les joueurs gérés par l'ordinateur
	 * @param nbJoueur à créer
	 */
	@SuppressWarnings("resource")
	public void creationIA(int nbJoueur) {
		
		Scanner scanner = new Scanner(System.in);
		int strategie;
		boolean joueurOf=false;
		
		for (int i = 0; i < nbJoueur; i++) {
			
			System.out.println("\nIA numero "+i +" : Quelle stratégie souahitez vous affronter ? \n0 : Aleatoire | 1 : Offensive (MAX 1)| 2 : Equilibree");
			strategie = scanner.nextInt();
			
			while(strategie<0 || strategie > 2){
				System.out.println("Erreur, recommencez :");
				strategie = scanner.nextInt();
			}
			
			joueurOf=this.verifierPresenceIaOffensive();
			
			switch (strategie) {
			case 0:
				IaAleatoire iaAleatoire = new IaAleatoire("ia_" + i +"_Aleatoire",this.nbJoueurs);
				this.ajouterJoueur(iaAleatoire);
				break;
				
			case 1:
				if(!joueurOf){
				IaOffensive  IaOffensive = new IaOffensive("ia_" + i +"_Offensive",this.nbJoueurs);
				this.ajouterJoueur(IaOffensive);}
				else {
					System.out.println("Nombre max d'IA offensive atteint, creation d'une IA aleatoire");
					this.ajouterJoueur(new IaAleatoire("ia_" + i +"_Aleatoire",this.nbJoueurs));
				}
				break;
				
			case 2:
				IaEquilibree  IaEquilibree = new IaEquilibree("ia_" + i +"_Equilibree",this.nbJoueurs);
				this.ajouterJoueur(IaEquilibree);
				break;

			default:
				break;
			}
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

//	public void creationIA(int nbIA, int niveau) {
//
//		for (int i = 0; i < nbIA; i++) {
//			if (niveau == 0) {
//				IaAleatoire iaAleatoire = new IaAleatoire("ia" + i,this.nbJoueurs);
//				this.ajouterJoueur(iaAleatoire);
//			}
//			if (niveau == 1) {
//				IaOffensive iaOffensive = new IaOffensive("ia" + i,
//						this.nbJoueurs);
//				this.ajouterJoueur(iaOffensive);
//			}
//		}
//	}

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
	public void initialisationPartie(Partie partie) {

		this.interfaceAjouterJoueur();
		this.pioche.melanger();
		this.pioche.distribuerCarte(partie);
	}

	/**
	 * Méthode permettant de gérer le joueur courant au sein de la boucle de
	 * jeu. Incrémente le joueur s'il y'a un joueur après lui sinon repasse au
	 * premier joueur.
	 */
	public void incrementerJoueur() {
		
		if (this.joueurCourant == this.nbJoueurs - 1) this.joueurCourant = 0;
		else joueurCourant++;

		int nb = 0;
		
		for (Iterator<Joueur> iterator = this.listeJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			nb = nb + joueur.calculerNombreTotalCarte();
		}
	
		nb += this.tapis.getListeCartes().size() + this.pioche.getListeCartes().size();
		System.out.println("NOMBRE TOTAL DE CARTE DANS LE JEU : " +nb);
	}

	/**
	 * Méthode permettant de gérer l'action d'un joueur sur la partie pour un
	 * tour
	 * 
	 * @param joueur
	 */
	public int faireJouerJoueur(Joueur joueur) {

		System.out.println("C'est " + joueur.getNom() + " qui joue ! \n"+ this.getTapis());

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
			System.out.println(nbMax + " carte(s) a(ont) ete piochee(s) par "
					+ joueur.getNom() + "\n");

		}

		else if (this.pioche.getListeCartes().isEmpty() && joueur.getMainJoueur().getListeCartes().isEmpty() && (!joueur.getTasVisible().getListeCartes().isEmpty())) {
			joueur.getMainJoueur().ajouterPlusieursCartes(joueur.getTasVisible().prendreTasVisible());
			System.out.println(joueur.getNom()
					+ " vient de prendre les cartes de son TasVisible.\n");

		}

		else if (this.pioche.getListeCartes().isEmpty() && joueur.getTasVisible().getListeCartes().isEmpty() && joueur.getMainJoueur().getListeCartes().isEmpty()) 
		{
			joueur.getMainJoueur().ajouterCarte(joueur.getTasCache().prendreCarte());
			System.out.println(joueur.getNom()+ " vient de prendre une carte de son TasCache.\nIl contient encore " +joueur.getTasCache().getListeCartes().size() + " cartes.\n");
		}

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
				
				if (joueur.estGagnant()) {estDanish = true; gagnant = joueur;} 
				else this.fairePiocherJoueur(joueur);
				
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

	@Override
	public void run() {
		
	}
	
}
