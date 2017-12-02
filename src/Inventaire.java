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
        int prixInitial;
        float rapport;
        float coefficiant;
        Bot b;

            // Point
            prixInitial = 10;
            rapport = 0.1f;
            coefficiant = 1.1f;
            b = new Bot("Point", "resources/shop/point_shop.png", prixInitial, rapport, coefficiant);
            b.setDisponibilite(new DisponibiliteTrue());
            bots.add(b);

            // Ligne
            prixInitial = Bot.prixInitialNextBot(bots.get(0), 1);
            rapport = Bot.rapportDEfficaciteNextBot(bots.get(0), 1);
            coefficiant = Bot.coefMultipliciteNextBot(bots.get(0), 1);
            b = new Bot("Ligne", "resources/shop/droite_shop.png", prixInitial, rapport, coefficiant);
            b.setDisponibilite(new DisponibiliteSeuilDePixels(100, partie.getCounter()));
            bots.add(b);


        // On rajoute les upgrades
    }

	/***************************************************
	METHODES
	***************************************************/

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
