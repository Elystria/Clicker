import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    List Produits = new ArrayList<Produit>(); //Liste des produits que contient la boutique;

    /* Initialisation du shop */
    public Shop() throws SlickException {
        //Créer tous les produits que l'on pourra acheter
       Produit item1 = new ProduitBot(new EnsembleBot("item1", 10), 10, new Image("resources/shop/prod_shop_1.png")
       );

       //Les ajouter à la liste de produits
       Produits.add(item1);

    }


    /* Affichage du shop */
    public void afficher(){
        //afficher les produits disponibles en magasin
        // Structure pour plusieurs profuits
        //Afficher un en-tete
        //Afficher les produits
        /*for (Object produit : Produits ) {
            produit.afficher();
            //ajouter une marge ou separation
        }*/


        //Affichage d'un seul objet
        Produit afficher = (Produit) this.Produits.get(0);
        afficher.afficher();
    }

    public  void update(){}
}
