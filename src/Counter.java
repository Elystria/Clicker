
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
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
    private boolean sensContraste; //indique quelle couleur s'éclaircit et laquelle s'assombrit dans le tesseract

    //Pour le gradient
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int coefRotation;//indique la vitesse de rotation
    private int zoneGrad;

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

        this.xStart = xPos;
        this.yStart = yPos;
        this.xEnd = (int) (xPos + (animation.getWidth() * tailleActuelle));
        this.yEnd = (int) (yPos + (animation.getHeight() * tailleActuelle));
        this.coefRotation = 1;
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
    public void afficher(Graphics g, WindowGame windows, GameContainer gc, float scale) {
        // on récupère la position d'affichage
        this.xPos = windows.getWindowsWidth() / 2 - ((int) ((animation.getWidth() * scale) / 2));
        this.yPos = windows.getWindowsHeight() / 2 - ((int) ((animation.getHeight() * scale) / 2));
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
            System.out.println("Ancienne Zone :" + oldZoneGrad);
            System.out.println("xStart : " + this.xStart);
            System.out.println("yStart : " + this.yStart);
        }
        //Debut et fin du gradient
        this.fondFill.setStart(this.xStart, this.yStart);
        this.fondFill.setEnd(this.xEnd,
                             this.yEnd);

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
            Color colorEnd = inverseColor(colorStart);

            this.setCouleurTess(colorStart, colorEnd);

            // on ajoute un bouncer !
            this.bouncers.add(bouncerStart);
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
