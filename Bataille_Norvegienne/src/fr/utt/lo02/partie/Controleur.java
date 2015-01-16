package fr.utt.lo02.partie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.joueur.Humain;
import fr.utt.lo02.joueur.Joueur;
import fr.utt.lo02.vue.VueGraphique;

/**
 * Classe controleur, permettant de faire la gestion entre les vues et les modèles.
 */
public class Controleur{

	private Partie partie;
	private VueGraphique vueGraphique=null;	
	private ArrayList<Carte> listeCartesSelectionnees;
	private boolean peutJouer;


	/**
	 * Constructeur de partie
	 * @param partie
	 */
	public Controleur(Partie partie) {
		//this.vueGraphique = vueGraphique;
		this.partie = partie;
		this.listeCartesSelectionnees = new ArrayList<Carte>();
		this.peutJouer=false;
	}

	/**
	 * Getter de partie
	 * @return partie
	 */
	public Partie getPartie(){
		return this.partie;
	}
	
	
	/**
	 * Getter de vueGraphique
	 * @return VueGraphique
	 */
	public VueGraphique getVueGraphique() {
		return vueGraphique;
	}

	/**
	 * Setter de vueGraphique
	 * @param vueGraphique
	 */
	public void setVueGraphique(VueGraphique vueGraphique) {
		this.vueGraphique = vueGraphique;
	}

	/**
	 * Permet d'ajouter une vueGraphique
	 * @param vueGraphique
	 */
	public void addVue(VueGraphique vueGraphique){	
		this.vueGraphique = vueGraphique;
	}

	
	/**
	 * Permet d'ajouter une carte à la collection
	 * @param Carte c
	 */
	public void ajouterCarteSelectionne(Carte c){
		this.listeCartesSelectionnees.add(c);
		//	System.out.println(this.listeCartesSelectionnees);
	}

	/**
	 * Permet de supprimer une carte
	 * @param Carte c
	 */
	public void supprimerCarteSelectionne(Carte c){
		this.listeCartesSelectionnees.remove(c);		
		//	System.out.println(this.listeCartesSelectionnees);
	}

	/**
	 * Méthode qui permet de gérer l'envoie d'information quand le joueur cliquera sur le bouton "envoyer"
	 * Vérifie la sélection de carte joué, si elle est correcte appellera la méthode jouer
	 * @return un boolean si la si la sélection est bonne ou non.
	 */
	public boolean envoyerSelection(){

		boolean selectionCorrecte = this.verifierSelection();
		if(selectionCorrecte){
			this.peutJouer=true;
			lancerPartie();
		}
		else listeCartesSelectionnees = new ArrayList<Carte>();
		return selectionCorrecte;
	}
	
	/**
	 * Permet d'envoyer la sélection de carte du début de partie que le joueur à choisi 
	 * @return selectionCorrecte de carte
	 */
	public boolean envoyerSelectionEchange(){

		boolean selectionCorrecte = this.verifierSelectionEchange();
		if(selectionCorrecte){
			this.partie.getHumain().getMainJoueur().getListeCartes().removeAll(this.partie.getHumain().getMainJoueur().getListeCartes());
			this.partie.getHumain().getMainJoueur().ajouterPlusieursCartes(this.listeCartesSelectionnees);
		}
		else this.listeCartesSelectionnees = new ArrayList<Carte>();
		return selectionCorrecte;
	}
	
	/**
	 * Méthode permettant de construire le TasVisible après un choix de l'utilisateur.
	 * On procède par comparaison pour déduire ce dernier. On envoie en paramètre une liste
	 * contenant les cartes de son TasVisible et celle de sa main avant le choix.
	 * On compare ensuite avec sa main actuelle pour déduire le tas visible.
	 * @param listeCarte
	 */
	public void envoyerTasVisibleEchange(LinkedList<Carte> listeCarte){
		this.partie.getHumain().getTasVisible().getListeCartes().removeAll(this.partie.getHumain().getTasVisible().getListeCartes());
		
		for (Iterator<Carte> iterator = this.partie.getHumain().getMainJoueur().getListeCartes().iterator(); iterator.hasNext();) {
			Carte carte = (Carte) iterator.next();
			for (Iterator<Carte> iterator2 = listeCarte.iterator(); iterator2.hasNext();) {
				Carte carteTasVisible = (Carte) iterator2.next();
				if(carte.equals(carteTasVisible)) iterator2.remove();
			}
			
		}
		
		this.partie.getHumain().getTasVisible().ajouterPlusieursCartes(listeCarte);
		this.listeCartesSelectionnees = new ArrayList<Carte>();
	}

