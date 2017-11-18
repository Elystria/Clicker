import org.newdawn.slick.Image;

public class EnsembleBot {

	/***************************************************
	ATTRIBUTS
	***************************************************/
	
	// Modèle de Marie
    //private String nom; //Nom du bot
    //private int gainPPS; //Gain de base d'un bot
    //private int nbPossedes; // Nombre d'instance du même bot possédé

	/***************************************************
	CONSTRUCTEUR
	***************************************************/

    public EnsembleBot(String nom, int gain){
        this.nom = nom;
        this.gainPPS = gain;
        this.nbPossedes = 0;
    }
    
	/***************************************************
	METHODES
	***************************************************/

    public void afficher(){}

	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getGainPPS() {
        return gainPPS;
    }

    public void setGainPPS(int gainPPS) {
        this.gainPPS = gainPPS;
    }

    public int getNbPossedes() {
        return nbPossedes;
    }

    public void setNbPossedes(int nbPossedes) {
        this.nbPossedes = nbPossedes;
    }

}
