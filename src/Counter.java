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
    private int tailleFreq; // indique à quelle fréquence la taille varie
    private int lastTailleVariation; // indique quand pour la dernière fois la taille a variée
    private List<Float> bouncers; // les petites augmentations de tailles quand un joueur clique sur le counter
    private float bouncerStart; // l'amplitude des bouncers
    private float bouncerDecrease; // la vitesse de disparition des bouncers
    private int lastDecrease; // la dernière fois que les bouncers ont été decrease en ms
    private int decreaseFrequence; // temps après lequel les bouncers sont decrease en ms
    private float tailleActuelle; // la véritable taille finale du counter !
    private Rectangle fond; // Le rectangle de fond permettant de gérer la couleur du tesseract
    private GradientFill fondFill; // Pour remplissage du rectangle
    private boolean sensContraste; //indique quelle couleur s'éclaircit et laquelle s'assombrit dans le tesseract

    //Pour le gradient
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int coefRotation;//indique la vitesse de rotation
    private int zoneGrad;

    //Pour le counter, animation clic
    private boolean clic;
    private static long t1;
    private static long t2;

    /* Constructeurs */

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

        // initialisation de l'affichage
       SpriteSheet spriteSheet = new SpriteSheet("resources/sprites/tesseract_spritesheet_trans.png", 160, 160);
        this.animation = new Animation();
        for(int i = 0; i < NB_SPRITE_ANIMATION; i++){
            this.animation.addFrame(spriteSheet.getSprite(i, 0), TIME_SPRITE_DURATION);
        }
        this.size = size;
        this.taille = size;
        this.tailleVariation = size / 200f;
        this.tailleMax = size + size / 20f;
        this.tailleMin = size - size / 20f;
        this.tailleCroissante = true;
        this.tailleFreq = 1000/Main.FRAME_RATE;
        this.lastTailleVariation = 0;
        this.bouncers = new ArrayList<Float>();
        this.bouncerStart = size / 4f;
        this.bouncerDecrease = size / 60f;
        this.lastDecrease = 0;
        this.decreaseFrequence = 10;
        this.tailleActuelle = this.taille;

        // initialisation de la position d'affichage
        this.xPos = 0;
        this.yPos = 0;

        this.xStart = xPos;
        this.yStart = yPos;
        this.xEnd = (int) (xPos + (animation.getWidth() * tailleActuelle));
        this.yEnd = (int) (yPos + (animation.getHeight() * tailleActuelle));
        this.coefRotation = 3;
        this.zoneGrad = 1;


        //Creation reclangle et fond
        this.fond = new Rectangle(xPos,
                yPos,
                animation.getWidth() * tailleActuelle,
                animation.getHeight() * tailleActuelle);
        this.fondFill = new GradientFill(xPos,
                yPos,
                Color.cyan,
                xEnd,
                yEnd,
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
    public void render(Graphics g, WindowGame windows, GameContainer gc, float scale) throws SlickException {


        // on récupère la position d'affichage
        this.getPositionAffichage(windows, scale);

        // On affiche le background
        this.renderBackground(g, windows, scale);

        // on affiche l'animation
        this.renderAnimation(scale);

        // on affiche le nombre de pixels juste au dessus
        this.renderText(g, windows);

        //on affiche une forme s'il y a eu un clic
        if (clic){
            this.animationClic(0,0);
        }
   }

    private void getPositionAffichage(WindowGame windows, float scale) {
        this.xPos = windows.getWindowsWidth() / 2 - ((int) ((animation.getWidth() * scale) / 2));
        this.yPos = windows.getWindowsHeight() / 2 - ((int) ((animation.getHeight() * scale) / 2));
    }


    //Affiche un produit du shop pendant une courte durée
    public void animationClic(int x, int y) throws SlickException {
        //Choisir la forme à afficher
        Image imageClic = new Image("resources/shop/prod_shop_1.png");
        imageClic.draw(x, y);

    }

    private void renderBackground(Graphics g, WindowGame windows, float scale) {
        //Premier affichage
        if(this.xStart == 0){
            this.xStart = windows.getWindowsWidth() / 2;
            this.yStart = yPos;
            this.xEnd = this.xStart;
            this.yEnd = this.yStart + (int) (animation.getHeight() * scale);
            this.fondFill.setStart(xStart, yStart);
            this.fondFill.setEnd(xEnd, yEnd);
        }

        // on recalcule le fond puis on l'affiche
        this.fond.setBounds(this.xPos,
                this.yPos,
                animation.getWidth() * scale,
                animation.getHeight() * scale);

        //On calcule et on fait tourner le gradient
        int xCentre = getXCentre();
        int yCentre = getYCentre();
        int oldZoneGrad = this.zoneGrad;
        if (getXStart() >= xCentre){
            //Si Ystart est sup a Ycentre
            for (int i = 1; i<= coefRotation; i++){
                this.yStart--;
                this.yEnd++;
            }
            if (getYStart() >= yCentre) {
                //System.out.println("En bas à droite");
                this.zoneGrad = 3;
                for (int i = 1; i<= coefRotation; i++){
                    this.xStart++;
                    this.xEnd--;
                }

            } else {
                //System.out.println("En haut à droite");
                this.zoneGrad = 4;
                for (int i = 1; i <= coefRotation; i++) {
                    this.xStart--;
                    this.xEnd++;
                }
            }
        }else {
            for(int i = 1; i <= coefRotation; i++) {
                this.yStart++;
                this.yEnd--;
            }
            //Sinon
            //Si Ystart est sup à Ycentre
            if (getYStart() < yCentre) {
                //System.out.println("En haut à gauche ");
                this.zoneGrad = 1;
                for(int i = 1; i <= coefRotation; i++) {
                    this.xStart--;
                    this.xEnd++;
                }
                // Sinon
            } else {
                //System.out.println("En bas à gauche ");
                this.zoneGrad = 2;
                for(int i = 1; i <= coefRotation; i++) {
                    this.xStart++;
                    this.xEnd--;
                }
            }
        }

        if(this.zoneGrad != oldZoneGrad){
           /* System.out.println("Ancienne Zone :" + oldZoneGrad);
            System.out.println("xStart : " + this.xStart);
            System.out.println("yStart : " + this.yStart);*/
        }
        //Debut et fin du gradient
        this.fondFill.setStart(this.xStart, this.yStart);
        this.fondFill.setEnd(this.xEnd,
                this.yEnd);

        g.fill(this.getFond(), this.getFondFill());
    }

    private void renderAnimation(float scale) {
        this.animation.draw(xPos, yPos, animation.getWidth() * scale, animation.getHeight()*scale);
    }

    private void renderText(Graphics g, WindowGame windows) throws SlickException {
        /*
        String s = "Pixels : " + nbPixels;
        Font font = g.getFont();
        int xString = windows.getWindowsWidth() / 2 - font.getWidth(s) / 2;
        int yString = windows.getWindowsHeight() / 2 - font.getHeight(s) * 2 - (int) (animation.getHeight() * size + size / 20) / 2;
        g.setColor(new Color(255, 255, 255));
        g.setFont(font);
        g.drawString(s, xString, yString);
        */
        /* TESTS SUR LES FONDS */
        /* GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {

            ge.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("resources/fonts/pixelhole/pixelhole.ttf")));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] fonts = ge.getAvailableFontFamilyNames();
        for (String font : fonts) {
            System.out.println(font);
            if (font.equals("Pixelhole")){
                String fontNbPixels = font;
            }

        } */

        String s = "Pixels : " + nbPixels;
        //Font pixelFont = g.getFont();
        UnicodeFont pixelFont = new UnicodeFont("resources/fonts/pixelhole/pixelhole.ttf", 55, false, false);

        pixelFont.addAsciiGlyphs();
        pixelFont.addGlyphs(400, 600);
        pixelFont.getEffects().add(new ColorEffect());
        pixelFont.loadGlyphs();

        int xString = windows.getWindowsWidth() / 2 - pixelFont.getWidth(s) / 2;
        int yString = windows.getWindowsHeight() / 2 - pixelFont.getHeight(s) * 2 - (int) (animation.getHeight() * size + size / 20) / 2;
        //g.setColor(new Color(175, 175, 175));
        g.setFont(pixelFont);
        g.drawString(s, xString, yString);

       /* FIN DES TESTS */
    }




    /* S'occupe de mettre à jour le counter */
    public void update(int delta) {
        // mise à jour de la taille initiale du counter
        this.updateTaille(delta);

        // Mise à jour des bouncers
        this.updateBouncers(delta);

        // On incrémente le counter toutes les secondes
        this.updatePixels(delta);
    }

    private void updateTaille(int delta) {
        lastTailleVariation += delta;
        if(lastTailleVariation > tailleFreq) {
            if (tailleCroissante) {
                if (taille < tailleMax) {
                    taille += tailleVariation;
                } else {
                    taille -= tailleVariation;
                    tailleCroissante = false;
                }
            } else {
                if (taille > tailleMin) {
                    taille -= tailleVariation;
                } else {
                    taille += tailleVariation;
                    tailleCroissante = true;
                }
            }
            lastTailleVariation = 0;
        }
    }

    private void updateBouncers(int delta) {
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
    }

    private void updatePixels(int delta) {
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
    public void mouseClicked(int x, int y) throws SlickException {
        //Vérifier la localisation du clic
        if (x>=xPos && x<=xPos + (getAnimation().getHeight() * tailleActuelle)
            && y>=yPos && y<=yPos + getAnimation().getWidth() * tailleActuelle){
            //Changer la couleur du tesseract de manière aleatoire;
            Random rdn = new Random();
            Color colorStart = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));
            Color colorEnd = inverseColor(colorStart);

            this.setCouleurTess(colorStart, colorEnd);

            // ajouter un bouncer
            this.bouncers.add(bouncerStart);

            // ajouter des pixels
            this.activer();

            //afficher un produit aléatoire du shop
            clic = true;
        }
    }

    /* Renvoie l'opposé de la couelur actuelle */
    public Color inverseColor(Color color){
        int r = color.getRed();
        int v = color.getGreen();
        int b = color.getBlue();

        //Inverser les composants RVB pour donner la couleur opposée
        return (new Color(255-r, 255-v, 255-b));
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

    /*Renvoie l'abscisse du centre de l'image */
    public int getXCentre(){
        int taille = (int) (animation.getWidth()*getTailleActuelle());
        return getxPos()+taille/2;
    }

    /* Renvoie l'ordonnée du centre de l'image */
    public int getYCentre(){
        int taille = (int) (animation.getHeight()*getTailleActuelle());
        return getyPos()+taille/2;
    }

    public int getXStart(){
        return (int) this.getFondFill().getStart().getX();
    }
    public int getYStart() {
        return (int) this.getFondFill().getStart().getY();
    }
    public int getXEnd() {
        return (int) this.getFondFill().getEnd().getX();
    }
    public int getYEnd() {
        return (int) this.getFondFill().getEnd().getY();
    }
}
