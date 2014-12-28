package fr.utt.lo02.vue;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Fenêtre affichant les règles du jeu
 *
 */
public class FenetreReglesJeu extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JEditorPane html;

	public FenetreReglesJeu() {

		this.setTitle("Règles du Jeu");
		this.setSize(700, 500);
		setBackground(Color.gray);
		this.getContentPane().setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);

		JPanel topPanel = new JPanel();
		JButton button = new JButton("Ok, je ne tricherai pas, promis !");
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.SOUTH);
		File f = new File("ressources/regles.html");
		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		try {
			URL url = new URL("file://" + f.getAbsolutePath());
			html = new JEditorPane(url);
			html.setEditable(false);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.getViewport().add(html, BorderLayout.CENTER);

			topPanel.add(scrollPane, BorderLayout.CENTER);

		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}

	
}