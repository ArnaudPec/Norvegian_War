package fr.utt.lo02.vue;

import java.util.Observable;
import java.util.Observer;

public class Vue implements Observer {	
	
	private VueGraphique vueGraphique;
	
	public Vue (){
		//this.vueGraphique = new VueGraphique();
	}
	
	public VueGraphique getVueGraphique(){
		return this.vueGraphique;
	}
	
	
	public static void main(String[] args) {
		//VueConsole vc = new VueConsole();
	}

	public void update(Observable o, Object arg) {
		
	}

}
