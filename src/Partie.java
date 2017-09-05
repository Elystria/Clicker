import org.newdawn.slick.SlickException;

/**
 * Classe Partie
 * Contient les informations relatives à la partie en cours
 */
public class Partie {

    /* Attributs */

    private Counter counter;
//    private Shop shop;
//    private Inventaire inventaire;
//    private Events events;

    /* Constructeurs */

    public Partie() throws SlickException {
        this.counter = new Counter();
//        this.shop = new Shop();
//        this.inventaire = new Inventaire();
//        this.events = new Events();
    }

    /* Méthodes */

    /* Getteurs et Setteurs */

    public Counter getCounter() {
        return counter;
    }

//    public Shop getShop() {
//        return shop;
//    }
//
//    public Inventaire getInventaire() {
//        return inventaire;
//    }
//
//    public Events getEvents() {
//        return events;
//    }
}
