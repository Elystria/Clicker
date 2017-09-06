import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

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
        SpriteSheet spriteSheet = new SpriteSheet("resources/sprites/tesseract_spritesheet.png", 160, 160);
        this.animation = new Animation();
        for(int i = 0; i < NB_SPRITE_ANIMATION; i++){
            this.animation.addFrame(spriteSheet.getSprite(i, 0), TIME_SPRITE_DURATION);
        }
        // initialisation de la position d'affichage
        this.xPos = windows.getWindowsWidth() / 2 - animation.getWidth() / 2;
        this.yPos = windows.getWindowsHeight() / 2 - animation.getHeight() / 2;
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
        g.drawAnimation(this.getAnimation(), yPos, xPos);
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
}
