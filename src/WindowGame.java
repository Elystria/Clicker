import org.newdawn.slick.*;

public class WindowGame extends BasicGame {


    /* Attributs */

    private GameContainer container; // le conteneur du windows
    private static final int WINDOWS_HEIGHT = 960;
    private static final int WINDOWS_WIDTH = 720;
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
    public void render(GameContainer container, Graphics g) throws SlickException {
        // affichage du counter
        partie.getCounter().afficher(this, 2f);
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
