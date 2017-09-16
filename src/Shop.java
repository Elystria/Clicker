import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<ProduitBot> produitsBots; // Liste des produits de Bots que contient la boutique;
    private List<ProduitUpgrade> produitsUpgrades; // Liste des produits d'Upgrades que contient la boutique;

    /* Initialisation du shop */
    public Shop(){
        // Créer tous les produits
        this.produitsBots = initProduitsBots();
        this.produitsUpgrades = initProduitsUpgrades();
    }

    private List<ProduitBot> initProduitsBots() {
        List<ProduitBot> p = new ArrayList<ProduitBot>();

        // Créer tous les produits que l'on pourra acheter
        p.add(new ProduitBot(new EnsembleBot("item1", 10), 10, new Image("resources/shop/prod_shop_1.png")));

        return p;
    }

    private List<ProduitUpgrade> initProduitsUpgrades() {
        List<ProduitUpgrade> p = new ArrayList<ProduitUpgrade>();

        // Créer tous les produits que l'on pourra acheter
        //p.add(new ProduitUpgrade());

        return p;
    }

    /* Affichage du shop */
    public void render(){
        // afficher le background du shop
        // afficher l'entête du shop
        // afficher les produitsUpgrades disponibles en magasin
        // afficher les produitsBots disponibles en magasin
    }

    /* Mise à jour du shop */
    public void update() {
        // mise à jour du background
        // mise à jour des produitsBots
        // mise à jour des produitsUpgrades
    }
}
