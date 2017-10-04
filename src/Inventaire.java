import java.util.ArrayList;

public class Inventaire {

    public ArrayList<ProduitBot> produitsPossedes; //Liste de bots que l'on possède
    public ArrayList<ProduitUpgrade> upgradesdebloquees; //Liste des upgrades que l'on possède

    public Inventaire(){
        this.produitsPossedes = new ArrayList<ProduitBot>();
        this.upgradesdebloquees = new ArrayList<ProduitUpgrade>();
    }

    public void afficher(){

    }

}
