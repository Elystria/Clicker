import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Produit {
    private int prixActuel;
    private Disponibilite disponibilite;
    private Image illustration;

    /* Constructeurs */
    public Produit(int prixActuel, Image image){
        this.prixActuel = prixActuel;
        this.disponibilite = new DisponibiliteFalse();
        this.illustration = image;
    }

    //Un produit par défaut
    public Produit(){
        this.prixActuel = 0;
        this.disponibilite = new DisponibiliteFalse();
    }

    /* Méthodes */

    public void acheter(){
    }

    public boolean estDisponible(){
        //par défaut, n'est pas disponible en magasin
        return false;
    }

    public void afficher(){
        this.illustration.draw();

    }

    /*Getters et Setters*/

    public void setPrixActuel(int prixActuel) {
        this.prixActuel = prixActuel;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getPrixActuel() {

        return prixActuel;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public Image getIllustration() {
        return illustration;
    }

    public void setIllustration(Image illustration) {
        this.illustration = illustration;
    }
}
