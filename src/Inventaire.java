import java.util.ArrayList;
import java.util.List;

public class Inventaire {

	/***************************************************
	ATTRIBUTS
	***************************************************/

	// modèle
    private List<EnsembleBot> ensembleBots; // Liste de bots que l'on possède
    private List<Upgrade> upgrades; // Liste des upgrades que l'on possède
    
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
        this.ensembleBots = new ArrayList<EnsembleBot>();
        this.upgrades = new ArrayList<Upgrade>();
        
        // On rajoute les produits
        // On rajoute les upgrades
    }

	/***************************************************
	METHODES
	***************************************************/

	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/

}
