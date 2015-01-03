package fr.utt.lo02.vue;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.partie.PartieControleur;

public class TapisPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private CartePanel derniereCarte;
	private CartePanel dessinPioche;
	
	public TapisPanel(BufferedImage matriceCarte, PartieControleur partieControleur){
		
		this.setPreferredSize(new Dimension(600,320));
		//this.dessinPioche = new CartePanel(new Carte(0, 0), matriceCarte, 0.7, partieControleur);
		int derniereCarte = partieControleur.getPartie().getPioche().getListeCartes().size();
		this.derniereCarte = new CartePanel(null, matriceCarte, 0.7, partieControleur, derniereCarte);
		this.add(this.derniereCarte);	
		//this.add(this.dessinPioche);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDessinPioche(Carte c){
		this.dessinPioche.setCarte(c);
	}

}
