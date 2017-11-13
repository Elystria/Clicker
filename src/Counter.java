//TODO mettre la creation de police dans les constructeurs

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import java.lang.System;

import java.awt.*;
//import java.awt.Font;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.*;

import static java.lang.System.currentTimeMillis;


public class Counter {

	/***************************************************
	ATTRIBUTS
	***************************************************/

    // model
    private int nbPixels; //Nombre de pixels possédés
    private int pps; //Nombre de pixels gagnés chaque seconde
    private int lastPps, freqPps; // la dernière fois qu'on a gagné des pixels, la fréquenc à laquelle on les gagnes
    private int ppc; //Nombre de pixels gagnés par clic
    
    // la vue
    private CounterVue vue; // ça s'occupera de tout l'affichage

    //Pour le counter, animation clic
    private boolean clic;
    private static long t1;
    private static long t2;

	/***************************************************
	CONSTRUCTEUR
	***************************************************/

    public Counter() throws SlickException {
        this(0, 0, 1, 2.0f);
    }


    public Counter(int nbPixelsDepart, int pps, int ppc, float size) throws SlickException {
        // initialisation du model
        this.nbPixels = nbPixelsDepart;
        this.pps = pps ;
        this.lastPps = 0;
        this.freqPps = 1000;
        this.ppc = ppc ;
        
        // création de la vue
        this.vue = new CounterVue(this, size);
    }


	/***************************************************
	METHODES
	***************************************************/

    /* Met à jour le nombre total de pixels gagnés grace au clic */
    public void activer(){
        setNbPixels(this.getNbPixels() + this.getPpc());
    }

    /* Met à jour le nombre total de pixels gagnés grace au pps */
    public void incrementer(){
        setNbPixels(this.getNbPixels() + this.getPps());
    }

    /* Affiche le counter à l'écran */
    public void render(Graphics g, WindowGame windows, GameContainer gc, float scale) throws SlickException {
    	// On demande à la vue de s'afficher
    	vue.render(g, windows, gc, scale);

        //on affiche une forme s'il y a eu un clic
        if (clic){
            this.animationClic(0,0);
        }
   }

    //Affiche un produit du shop pendant une courte durée
    public void animationClic(int x, int y) throws SlickException {
        //Choisir la forme à afficher
        Image imageClic = new Image("resources/shop/prod_shop_1.png");
        imageClic.draw(x, y);
        
        // Continue Marie t'es sur le bon chemin ! <3
        // TODO
    }

    /* S'occupe de mettre à jour le counter */
    public void update(int delta) {
    	// On met à jour la vue
    	vue.update(delta);
    	
        // On incrémente le counter toutes les secondes
        this.updatePixels(delta);
    }

    // On augmente le nombre de pixels toutes les secondes !
    private void updatePixels(int delta) {
        lastPps += delta;
        if(lastPps > freqPps) {
            nbPixels += pps;
            lastPps = 0;
        }
    }
    
    /* Réagit lors du clic d'un utilisateur
     * x et y : int coordonnées du clic  */
    public void mouseClicked(int x, int y) throws SlickException {    	
        //Vérifier la localisation du clic
        if (x>=vue.getxPos() && x<=vue.getxPos() + (vue.getAnimation().getHeight() * vue.getTailleActuelle())
            && y>=vue.getyPos() && y<=vue.getyPos()+ vue.getAnimation().getWidth() * vue.getTailleActuelle()){        	
            // ajouter des pixels
            this.activer();
            
            // mettre à jour la vue
            vue.mouseClicked(x, y);

            //afficher un produit aléatoire du shop
            clic = true;
        }
    }



	/***************************************************
	GETTEURS & SETTEURS
	***************************************************/

	public CounterVue getVue() {
		return vue;
	}


	public void setVue(CounterVue vue) {
		this.vue = vue;
	}

    public int getNbPixels() {
        return nbPixels;
    }

    public void setNbPixels(int nbPixels) {
        this.nbPixels = nbPixels;
    }

    public int getPps() {
        return pps;
    }

    public void setPps(int pps) {
        this.pps = pps;
    }

    public int getPpc() {
        return ppc;
    }

    public void setPpc(int ppc) {
        this.ppc = ppc;
    }

}
