import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ProduitBot extends Produit {

    /* Attributs */

    // Model
    private EnsembleBot bot;// EnsembleBot correspondant au produit
    private int prixDeBase; //Prix original du bot

    /* Constructeurs */

    public ProduitBot(EnsembleBot bot, int prix, String image) throws SlickException {
        super(prix, image);
        this.bot = bot;
        this.prixDeBase = prix;
    }

    /* Méthodes */

    //@Override
    //public void afficher(){}

    @Override
    public void acheter(){}


    /* Getteurs et Setteurs */

    public int getPrixDeBase(){
        return prixDeBase;
    }

    public int getPrixActuel(){
        //TODO
        return 0;
    }


    public EnsembleBot getBot() {
        return bot;
    }

    public void setBot(EnsembleBot bot) {
        this.bot = bot;
    }

    public void setPrixDeBase(int prixDeBase) {
        this.prixDeBase = prixDeBase;
    }


}
