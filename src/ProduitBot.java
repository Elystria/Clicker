import org.newdawn.slick.Image;

public class ProduitBot extends Produit {

    private EnsembleBot bot;// EnsembleBot correspondant au produit
    private int prixDeBase; //Prix original du bot




    public ProduitBot(EnsembleBot bot, int prix, Image image){
        super(prix, image);
        this.bot = bot;
        this.prixDeBase = prix;

    }

    public int getPrixDeBase(){
        return prixDeBase;
    }

    public int getPrixActuel(){
        //TODO
        return 0;
    }
    //@Override
    //public void afficher(){}

    @Override
    public void acheter(){}

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
