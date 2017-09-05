/**
 * Classe Partie
 * Contient les informations relatives à la partie en cours
 */
public class Partie {

    /* Attributs */
    //Nombre de pixels possédés
    private int nbPixels;
    //Nombre de pixels gagnés chaque seconde
    private int pps;
    //Nombre de pixels gagnés par clic
    private int ppc;

    //TODO: méthode jouer ?


    /* Setters */
    public void setNbPixels(int nbPixels) {
        this.nbPixels = nbPixels;
    }

    public void setPps(int pps) {
        this.pps = pps;
    }

    public void setPpc(int ppc) {
        this.ppc = ppc;
    }

    /* Getters */
    public int getNbPixels() {
        return nbPixels;
    }

    public int getPps() {
        return pps;
    }

    public int getPpc() {
        return ppc;
    }
}
