public class ProduitBot extends Produit {

    public EnsembleBot bot;// EnsembleBot correspondant au produit
    public int prixDeBase; //Prix original du bot

    public ProduitBot(EnsembleBot bot, int prix){
        super(prix);
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
    @Override
    public void afficher(){}

    @Override
    public void acheter(){}
}
