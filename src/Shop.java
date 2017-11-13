import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.nextAfter;


//Ligne inutile !

public class Shop {

	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model
    private List<ProduitBot> produitsBots; // Liste des produits de Bots que contient la boutique;
    private List<ProduitUpgrade> produitsUpgrades; // Liste des produits d'Upgrades que contient la boutique;
    private Partie partie;
    
    // la vue
    private ShopVue vue; // la vue pour afficher le Shop

	/***************************************************
	CONSTRUCTEUR
	***************************************************/

    /* Initialisation du shop */
    public Shop(WindowGame windows, Partie partie) throws SlickException {
        this.partie = partie;
        this.vue = new ShopVue(this, windows);

        // Création des Produits
        this.produitsBots = initProduitsBots();
        this.produitsUpgrades = initProduitsUpgrades();

        // Créer tous les produits
        this.produitsBots = initProduitsBots();
        this.produitsUpgrades = initProduitsUpgrades();
    }
    
    private List<ProduitBot> initProduitsBots() throws SlickException {
        List<ProduitBot> p = new ArrayList<ProduitBot>();

        // Créer tous les produits que l'on pourra acheter
        p.add(new ProduitBot(new EnsembleBot("Pixel", 10), 10, "resources/shop/point_shop.png", partie));
        p.add(new ProduitBot(new EnsembleBot("Ligne", 10), 10, "resources/shop/droite_shop.png", partie));
        p.add(new ProduitBot(new EnsembleBot("Triangle", 10), 10, "resources/shop/triangle_shop.png", partie));
        p.add(new ProduitBot(new EnsembleBot("Carre", 10), 10, "resources/shop/carre_shop.png", partie));

        return p;
    }

    private List<ProduitUpgrade> initProduitsUpgrades() {
        List<ProduitUpgrade> p = new ArrayList<ProduitUpgrade>();

        // Créer tous les produits que l'on pourra acheter
        //p.add(new ProduitUpgrade());

        return p;
    }
    
	/***************************************************
	METHODES
	***************************************************/

    /* Affichage du shop */
    public void render(Graphics g) throws SlickException {
    	// On affiche la vue
    	vue.render(g);
    }

    /* Mise à jour du shop */
    public void update(int delta) {
    	vue.update(delta);
    }

    public void mouseClicked(int x, int y) {
        Random rdn = new Random();
        // Si on est dans l'entête
        if(x > vue.getEnteteFond().getX() && x < vue.getEnteteFond().getX() + vue.getEnteteFond().getWidth()
                && y > vue.getEnteteFond().getY() && y < vue.getEnteteFond().getY() + vue.getEnteteFond().getHeight()) {
            // on change de catchPhrase !
            int old = vue.getCurrentPhrase();
            while(vue.getCurrentPhrase() == old) {
            	vue.setCurrentPhrase(rdn.nextInt(vue.getCatchPhrases().size()));
            }
        }
        //Si on est dans la partie principale du Shop
        if ( x > vue.getEnteteFond().getX() && x < vue.getEnteteFond().getX() + vue.getEnteteFond().getWidth()
            && y > vue.getEnteteFond().getY() + vue.getEnteteFond().getHeight() ) {
            //Detection du produit sur lequel on a cliqué
            int nbProduits = this.produitsBots.size();
            int hauteur = produitsBots.get(0).getIllustration().getHeight();
            int indiceProduitClique = (int) (y - vue.getEnteteFond().getHeight())/hauteur;
            
            // Acheter si le produit existe
            if (indiceProduitClique < nbProduits){
                produitsBots.get(indiceProduitClique).acheter();
            }

        }
    }
    
	/***************************************************
	GETTEURS & SETTEURS
	***************************************************/

	public List<ProduitBot> getProduitsBots() {
		return produitsBots;
	}

	public void setProduitsBots(List<ProduitBot> produitsBots) {
		this.produitsBots = produitsBots;
	}

	public List<ProduitUpgrade> getProduitsUpgrades() {
		return produitsUpgrades;
	}

	public void setProduitsUpgrades(List<ProduitUpgrade> produitsUpgrades) {
		this.produitsUpgrades = produitsUpgrades;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public ShopVue getVue() {
		return vue;
	}

	public void setVue(ShopVue vue) {
		this.vue = vue;
	}
    
    
}














