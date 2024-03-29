package fr.utt.lo02.vue;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.partie.Controleur;

/**
 * Classe en charge de l'affichage du tapis du jeu de carte
 *
 */
public class TapisPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private CartePanel derniereCarte;
	private CartePanel dessinPioche;
	
	public TapisPanel(BufferedImage matriceCarte, Controleur partieControleur){
		this.setPreferredSize(new Dimension(500,320));
		
		Carte cartePrecedente = partieControleur.getPartie().getTapis().getCarteDuDessus();
		
		this.dessinPioche = new CartePanel(cartePrecedente, matriceCarte, false, 0.7, partieControleur);

		this.derniereCarte = new CartePanel(null, matriceCarte, false, 0.7, partieControleur);
		this.add(this.derniereCarte);	
		this.add(this.dessinPioche);
	}

	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	public void setDessinPioche(Carte c){
		this.dessinPioche.setCarte(c);
	}

}
