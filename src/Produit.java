import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public abstract class Produit {

    /* Attributs */

    // Model
    private int prixDeBase;
    private Disponibilite disponibilite;

    // Vue
    private Image illustration;

    /* Constructeurs */
    public Produit(int prixDeBase, String image) throws SlickException {
        this.prixDeBase = prixDeBase;
        this.disponibilite = new DisponibiliteFalse();
        this.illustration = new Image(image);
    }

    //Un produit par défaut
    public Produit(){
        this.prixDeBase = 0;
        this.disponibilite = new DisponibiliteFalse();
        this.illustration = null;
    }

    /* Méthodes */

    public abstract void acheter();

    public boolean estDisponible(){
        return disponibilite.estDisponible();
    }

    public void afficher(Rectangle rect){
        // afficher l'illustration au bon endroit
        // TODO
        this.illustration.draw();
    }

    /*Getters et Setters*/

    public void setPrixActuel(int prixDeBase) {
        this.prixDeBase = prixDeBase;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getPrixActuel() {

        return prixDeBase;
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
