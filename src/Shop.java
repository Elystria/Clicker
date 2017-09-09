import java.util.ArrayList;
import java.util.List;

public class Shop {

    List Produits = new ArrayList<Produit>(); //Liste des produits que contient la boutique;

    /* Initialisation du shop */
    public Shop(){
        //Créer tous les produits que l'on pourra acheter
       Produit item1 = new ProduitBot(new EnsembleBot("item1", 10), 10);

       //Les ajouter à la liste de produits
       Produits.add(item1);

    }


    /* Affichage du shop */
    public void afficher(){
        //afficher les produits disponibles en magasin
    }
}
