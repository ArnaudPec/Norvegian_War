package fr.utt.lo02.carte;

import java.util.Collections;
import java.util.LinkedList;

import fr.utt.lo02.partie.Partie;



/**
 * Classe singleton qui hérite de Tas
 */
public class Pioche extends Tas {

	/**
	 * Attribut static permettant de stocker l'instance de pioche afin de ne pouvoir créer qu'une seule pioche. 
	 */
	private static Pioche pioche = null;
	
		
	/**
	 * Constructeur de pioche, créer l'ensemble des cartes de la pioche.  
	 */
	private Pioche() 
	{
		LinkedList<Carte> pioche = new LinkedList<Carte>();
		
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 2; j < 15; j++) 
			{	
				Carte carte = new Carte(i, j);
				pioche.add(carte);
			}
		}
		
		this.listeCartes = pioche;
	}

	/**
	 * Permet d'appeler une instance de pioche, s'il n'y a pas elle en crée une autrement elle retourne celle déjà crée.
	 * @return une instance de pioche
	 */
	public static Pioche getInstannce()
	{
		Pioche instance;
		
		if(pioche==null)
		{
			Pioche nouvellePioche = new Pioche();
			pioche = nouvellePioche;
			instance = pioche;
		}
		else
		{
			instance = pioche; 
		}
		
		return instance;
 	}
	
	
	@Override
	public void deplacerCarte(int position, Tas main) 
	{
		try {
			main.ajouterCarte(this.listeCartes.get(position));
			this.listeCartes.remove(position);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void choisirCarteADeplacer() {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Permet de mélanger la pioche 
	 */
	public void melanger()
	{
		 Collections.shuffle(this.listeCartes);  
	}
	
	/**
	 * Permet de prendre la première carte de la pioche et de la supprimer de la pioche.
	 * @return la première carte de la pioche.
	 */
	public Carte prendreCarteDuDessus()
	{
		return this.listeCartes.pollFirst();
	}
	
	public void distribuerCarte(Partie partie)
	{
		
		for (int i = 0; i < partie.getnbJoueurs() ; i++) {
			
		}
	}
	
	
}
