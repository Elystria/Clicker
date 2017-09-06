//TODO : mettre des couleurs aleatoires pour le gradient

import org.newdawn.slick.*;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import java.util.Random;

public class Counter {


    /* Attributs */

    private int nbPixels; //Nombre de pixels possédés
    private int pps; //Nombre de pixels gagnés chaque seconde
    private int ppc; //Nombre de pixels gagnés par clic
    private Animation animation;
    private final static int NB_SPRITE_ANIMATION = 15;
    private final static int TIME_SPRITE_DURATION = 100;
    private int xPos;
    private int yPos;
    private Rectangle fond; //Le rectangle permettant de gérer la couleur du tesseract
    private GradientFill fondFill; //Pour remplissage du rectangle

    /* Constructeurs */

    public Counter(WindowGame windows) throws SlickException {
        this(0, 0, 1, windows);
    }


    public Counter(int nbPixelsDepart, int pps, int ppc, WindowGame windows) throws SlickException {
        // initialisation du model
        this.nbPixels = nbPixelsDepart;
        this.pps = pps ;
        this.ppc = ppc ;

        // initialisation de l'affichage
        //Obtenir lles dimensions du spritesheet
        Image spriteSheetPNG = new Image("resources/sprites/tesseract_spritesheet_trans.png");
        int hauteurSheet = spriteSheetPNG.getHeight();
        int largeurSheet = spriteSheetPNG.getWidth();
        SpriteSheet spriteSheet = new SpriteSheet(spriteSheetPNG, hauteurSheet, largeurSheet/15);
        this.animation = new Animation();
        for(int i = 0; i < NB_SPRITE_ANIMATION; i++){
            this.animation.addFrame(spriteSheet.getSprite(i, 0), TIME_SPRITE_DURATION);
        }
        // initialisation de la position d'affichage
        this.xPos = windows.getWindowsWidth() / 2 - animation.getWidth() / 2;
        this.yPos = windows.getWindowsHeight() / 2 - animation.getHeight() / 2;

        //Creation reclangle et fond (attention y et x inverses)
        this.fond = new Rectangle(this.xPos, this.yPos, animation.getWidth(), animation.getHeight());
        this.fondFill = new GradientFill(this.xPos, this.yPos, Color.cyan, this.xPos+animation.getWidth(), this.yPos+animation.getHeight(), Color.red);
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
    public void afficher(Graphics g) {
        g.fill(this.getFond(), this.getFondFill());
        g.drawAnimation(this.getAnimation(), xPos, yPos);
    }

    /* Change la couleur du tesseract */
    public void setCouleurTess(Color coulDebut, Color coulFin){
        this.getFondFill().setStartColor(coulDebut);
        this.getFondFill().setEndColor(coulFin);
    }

    /* Réagit lors du clic d'un utilisateur
     * x et y : int coordonnées du clic  */

    public void mouseClic(int x, int y){
        int xPos = this.getxPos();
        int yPos = this.getyPos();
        //Vérifier la localisation du clic
        if (x>=xPos && x<=xPos+getAnimation().getHeight() && y>=yPos && y<=yPos+getAnimation().getWidth()){
            //Changer la couleur du tesseract de manière aleatoire;
            Random rdn = new Random();
            Color colorStart = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));
            Color colorEnd = new Color(rdn.nextInt(256), rdn.nextInt(256), rdn.nextInt(256));

            this.setCouleurTess(colorStart, colorEnd);
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
}
