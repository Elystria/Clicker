import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public abstract class Produit {

	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model
    private int prixDeBase;
    private Disponibilite disponibilite;

    // Vue
    private Image illustration;

	/***************************************************
	ATTRIBUTS
	***************************************************/

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

	/***************************************************
	ATTRIBUTS
	***************************************************/

    public abstract void acheter() throws SlickException;

    public boolean estDisponible(){
        return disponibilite.estDisponible();
    }

	/***************************************************
	ATTRIBUTS
	***************************************************/

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
