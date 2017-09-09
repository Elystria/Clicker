//TODO : mettre des couleurs aleatoires pour le gradient

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.TextField;

import java.awt.*;
import java.awt.FontMetrics;
import java.util.List;
import java.util.Random;
import java.util.*;


public class Counter {


    /* Attributs */

    // model
    private int nbPixels; //Nombre de pixels possédés
    private int pps; //Nombre de pixels gagnés chaque seconde
    private int lastPps, freqPps; // la dernière fois qu'on a gagné des pixels, la fréquenc à laquelle on les gagnes
    private int ppc; //Nombre de pixels gagnés par clic

    // vue
    private Animation animation; // l'animation du tesserac
    private final static int NB_SPRITE_ANIMATION = 15; // son nombre d'images
    private final static int TIME_SPRITE_DURATION = 100; // la durée de la transition entre chaque images
    private int xPos; // la position de l'animation
    private int yPos;
    private float size; // la taille multiplicative globale voulue du counter
    private float taille; // sa taille relative : 1 = taille inchangée
    private float tailleVariation; // sa variation de taille maximum
    private float tailleMin, tailleMax; // les variations extremums de taille
    private boolean tailleCroissante; // indique si la taille est en train de grandir
    private List<Float> bouncers; // les petites augmentations de tailles quand un joueur clique sur le counter
    private float bouncerStart; // l'amplitude des bouncers
    private float bouncerDecrease; // la vitesse de disparition des bouncers
    private int lastDecrease; // la dernière fois que les bouncers ont été decrease en ms
    private int decreaseFrequence; // temps après lequel les bouncers sont decrease en ms
    private float tailleActuelle; // la véritable taille finale du counter !
    private Rectangle fond; // Le rectangle de fond permettant de gérer la couleur du tesseract
    private GradientFill fondFill; // Pour remplissage du rectangle

    /* Constructeurs */

    public Counter() throws SlickException {
        this(0, 1, 1, 2.0f);
    }


    public Counter(int nbPixelsDepart, int pps, int ppc, float size) throws SlickException {
        // initialisation du model
        this.nbPixels = nbPixelsDepart;
        this.pps = pps ;
        this.lastPps = 0;
        this.freqPps = 1000;
        this.ppc = ppc ;

        // initialisation de l'affichage
       SpriteSheet spriteSheet = new SpriteSheet("resources/sprites/tesseract_spritesheet_trans.png", 160, 160);
        this.animation = new Animation();
        for(int i = 0; i < NB_SPRITE_ANIMATION; i++){
            this.animation.addFrame(spriteSheet.getSprite(i, 0), TIME_SPRITE_DURATION);
        }
        this.size = size;
        this.taille = size;
        this.tailleVariation = size / 2000f;
        this.tailleMax = size + size / 20f;
        this.tailleMin = size - size / 20f;
        this.tailleCroissante = true;
        this.bouncers = new ArrayList<Float>();
        this.bouncerStart = size / 4f;
        this.bouncerDecrease = size / 60f;
        this.lastDecrease = 0;
        this.decreaseFrequence = 10;
        this.tailleActuelle = this.taille;

        // initialisation de la position d'affichage
        this.xPos = 0;
        this.yPos = 0;

        //Creation reclangle et fond
        this.fond = new Rectangle(xPos,
                yPos,
                animation.getWidth() * tailleActuelle,
                animation.getHeight() * tailleActuelle);
        this.fondFill = new GradientFill(xPos,
                yPos,
                Color.cyan,
                xPos + (animation.getWidth() * tailleActuelle),
                yPos + (animation.getHeight() * tailleActuelle),
                Color.red);

   }


    /* Méthodes */

    /* Met à jour le nombre total de pixels gagnés grace au clic */
    public void activer(){
        setNbPixels(this.getNbPixels() + this.getPpc());
    }

    /* Met à jour le nombre total de pixels gagnés grace au pps */
    public void incrementer(){
        setNbPixels(this.getNbPixels() + this.getPps());
    }

