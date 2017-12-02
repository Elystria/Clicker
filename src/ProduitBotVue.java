import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class ProduitBotVue {

	/***************************************************
	ATTRIBUTS
	***************************************************/
	
	// le modèle
	private ProduitBot produit; // le modèle
	
	// les textes
	private Text textNom; // pour afficher le nom du produit
	private Text textPrix; // pour afficher son prix
	private Text textQuantite; // pour afficher combien on en posède
	
	/***************************************************
	CONSTRUCTEUR
	***************************************************/
	
	public ProduitBotVue(ProduitBot produit) {
		this.produit = produit;
		String nom = produit.getBot().getNom();
		int prix = produit.getBot().getPrixNextBot();
		int quantite = produit.getBot().getNbPossede();
		String police = "resources/fonts/LLPIXEL3.ttf";
		this.textNom = new Text(nom, police, 20, Color.black);
		this.textPrix = new Text(prix + " pixels", police, 17, new Color(255, 50, 50, 255));
		this.textQuantite = new Text(quantite + "", police, 30, new Color(200, 50, 200, 255));
	}
	
	/***************************************************
	METHODES
	***************************************************/
	
	// le rectangle est la zone d'affichage
	public void render(Graphics g, Rectangle rect) {
		// afficher l'image
        Image img = produit.getIllustration();
        float scale = (float) rect.getHeight() / img.getHeight();
        img.draw(rect.getX(), rect.getY(), img.getWidth() * scale, img.getHeight() * scale);

		// afficher le nom et le prix du produit
        float offset = img.getWidth() * scale ;
        textNom.setPos((int)rect.getX() + (int)offset, (int)rect.getY());
		textNom.draw(g);
        int prix = produit.getBot().getPrixNextBot();
        textPrix.setTexte(prix + " pixels");
        textPrix.setPos((int)rect.getX() + (int)offset,  (int)rect.getY() + (int)rect.getHeight() / 2);
        textPrix.draw(g);
        int quantite = produit.getBot().getNbPossede();
        textQuantite.setTexte(quantite + "");
        textQuantite.setPos((int) rect.getX() + (int) rect.getWidth() - textQuantite.getWidthText() - 20,
							(int) rect.getY() + (int) rect.getHeight() / 2 - textQuantite.getHeightText() / 2);
        textQuantite.draw(g);
    }	
	
	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/
}
