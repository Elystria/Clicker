import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;

public class CounterVue {
	
	/***************************************************
	ATTRIBUTS
	***************************************************/
	
	private Counter counter; // le counter duquel on affiche la vue
	
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

    // Pour le gradient
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int coefRotation;//indique la vitesse de rotation
    private int zoneGrad;
    
    // Pour le texte
    private Text textNbPixel; // affiche le nombre de pixels
    private Text textPps; // affiche le pps

	/***************************************************
	CONSTRUCTEUR
	***************************************************/
    
    public CounterVue (Counter counter, float size) throws SlickException {
    	// on link avec le modèle
    	this.counter = counter;
    	
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
        
        // Initialisation du texte
        this.textNbPixel = new Text("NbPixels", "resources/fonts/LLPIXEL3.ttf", 30, Color.white);
        this.textPps = new Text("PPS", "resources/fonts/LLPIXEL3.ttf", 17, Color.red);
    }
    
	/***************************************************
	METHODES
	***************************************************/
    
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
   }

    private void getPositionAffichage(WindowGame windows, float scale) {
        this.xPos = windows.getWindowsWidth() / 2 - ((int) ((animation.getWidth() * scale) / 2));
        this.yPos = windows.getWindowsHeight() / 2 - ((int) ((animation.getHeight() * scale) / 2));
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
        // Le nombre de pixels
        int xString = windows.getWindowsWidth() / 2;
        int yString = windows.getWindowsHeight() / 2 - (int) (animation.getHeight() * size) / 2 - textNbPixel.getHeightText() * 2;
        String s = "Pixels : " + Math.round(counter.getNbPixels());
        textNbPixel.setTexte(s);
        textNbPixel.centerArround(xString, yString);
        textNbPixel.draw(g);

        s = "PPS : " + String.format("%.3g%n", counter.getPps()) + "   PPC : " + String.format("%.3g%n", counter.getPpc());
        yString += textPps.getHeightText();
        textPps.setTexte(s);
        textPps.centerArround(xString, yString);
        textPps.draw(g);
    }

    /* S'occupe de mettre à jour le counter */
    public void update(int delta) {    	
        // mise à jour de la taille initiale du counter
        this.updateTaille(delta);

        // Mise à jour des bouncers
        this.updateBouncers(delta);
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

    /* Réagit lors du clic d'un utilisateur
     * x et y : int coordonnées du clic  */
    public void mouseClicked(int x, int y) {    	        	
        //Changer la couleur du tesseract de manière aleatoire;
        Random rdn = new Random();
        Color colorStart = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));
        Color colorEnd = inverseColor(colorStart);

        this.setCouleurTess(colorStart, colorEnd);

        // ajouter un bouncer
        this.bouncers.add(bouncerStart);
    }

    /* Renvoie l'opposé de la couelur actuelle */
    public Color inverseColor(Color color){
        int r = color.getRed();
        int v = color.getGreen();
        int b = color.getBlue();

        //Inverser les composants RVB pour donner la couleur opposée
        return (new Color(255-r, 255-v, 255-b));
    }
    
	/***************************************************
	GETTEURS & SETTEURS
	***************************************************/
    
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
    
    /* Change la couleur du tesseract */
    public void setCouleurTess(Color coulDebut, Color coulFin){
        this.getFondFill().setStartColor(coulDebut);
        this.getFondFill().setEndColor(coulFin);
    }
}
















