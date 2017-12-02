import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public abstract class Produit {

	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model
    private Disponibilite disponibilite;

    // Vue
    private Image illustration;

	/***************************************************
	ATTRIBUTS
	***************************************************/

    public Produit(Disponibilite disponibilite, String image) throws SlickException {
        this.disponibilite = disponibilite;
        this.illustration = new Image(image);
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

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
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
