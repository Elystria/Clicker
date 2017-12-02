import java.util.ArrayList;
import java.util.List;

public class Inventaire {

	/***************************************************
	ATTRIBUTS
	***************************************************/

	// modèle
    private List<Bot> bots; // Liste des bots qui existent dans le jeu
    private List<Upgrade> upgrades; // Liste des upgrades qui existent dans le jeu
    
    // les informations sur la partie
    private Partie partie;

	/***************************************************
	CONSTRUCTEUR
	***************************************************/

    // C'est ici qu'on va gérer l'équilibrage du jeu !!!!
    public Inventaire(Partie partie){
    	this.partie = partie;
    	
    	// de base on possède tous les produits et upgrades du jeu
    	// mais ils ne sont affichables que si elles sont disponibles
        this.bots = new ArrayList<Bot>();
        this.upgrades = new ArrayList<Upgrade>();
        
        // On rajoute les bots
        for(ProduitBot pb : partie.getShop().getProduitsBots()) {
            bots.add(pb.getBot());
        }

        // On rajoute les upgrades
    }

	/***************************************************
	METHODES
	***************************************************/

	public float getPpsFromBots() {
	    float pps = 0;

	    for(Bot b : bots) {
	        pps += b.getPPSTotal();
        }

        return pps;
    }

	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/
    public List<Bot> getBots() {
        return bots;
    }
    public void setBots(List<Bot> bots) {
        this.bots = bots;
    }
    public List<Upgrade> getUpgrades() {
        return upgrades;
    }
    public void setUpgrades(List<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }
}
