public class EnsembleBot {
    public String nom; //Nom du bot
    public int gainPPS; //Gain d'un bot

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

    public int nbPossedes; //Nombre de bot possedes
}
