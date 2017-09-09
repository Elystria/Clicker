public class EnsembleBot {
    private String nom; //Nom du bot
    private int gainPPS; //Gain d'un bot
    private int nbPossedes;

    public EnsembleBot(){
        this("default", 0);
    }

    public EnsembleBot(String nom, int gain){
        this.nom = nom;
        this.gainPPS = gain;
        this.nbPossedes = 0;
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
