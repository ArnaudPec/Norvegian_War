package fr.utt.lo02.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.swing.JPanel;

import fr.utt.lo02.carte.Carte;
import fr.utt.lo02.carte.MainJoueur;


public class MainScrollPane extends ScrollPane {
	
	private static final long serialVersionUID = 1L;
	private JPanel main;
	private MainJoueur mainJoueur;
	private BufferedImage matriceCarte;

	public MainScrollPane(BufferedImage matriceCarte) throws HeadlessException {
		super();
		
		
		//test
		this.mainJoueur = new MainJoueur();
		this.mainJoueur.ajouterCarte(new Carte(3, 8));
		this.mainJoueur.ajouterCarte(new Carte(0, 13));
		this.mainJoueur.ajouterCarte(new Carte(1, 2));
		this.mainJoueur.ajouterCarte(new Carte(3, 14));
		this.mainJoueur.ajouterCarte(new Carte(2, 2));
		this.mainJoueur.ajouterCarte(new Carte(2, 14));
		//fin 
		
		
		this.matriceCarte = matriceCarte;
		this.setPreferredSize(new Dimension(800,200));
		this.ajouterCartesMainJoueur();
		
		
		this.add(this.main);
	}
	
	public MainScrollPane(BufferedImage matriceCarte, MainJoueur mainJoueur) throws HeadlessException {
		super();
		this.mainJoueur = mainJoueur;
		this.matriceCarte = matriceCarte;
		this.setPreferredSize(new Dimension(800,200));
		
		this.ajouterCartesMainJoueur();
		
		this.add(this.main);
	}

	@Override
	public void setBackground(Color c) {
		super.setBackground(c);
		this.main.setBackground(c);
	}
	
	private void ajouterCartesMainJoueur(){
		this.main = new JPanel();
		for (Iterator<Carte> iterator = this.mainJoueur.getListeCartes().iterator(); iterator.hasNext();) {
			Carte carte = (Carte) iterator.next();
			this.main.add(new CartePanel(carte, this.matriceCarte, 0.5));
			
		}
	}
	
	
	
	

	

}