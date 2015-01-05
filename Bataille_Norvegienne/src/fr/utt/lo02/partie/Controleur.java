package fr.utt.lo02.partie;

import java.util.ArrayList;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.joueur.Humain;
import fr.utt.lo02.joueur.Joueur;
import fr.utt.lo02.vue.VueGraphique;

public class Controleur{

	private Partie partie;
	private VueGraphique vueGraphique=null;
	private ArrayList<Carte> listeCartesSelectionnees;

	private boolean peutJouer;


	public Controleur(Partie partie) {
		//this.vueGraphique = vueGraphique;
		this.partie = partie;
		this.listeCartesSelectionnees = new ArrayList<Carte>();
		this.peutJouer=false;
	}

	public Partie getPartie(){
		return this.partie;
	}
	

	public void addVue(VueGraphique vueGraphique){	
		this.vueGraphique = vueGraphique;
	}

	public void ajouterCarteSelectionne(Carte c){
		this.listeCartesSelectionnees.add(c);
		//	System.out.println(this.listeCartesSelectionnees);
	}

	public void supprimerCarteSelectionne(Carte c){
		this.listeCartesSelectionnees.remove(c);		
		//	System.out.println(this.listeCartesSelectionnees);
	}

	public boolean envoyerSelection(){

		boolean selectionCorrecte = this.verifierSelection();
		if(selectionCorrecte){
			this.peutJouer=true;
			lancerPartie();
		}
		else listeCartesSelectionnees = new ArrayList<Carte>();
		return selectionCorrecte;
	}

	public boolean verifierSelection(){
		if(this.partie.verifierSelection(this.listeCartesSelectionnees))return true;
		else return false;
	}

	public void boutonJouer(){
		this.vueGraphique.changerBouton();
	}


	public void jouerHumain(){

		Joueur joueur = this.partie.getListeJoueurs().get(this.partie.getJoueurCourant());			

		this.partie.getTapis().ajouterPlusieursCartes(this.partie.getHumain().choisirCarteAJouer(this.listeCartesSelectionnees));
		int nbCartesPosees=this.listeCartesSelectionnees.size();
		this.listeCartesSelectionnees = new ArrayList<Carte>();									

		if (this.partie.getHumain().estGagnant()) {
			this.vueGraphique.afficherVictoire();
			this.partie.setTerminee(true);
			this.resetPartie();

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

	public void IAJouer(){

		Joueur joueur = this.partie.getListeJoueurs().get(this.partie.getJoueurCourant());			
		if (joueur.peutJouer(this.partie.getTapis().getCarteDuDessus())){

			int nbCartesPosees = this.partie.faireJouerJoueur(joueur);

			if (joueur.estGagnant()) {
				//estDanish = true; 
				//gagnant = joueur;

				this.vueGraphique.afficherDefaite();
				this.partie.setTerminee(true);
				this.resetPartie();


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

	public void resetPartie(){
		this.partie.reset();
		this.vueGraphique=new VueGraphique(this.partie, this);
	}

}
