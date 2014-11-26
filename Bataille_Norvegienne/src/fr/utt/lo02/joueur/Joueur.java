package fr.utt.lo02.joueur;

import java.util.Scanner;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.carte.MainJoueur;
import fr.utt.lo02.carte.TasCache;
import fr.utt.lo02.carte.TasVisible;

public class Joueur implements Strategie{
	
	protected int numJoueur;
	protected String nom;
	protected MainJoueur mainJoueur;
	protected TasCache tasCache;
	protected TasVisible tasVisible;
	
	public Joueur(String nomJoueur, int numJoueur)
	{
		this.numJoueur = numJoueur;
		this.nom = nomJoueur;
		this.mainJoueur = new MainJoueur();
		this.tasCache = new TasCache();
		this.tasVisible = new TasVisislbe();
	}
	
	/**
	 * @param numJoueur
	 * @param nom
	 * @param mainJoueur
	 * @param tasCachee
	 * @param tasVisible
	 */
	public Joueur(int numJoueur, String nom, MainJoueur mainJoueur, TasCache tasCache,
			TasVisible tasVisible) {
		super();
		this.numJoueur = numJoueur;
		this.nom = nom;
		this.mainJoueur = mainJoueur;
		this.tasCache = tasCache;
		this.tasVisible = tasVisible;
	}
	public int getNumJoueur() {
		return numJoueur;
	}
	public void setNumJoueur(int numJoueur) {
		this.numJoueur = numJoueur;
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public MainJoueur getMainJoueur() {
		return this.mainJoueur;
	}
	public void setMainJoueur(MainJoueur mainJoueur) {
		this.mainJoueur = this.mainJoueur;
	}
	public TasCache getTasCache() {
		return this.tasCache;
	}
	public void sethTasCache(TasCache tasCachee) {
		this.tasCache = tasCachee;
	}
	public TasVisible getTasVisible() {
		return tasVisible;
	}
	public void setTasVisible(TasVisible tasVisible) {
		this.tasVisible = tasVisible;
	}
	
	
	public void estDanish() { //Il faut enlever cette méthode ici, et on retourne un boolean false si le joueur ne peu pas jouer.
		
	} 
	
	public boolean estGagnant(){
		boolean resultat = false;
		if(this.tasCache.getListeCartes() == null && this.mainJoueur.getListeCartes() == null)
		{
			resultat = true;
		}
		
		return resultat;
	}
	
	public boolean peuJouer(){
		//vérifie que le joueur peu jouer.
	}
	
	public void changerCarte() {
		
	}
	
	public Carte[] choisirCarteAJouer(){
		
		
		
		System.out.println("choisissez les ou la carte(s) que vous voulez jouer.");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		
		String[] split = str.split("-");
		
		Carte[] listeCarte = new Carte[split.length];
		
		for(int i = 0; i < split.length; i++) {
			listeCarte[i] = this.mainJoueur.getListeCartes().get(Integer.parseInt(split[i]));
		}
		
		return listeCarte;	
		
	}
	
	
}