    /* Affiche le counter à l'écran */
    public void afficher(Graphics g, WindowGame windows, GameContainer gc, float scale) {
        // on récupère la position d'affichage
        this.xPos = windows.getWindowsWidth() / 2 - ((int) ((animation.getWidth() * scale) / 2));
        this.yPos = windows.getWindowsHeight() / 2 - ((int) ((animation.getHeight() * scale) / 2));

        // on recalcul le fond puis on l'affiche
        this.fond.setBounds(this.xPos,
                this.yPos,
                animation.getWidth() * scale,
                animation.getHeight() * scale);
        this.fondFill.setStart(this.xPos, this.yPos);
        this.fondFill.setEnd(this.xPos+(animation.getWidth() * scale),
                             this.yPos+(animation.getHeight() * scale));
        g.fill(this.getFond(), this.getFondFill());

        // on affiche l'animation
        this.animation.draw(xPos, yPos, animation.getWidth() * scale, animation.getHeight()*scale);

        // on affiche le nombre de pixels juste au dessus
        // METHODE QUI NE MARCHE PAS !!!
        /*
        try {
            java.awt.Font javaFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    org.newdawn.slick.util.ResourceLoader.getResourceAsStream("resources/fonts/pixelmix/pixelmix.ttf"));
            javaFont = javaFont.deriveFont(java.awt.Font.PLAIN, 12.f);
            org.newdawn.slick.UnicodeFont uniFont = new org.newdawn.slick.UnicodeFont(javaFont);
            uniFont.addAsciiGlyphs();
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        String s = "Pixels : " + nbPixels;
        Font font = g.getFont();
        int xString = windows.getWindowsWidth() / 2 - font.getWidth(s) / 2;
        int yString = windows.getWindowsHeight() / 2 - font.getHeight(s) * 2 - (int) (animation.getHeight() * size + size / 20) / 2;
        g.setColor(new Color(175, 175, 175));
        g.setFont(font);
        g.drawString(s, xString, yString);
    }

    /* S'occupe de mettre à jour le counter */
    public void update(int delta) {
        // mise à jour de la taille du counter
        if(tailleCroissante) {
            if(taille < tailleMax) {
                taille += tailleVariation;
            } else {
                taille -= tailleVariation;
                tailleCroissante = false;
            }
        } else {
            if(taille > tailleMin) {
                taille -= tailleVariation;
            } else {
                taille += tailleVariation;
                tailleCroissante = true;
            }
        }

        // On fait bouncer le counter
        tailleActuelle = taille;
        lastDecrease += delta;
        for (int i = 0; i < bouncers.size(); i++) {
            // on ajoute les bouncers à la taille
            tailleActuelle += bouncers.get(i);

            // on fait diminuer les bouncers
            if(lastDecrease > decreaseFrequence) {
                bouncers.set(i, bouncers.get(i) - bouncerDecrease);
                if (bouncers.get(i) <= 0) {
                    bouncers.remove(i);
                    i = i - 1;
                }
            }
        }
        if(lastDecrease > decreaseFrequence) {
            lastDecrease = 0;
        }

        // On incrémente le counter toutes les secondes
        lastPps += delta;
        if(lastPps > freqPps) {
            nbPixels += pps;
            lastPps = 0;
        }
    }

    /* Change la couleur du tesseract */
    public void setCouleurTess(Color coulDebut, Color coulFin){
        this.getFondFill().setStartColor(coulDebut);
        this.getFondFill().setEndColor(coulFin);
    }

    /* Réagit lors du clic d'un utilisateur
     * x et y : int coordonnées du clic  */
    public void mouseClicked(int x, int y){
        int xPos = this.getxPos();
        int yPos = this.getyPos();
        //Vérifier la localisation du clic
        if (x>=xPos && x<=xPos + (getAnimation().getHeight() * tailleActuelle)
            && y>=yPos && y<=yPos + getAnimation().getWidth() * tailleActuelle){
            //Changer la couleur du tesseract de manière aleatoire;
            Random rdn = new Random();
            Color colorStart = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));
            Color colorEnd = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));

            this.setCouleurTess(colorStart, colorEnd);

            // on ajoute un bouncer !
            this.bouncers.add(bouncerStart);
        }
    }


    /* Getteurs et Setteurs */

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

    public Animation getAnimation() {
        return animation;
    }

    public Rectangle getFond() {return fond;}

    public void setFond(Rectangle fond) { this.fond = fond;}

    public GradientFill getFondFill() {return fondFill;}

    public void setFondFill(GradientFill fondFill) {this.fondFill = fondFill;}

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public float getTailleActuelle() {
        return tailleActuelle;
    }
}
