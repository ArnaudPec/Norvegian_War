package fr.utt.lo02.partie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

import apple.awt.CImage.Creator;
import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.carte.Pioche;
import fr.utt.lo02.carte.Tapis;
import fr.utt.lo02.joueur.IaAleatoire;
import fr.utt.lo02.joueur.IaOffensive;
import fr.utt.lo02.joueur.Joueur;

public class Partie {

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
		
		if(instancePartie==null)
		{
			instance = new Partie();
			instancePartie = instance;
		}
		else
		{
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
	
	public Joueur getJoueur(int position){
		Joueur joueur=null;
		try 
		{
			joueur = this.listeJoueurs.get(position);	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return joueur;	
	}

	public void setJoueurCourant(int joueurCourant) {
		this.joueurCourant = joueurCourant;
	}

	public ArrayList<Joueur> getListeJoueurs() {
		return this.listeJoueurs;
	}

	public void setListeJoueurs(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	public Pioche getPioche() {
		return this.pioche;
	}

	public void setPioche(Pioche pioche) {
		this.pioche = pioche;
	}

	public Tapis getTapis() {
		return this.tapis;
	}

	public void setTapis(Tapis tapis) {
		this.tapis = tapis;
	}

	public static void setInstancePartie(Partie instancePartie) {
		Partie.instancePartie = instancePartie;
	}
	
	
	public void ajouterJoueur(Joueur joueur){
		this.listeJoueurs.add(joueur);
		this.nbJoueurs++;
	}
	
	public void interfaceAjouterJoueur()
	{
		boolean conditionIA = false;
		boolean conditionJ = false;
		
		int nbJoueursHumain=0;
		int nbJoueursIA=0;
		
		int niveau=0;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Combien de joueur humain voulez vous ajouter");
		String demandeNbJoueurs = sc.nextLine();
		
		nbJoueursHumain = Integer.parseInt(demandeNbJoueurs);
		
		
		do {
			if(nbJoueursHumain > 0 && nbJoueursHumain <= 11 )
			{
				conditionJ = true;
			}
		} while (!conditionJ);
			

		System.out.println("Voulez vous ajouter des IA ?(Oui/Non)");
		String demandeIA = sc.nextLine();
		
		if("OUI".equals(demandeIA.toUpperCase())){
			
			System.out.println("Combien d'IA voulez vous ajouter ?");
			String demandeNbIA = sc.nextLine();
			
			nbJoueursIA= Integer.parseInt(demandeNbIA);
			
			do {
				if(nbJoueursHumain+nbJoueursIA > 0 && nbJoueursHumain+nbJoueursIA <= 11 )
				{
					conditionJ = true;
				}
			} while (!conditionIA);
			
			System.out.println("Quel niveau d'IA désirez vous (0=aléatoire, 1=offensive)");
			String niveauIA = sc.nextLine();
			niveau = Integer.parseInt(niveauIA);
		}
		else{
			if(nbJoueursHumain<2)
			{
				System.out.println("Vous ne pouvez pas lancer de partie seul, une IA a été crée ");
				nbJoueursIA=1;
				niveau=0;
			}
		}
		
		creationJoueur(nbJoueursHumain);
		creationIA(nbJoueursIA, niveau);
		
	}
	
	
	public void creationJoueur(int nbJoueur)
	{
		Scanner sc = new Scanner(System.in);

		for (int i = 0; i < nbJoueur ; i++) {
			System.out.println("Entrez le nom du joueur");
			String nomJoueur = sc.nextLine();
			Joueur joueur = new Joueur(nomJoueur, this.nbJoueurs);
			ajouterJoueur(joueur);
		}
	}
	
	public void creationIA(int nbIA, int niveau){
		
		for (int i = 0; i < nbIA ; i++) {
			if(niveau == 0)
			{
				IaAleatoire iaAleatoire = new IaAleatoire("ia"+i, this.nbJoueurs);
				ajouterJoueur(iaAleatoire);
				this.nbJoueurs++;
			}
			if(niveau == 1)
			{
				IaOffensive iaOfensive = new IaOffensive("ia"+i, this.nbJoueurs);
				ajouterJoueur(iaOfensive);
				this.nbJoueurs++;
			}

		}
		
	}
	
	
	public boolean estGagnee()
	{
		return false;
	}
	
	
	/**
	 *Methode permettant d'initialiser le jeu.
	 *-Melanger les cartes
	 *-Distribution des cartes
	 *-lancer la boucle de jeu 
	 */
	public void initialisationPartie(Partie partie){
		
		this.interfaceAjouterJoueur();
		this.pioche.melanger();
		//listeJoueurs.get(0).getTasCache().ajouterCarte(new Carte(2,10));
		//System.out.println(listeJoueurs.get(0).getTasCache());
		this.pioche.distribuerCarte(partie);
		//lancerPartie();
		
	}
	
	
	/**
	 * Methode permettant de lancer la boucle de jeu.
	 */
	public void lancerPartie(){
		
		System.out.println("La partie démarre");
		boolean estDanish = false;
		boolean estGagnee = false;
		
		while(!estGagnee)
		{
			//tant que tout le monde peu jouer ..
			while(!(estDanish && estGagnee))
			{
				Joueur joueur = this.listeJoueurs.get(joueurCourant); //récupération du joueur courant
				//Si le joueur peu jouer alors on passe au suivant
				if(joueur.peutJouer(this.getTapis().carteDuDessus()))
				{
					System.out.println(joueur.getMainJoueur().toString());
					// ! \\ 
					//à modifier car le joueur peut choisir de jouer plusieurs cartes 
					
					Carte[] carteJouees = joueur.choisirCarteAJouer(this.getTapis().carteDuDessus());
					this.getTapis().ajouterPlusieursCartes(carteJouees);
					
					for (int i = 0; i < carteJouees.length; i++) {
						joueur.getMainJoueur().ajouterCarte(this.pioche.prendreCarteDuDessus());
					}
									
					joueurCourant++;
					
					//Si le joueur à posé et qu'il n'a plus de carte il à gagné !
					if(joueur.estGagnant())
					{
						estGagnee = true;
					}
				}
				else //Sinon on donne la main au dernier à avoir poser et on fini la manche.
				{
					joueur.getMainJoueur().getListeCartes().addAll(this.tapis.getListeCartes());//Je donne le tapis au joueur qui n'a pas pu jouer.
					this.tapis=null;
					joueurCourant--;
					estDanish = true;
				}
			}
		}	
	}
	
	/**
	 * Permet de faire piocher un joueur
	 * @param nbCartes nombre de carte à faire piocher
	 * @param joueur joueur qui doit piocher
	 */
	public void piocher(int nbCartes, Joueur joueur){
		LinkedList<Carte> liste = new LinkedList<Carte>();
		
		for (int i = 0; i < nbCartes; i++) {
			liste.add(this.pioche.prendreCarteDuDessus());
		}
		
		joueur.getMainJoueur().getListeCartes().addAll(liste);
	}
	
	
	 public static void main(String[] args) {
		
		System.out.println("Nouvelle partie de Bataille Norvegienne.");		
		Partie partie = getInstancePartie();
		partie.initialisationPartie(partie);
		partie.lancerPartie();
		//System.out.println(partie.getJoueur(0).getMainJoueur());
	
	}
	
	
}
