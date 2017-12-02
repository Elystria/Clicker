import org.newdawn.slick.SlickException;

/**
 * Classe Partie
 * Contient les informations relatives à la partie en cours
 */
public class Partie {

    /***************************************************
    METHODES
    ***************************************************/

    private Counter counter;
    private WindowGame windows;
    private Shop shop;
    private Inventaire inventaire;
//    private Inventaire inventaire;
//    private Events events;

    /***************************************************
    CONSTRUCTEUR
    ***************************************************/

    public Partie() throws SlickException {
        this.windows = windows;
        this.counter = new Counter();
        this.inventaire = new Inventaire(this);
        this.shop = new Shop(windows, this);
    }

    /***************************************************
    METHODES
    ***************************************************/

    /***************************************************
    GETS && SETS
    ***************************************************/

    public Counter getCounter() {
        return counter;
    }

    public Shop getShop(Partie this, Partie partie) {
        return shop;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }
//
//    public Events getEvents() {
//        return events;
//    }
}
