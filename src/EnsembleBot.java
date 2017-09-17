public class EnsembleBot {

    /* Attributs */

    // Model
    private String nom; //Nom du bot
    private int gainPPS; //Gain de base d'un bot
    private int nbPossedes; // Nombre d'instance du même bot possédé

    public EnsembleBot(String nom, int gain){
        this.nom = nom;
        this.gainPPS = gain;
        this.nbPossedes = 0;
    }

    public EnsembleBot(){
        this("default", 0);
    }

    public void afficher(){}

    /*Getters et Setters*/

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
