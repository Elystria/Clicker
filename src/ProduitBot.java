import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class ProduitBot extends Produit {

	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model
    private EnsembleBot bot;// EnsembleBot correspondant au produit
    private int prixDeBase; //Prix original du bot
    private Partie partie; //partie dans laquelle on achète le produit
    
    // Vue
    private ProduitBotVue vue; // la vue

	/***************************************************
	CONSTRUCTEUR
	***************************************************/

    public ProduitBot(EnsembleBot bot, int prix, String image, Partie partie) throws SlickException {
        super(prix, image);
        this.bot = bot;
        this.prixDeBase = prix;
        this.partie = partie;
        this.vue = new ProduitBotVue(this);
    }

	/***************************************************
	METHODES
	***************************************************/

    @Override
    public void acheter() {
        //Peut-on acheter le produit ?
        Counter counter = partie.getCounter();

        if(this.getPrixActuel() <= counter.getNbPixels()){
            this.bot.setNbPossedes(this.bot.getNbPossedes() + 1);
            System.out.println("acheté");
            //Mettre le nombre de pixels possédés à jour
            counter.setNbPixels(counter.getNbPixels() - this.getPrixActuel());
            //Mettre à jour l'inventaire
            try {
                partie.getInventaire().getProduitsPossedes().add(new ProduitBot(this.bot, this.getPrixActuel(), this.getIllustration().getResourceReference(), partie));
            } catch (Exception e){
                e.printStackTrace();

            }
        }
    }
    
    public void render(Graphics g, Rectangle rect) {
    	vue.render(g, rect);
    }

	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/

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
