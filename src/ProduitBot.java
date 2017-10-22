import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ProduitBot extends Produit {

    /* Attributs */

    // Model
    private EnsembleBot bot;// EnsembleBot correspondant au produit
    private int prixDeBase; //Prix original du bot
    private Partie partie; //partie dans laquelle on achète le produit
    /* Constructeurs */

    public ProduitBot(EnsembleBot bot, int prix, String image, Partie partie) throws SlickException {
        super(prix, image);
        this.bot = bot;
        this.prixDeBase = prix;
        this.partie = partie;

    }

    /* Méthodes */

    //@Override
    //public void afficher(){}

    @Override
    public void acheter() {
        //Peut-on acheter le produit ?
        Counter counter = partie.getCounter();
        CounterText counterText = counter.getCounterText();

        if(this.getPrixActuel() <= counterText.getNbPixels()){
            this.bot.setNbPossedes(this.bot.getNbPossedes() + 1);
            System.out.println("acheté");
            //Mettre le nombre de pixels possédés à jour
            counterText.setNbPixels(counterText.getNbPixels() - this.getPrixActuel());
            //Mettre à jour l'inventaire
            try {
                partie.getInventaire().getProduitsPossedes().add(new ProduitBot(this.bot, this.getPrixActuel(), this.getIllustration().getResourceReference(), partie));
            } catch (Exception e){
                e.printStackTrace();

            }

        }
    }


    /* Getteurs et Setteurs */

    public int getPrixDeBase(){
        return prixDeBase;
    }

    public int getPrixActuel(){

        return this.prixDeBase;
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
