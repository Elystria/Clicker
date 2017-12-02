import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class ProduitBot extends Produit {

	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model
    private Bot bot;// EnsembleBot correspondant au produit
    private Partie partie; //partie dans laquelle on achète le produit
    
    // Vue
    private ProduitBotVue vue; // la vue

	/***************************************************
	CONSTRUCTEUR
	***************************************************/

    public ProduitBot(Bot bot, Partie partie) throws SlickException {
        this.bot = bot;
        this.partie = partie;
        this.vue = new ProduitBotVue(this);
    }
    
	/***************************************************
	METHODES
	***************************************************/

    @Override
    public void acheter() {
        // Peut-on acheter le produit ?
        Counter counter = partie.getCounter();

        if(bot.getPrixNextBot() <= counter.getNbPixels()){
            // Payer
            counter.setNbPixels(counter.getNbPixels() - bot.getPrixNextBot());

            // Puis obtenir l'objet
            bot.setNbPossede(bot.getNbPossede() + 1);
            System.out.println("acheté");
            

            // Mettre à jour l'inventaire
            try {
            	// TODO !
                //partie.getInventaire().getProduitsPossedes().add(new ProduitBot(this.bot, this.getPrixActuel(), this.getIllustration().getResourceReference(), partie));
            } catch (Exception e){
                e.printStackTrace();

            }
        }
    }
    
    public void render(Graphics g, Rectangle rect) {
    	vue.render(g, rect);
    }

    public boolean estDisponible() {
        return bot.getDisponibilite().estDisponible();
    }

    /***************************************************
	GETTEURS && SETTEURS
	***************************************************/
    public Bot getBot() {
        return bot;
    }
    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
