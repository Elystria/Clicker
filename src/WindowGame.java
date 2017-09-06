import org.newdawn.slick.*;

public class WindowGame extends BasicGame {


    /* Attributs */

    private GameContainer container; // le conteneur du windows
    private static final int WINDOWS_HEIGHT = 720;
    private static final int WINDOWS_WIDTH = 960;
    private Partie partie; // la partie en elle-même

    /* Constructeurs */

    public WindowGame() {
        super("Lesson 1 :: WindowGame");
    }

    /* Méthodes */

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;

        // on initialise la partie
        partie = new Partie(this);
    }

    @Override
    public void keyReleased(int key, char c){
        if (Input.KEY_ESCAPE == key){
            container.exit();
        }
   }

   @Override
   public void mouseClicked(int button, int x, int y, int clickCount){
       int xPos = this.partie.getCounter().getxPos();
       int yPos = this.partie.getCounter().getyPos();
       if (x>=xPos && x<=xPos+160 && y>=yPos && y<=yPos+160){
            //Changer la couleur du tesseract
            if(this.partie.getCounter().getFondFill().getStartColor().equals(Color.cyan)) {
                this.partie.getCounter().setCouleurTess(Color.blue, Color.green);
            }
            else{
                this.partie.getCounter().setCouleurTess(Color.cyan, Color.white);
            }
        }
   }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        // affichage du counter
        partie.getCounter().afficher(g);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }

    /* Getteurs et Setteurs */

    public static int getWindowsHeight() {
        return WINDOWS_HEIGHT;
    }

    public static int getWindowsWidth() {
        return WINDOWS_WIDTH;
    }
}