	/**
	 * Méthode qui vérifie que la sélection de carte est correcte
	 * @return boolean si les cartes sont sélectionnés ou non
	 */
	public boolean verifierSelection(){
		if(this.partie.verifierSelection(this.listeCartesSelectionnees))return true;
		else return false;
	}
	
	/**
	 * Vérifie que les cartes sont correctes lors de l'échange
	 * @return boolean true or false
	 */
	public boolean verifierSelectionEchange(){
		if(this.partie.verifierSelectionEchange(this.listeCartesSelectionnees))return true;
		else return false;
	}
	
	
	/**
	 * Méthode qui fait jouer un joueur Humain 
	 */
	public void jouerHumain(){

		Joueur joueur = this.partie.getListeJoueurs().get(this.partie.getJoueurCourant());			

		this.partie.getTapis().ajouterPlusieursCartes(this.partie.getHumain().choisirCarteAJouer(this.listeCartesSelectionnees));
		int nbCartesPosees=this.listeCartesSelectionnees.size();
		this.listeCartesSelectionnees = new ArrayList<Carte>();									

		if (this.partie.getHumain().estGagnant()) {
			this.vueGraphique.afficherVictoire();
			this.partie.setTerminee(true);
			//this.resetPartie();

		} else {
			this.partie.fairePiocherJoueur(joueur);
		}

		if (this.partie.getTapis().getCarteDuDessus().estSpeciale()) 
		{
			ActionSpeciale actionSpeciale = new ActionSpeciale(this.partie,joueur.getNumJoueur(), nbCartesPosees);
			if(actionSpeciale.appelerBonneMethode() == 14)
			{	
				actionSpeciale.effectuerActionA(this.vueGraphique.choisirJoueur());
			}
		}
		this.partie.incrementerJoueur();
		this.peutJouer=false;
		lancerPartie();
	}

	/**
	 *Méthode permettant de faire jouer une IA 
	 */
	public void IAJouer(){

		Joueur joueur = this.partie.getListeJoueurs().get(this.partie.getJoueurCourant());			
		if (joueur.peutJouer(this.partie.getTapis().getCarteDuDessus())){

			int nbCartesPosees = this.partie.faireJouerJoueur(joueur);

			if (joueur.estGagnant()) {

				this.vueGraphique.afficherDefaite();
				this.partie.setTerminee(true);
			} else {
				this.partie.fairePiocherJoueur(joueur);
			}

			if (this.partie.getTapis().getCarteDuDessus().estSpeciale()) 
			{
				ActionSpeciale actionSpeciale = new ActionSpeciale(this.partie,joueur.getNumJoueur(), nbCartesPosees);
				actionSpeciale.appelerBonneMethode();
			}
		}			

		else{
			joueur.getMainJoueur().getListeCartes().addAll(this.partie.getTapis().prendreTapis());
			System.out.println("Le joueur "+ joueur.getNom()+ " ne peut pas jouer, il ramasse le tapis.\n");			
		}
		this.partie.incrementerJoueur();
		lancerPartie();
	}

	/**
	 *Méthode permettant de gérer un tour de jeu, vérifie si la partie est gagné par un joueur
	 *S'occupe de lancer la bonne méthode de "jouer" 
	 */
	public void lancerPartie(){

		System.out.println("-------------");
		System.out.println(this.partie.getListeJoueurs().get(this.partie.getJoueurCourant()).getNom());
		System.out.println(this.partie.getJoueurCourant());
		System.out.println("-------------");

		Joueur joueur = this.partie.getListeJoueurs().get(this.partie.getJoueurCourant());			

		if(!this.partie.isTerminee()){
			if(joueur instanceof Humain){
				if (joueur.peutJouer(this.partie.getTapis().getCarteDuDessus())) {
					if(peutJouer)
					{
						jouerHumain();
					}		
				}
				else{
					joueur.getMainJoueur().getListeCartes().addAll(this.partie.getTapis().prendreTapis());
					System.out.println("Le joueur "+ joueur.getNom()+ " ne peut pas jouer, il ramasse le tapis.\n");
					this.vueGraphique.notifierPriseTapis();
					this.partie.incrementerJoueur();
					lancerPartie();
				}
			}else{
				IAJouer();
			}
		}
	}
}
