import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        /*
        for(ProduitBot pb : produitsBots) {
            System.out.println("Prix du " + pb.getBot().getNom() + " = " + pb.getBot().getPrixNextBot());
        }
        for(ProduitBot pb : produitsBots) {
            System.out.println("Rapport du " + pb.getBot().getNom() + " = " + pb.getBot().getRapportDEfficacite());
        }
        for(ProduitBot pb : produitsBots) {
            System.out.println("Coef du " + pb.getBot().getNom() + " = " + pb.getBot().getCoefMultiplicite());
        }
        */
        this.produitsUpgrades = initProduitsUpgrades();

        // Créer tous les produits
        this.produitsBots = initProduitsBots();
        this.produitsUpgrades = initProduitsUpgrades();
    }
    
    private List<ProduitBot> initProduitsBots() throws SlickException {
        List<ProduitBot> p = new ArrayList<ProduitBot>();

        // On rajoute les bots
        Integer prixInitial;
        float rapport;
        Bot b;
        ProduitBot pb;

        // Point
        prixInitial = 10;
        rapport = 0.01f;
        b = new Bot("Point", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteTrue(), "resources/shop/point_shop.png", partie);
        p.add(pb);


        // Ligne
        prixInitial = 100;
        rapport = 0.007f;
        b = new Bot("Ligne", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(10, partie.getCounter()),
                "resources/shop/droite_shop.png", partie);
        p.add(pb);

        // Triangle
        prixInitial = 1_333;
        rapport = 0.005f;
        b = new Bot("Triangle", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 15, partie.getCounter()),
                "resources/shop/triangle_shop.png", partie);
        p.add(pb);

        // Carre
        prixInitial = 10_000;
        rapport = 0.0025f;
        b = new Bot("Carre", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 10, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);

        // Pentagone
        prixInitial = 55_555;
        rapport = 0.001f;
        b = new Bot("Pentagone", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 10, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);

        // Hexagone
        prixInitial = 1_000_000;
        rapport = 0.0006f;
        b = new Bot("Hexagone", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 25, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);

        // Heptagone
        prixInitial = 7_000_000;
        rapport = 0.0003f;
        b = new Bot("Heptagone", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 10, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);

        // Octogone
        prixInitial = 42_424_242;
        rapport = 0.00015f;
        b = new Bot("Octogone", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 10, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);

        // Énnéagone
        prixInitial = 999_999_999;
        rapport = 0.00008f;
        b = new Bot("Énnéagone", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 30, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);

        // Décagone !
        prixInitial = 2_147_483_647; // /!\ À mettre à 10_000_000_000 !
        rapport = 0.000005f;
        b = new Bot("Décagone !", prixInitial, rapport);
        pb = new ProduitBot(b, new DisponibiliteSeuilDePixels(b.getPrixNextBot() / 2, partie.getCounter()),
                "resources/shop/carre_shop.png", partie);
        p.add(pb);
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
        if (x > vue.getEnteteFond().getX() && x < vue.getEnteteFond().getX() + vue.getEnteteFond().getWidth()
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














