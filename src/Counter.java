
import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.Random;
import java.util.*;

public class Counter {


    /* Attributs */

    // model
    private int nbPixels; //Nombre de pixels possédés
    private int pps; //Nombre de pixels gagnés chaque seconde
    private int ppc; //Nombre de pixels gagnés par clic

    // vue
    private Animation animation; // l'animation du tesserac
    private final static int NB_SPRITE_ANIMATION = 15; // son nombre d'images
    private final static int TIME_SPRITE_DURATION = 100; // la durée de la transition entre chaque images
    private int xPos; // la position de l'animation
    private int yPos;
    private float taille; // sa taille relative : 1 = taille inchangée
    private float tailleVariation; // sa variation de taille maximum
    private float tailleMin, tailleMax; // les variations extremums de taille
    private boolean tailleCroissante; // indique si la taille est en train de grandir
    private List<Float> bouncers; // les petites augmentations de tailles quand un joueur clique sur le counter
    private float bouncerStart; // l'amplitude des bouncers
    private float bouncerDecrease; // la vitesse de disparition des bouncers
    private float tailleActuelle; // la véritable taille finale du counter !
    private Rectangle fond; // Le rectangle de fond permettant de gérer la couleur du tesseract
    private GradientFill fondFill; // Pour remplissage du rectangle
    private boolean sensContraste; //indique quelle couleur s'éclaircit et laquelle s'assombrit dans le tesseract

    //Pour le gradient
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    /* Constructeurs */

    public Counter() throws SlickException {
        this(0, 0, 1);
    }


    public Counter(int nbPixelsDepart, int pps, int ppc) throws SlickException {
        // initialisation du model
        this.nbPixels = nbPixelsDepart;
        this.pps = pps ;
        this.ppc = ppc ;

        // initialisation de l'affichage
       SpriteSheet spriteSheet = new SpriteSheet("resources/sprites/tesseract_spritesheet_trans.png", 160, 160);
        this.animation = new Animation();
        for(int i = 0; i < NB_SPRITE_ANIMATION; i++){
            this.animation.addFrame(spriteSheet.getSprite(i, 0), TIME_SPRITE_DURATION);
        }
        this.taille = 2f;
        this.tailleVariation = 0.001f;
        this.tailleMax = 2.1f;
        this.tailleMin = 1.9f;
        this.tailleCroissante = true;
        this.bouncers = new ArrayList<Float>();
        this.bouncerStart = 0.5f;
        this.bouncerDecrease = 0.003f;
        this.tailleActuelle = this.taille;

        // initialisation de la position d'affichage
        this.xPos = 0;
        this.yPos = 0;

        this.xStart = xPos;
        this.yStart = yPos;
        this.xEnd = (int) (xPos + (animation.getWidth() * tailleActuelle));
        this.yEnd = (int) (yPos + (animation.getHeight() * tailleActuelle));


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
        this.sensContraste = true;
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
    public void afficher(Graphics g, WindowGame windows, float scale) {
        // on récupère la position d'affichage
        this.xPos = windows.getWindowsWidth() / 2 - ((int) ((animation.getWidth() * scale) / 2));
        this.yPos = windows.getWindowsHeight() / 2 - ((int) ((animation.getHeight() * scale) / 2));
        //Premier affichage
        if(this.xStart == 0){
            this.xStart = this.getxPos();
            this.yStart = this.getyPos();
            this.xEnd = this.xStart + (int) (animation.getWidth() * scale)/2;
            this.yEnd = this.yStart + (int) (animation.getHeight() * scale)/2;
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

        if (getXStart() >= xCentre){
            //Si Ystart est sup a Ycentre
            this.yStart--;
            this.yEnd++;
            if (getYStart() >= yCentre) {
                //Diminuer X et Augmenter Y
                //System.out.println("En bas à droite");
                this.xStart++;
                this.xEnd--;

            } else {
                //System.out.println("En haut à droite");
                //Augmenter tout
                this.xStart--;
                this.xEnd++;
            }
        }else {
            this.yStart++;
            this.yEnd--;
            //Sinon
            //Si Ystart est sup à Ycentre
            if (getYStart() < yCentre) {
                //System.out.println("En haut à gauche ");
                //Diminuer tout
                this.xStart--;
                this.xEnd++;
                //System.out.println("XStart: " + getXStart());
                //System.out.println("YStart: " + getYStart());
                // Sinon
            } else {
                //System.out.println("En bas à gauche ");
                this.xStart++;
                this.xEnd--;

                //Augmenter X, Diminuer Y
            }
        }
        //Debut et fin du gradient
        this.fondFill.setStart(this.xStart, this.yStart);
        this.fondFill.setEnd(this.xEnd,
                             this.yEnd);

       g.fill(this.getFond(), this.getFondFill());

        // on affiche l'animation
        this.animation.draw(xPos, yPos, animation.getWidth() * scale, animation.getHeight()*scale);
    }

    /* S'occupe de mettre à jour le counter */
    public void update() {
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

       tailleActuelle = taille;
       for(int i = 0; i < bouncers.size();i++) {
           tailleActuelle += bouncers.get(i);
           bouncers.set(i, bouncers.get(i) - bouncerDecrease);
           if(bouncers.get(i) <= 0) {
               bouncers.remove(i);
               i = i - 1;
           }
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
