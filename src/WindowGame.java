import org.newdawn.slick.*;

public class WindowGame extends BasicGame {


    /* Attributs */

    private GameContainer container; // le conteneur du windows
    private static final int WINDOWS_WIDTH = 960; // la largeur de la fenêtre
    private static final int WINDOWS_HEIGHT = 720; // la hauteur de la fenêtre
    private Partie partie; // la partie en elle-même

    /* Constructeurs */

    public WindowGame() {
        super("Lesson 1 :: WindowGame");
    }

    /* Méthodes */

    @Override
    public void init(GameContainer gc) throws SlickException {
        this.container = gc;

        // on initialise la partie
        partie = new Partie();
    }

    @Override
    public void keyReleased(int key, char c){
        if (Input.KEY_ESCAPE == key){
            container.exit();
        }
   }

   @Override
   public void mouseClicked(int button, int x, int y, int clickCount){
        //Clic sur le counter
       try {
           this.partie.getCounter().mouseClicked(x, y);
       } catch (SlickException e) {
           e.printStackTrace();
       }
       this.partie.getShop().mouseClicked(x, y);
   }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        // affichage du counter
        partie.getCounter().render(g, this, gc, partie.getCounter().getVue().getTailleActuelle());
        partie.getShop().render(g);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
       partie.getCounter().update(delta);
       partie.getShop().update(delta);
    }

    /* Getteurs et Setteurs */

    public static int getWindowsHeight() {
        return WINDOWS_HEIGHT;
    }

    public static int getWindowsWidth() {
        return WINDOWS_WIDTH;
    }
}
