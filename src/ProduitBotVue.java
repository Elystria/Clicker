import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
	
	/***************************************************
	CONSTRUCTEUR
	***************************************************/
	
	public ProduitBotVue(ProduitBot produit) {
		this.produit = produit;
		String nom = produit.getBot().getNom();
		int prix = produit.getPrixActuel();
		String police = "resources/fonts/LLPIXEL3.ttf";
		this.textNom = new Text(nom, police, 20, Color.black);
		this.textPrix = new Text(prix + "pixels", police, 17, new Color(255, 50, 50, 255));
	}
	
	/***************************************************
	METHODES
	***************************************************/
	
	// le rectangle est la zone d'affichage
	public void render(Graphics g, Rectangle rect) {
		// afficher l'image
        float scale = (float) rect.getHeight() / produit.getIllustration().getHeight();
        produit.getIllustration().draw(rect.getX(), rect.getY()	, produit.getIllustration().getWidth() * scale, produit.getIllustration().getHeight() * scale);

		// afficher le nom et le prix du produit
        float offset = produit.getIllustration().getWidth() * scale ;
        textNom.setPos((int)rect.getX() + (int)offset, (int)rect.getY());
        textPrix.setPos((int)rect.getX() + (int)offset,  (int)rect.getY() + (int)rect.getHeight() / 2);
        textNom.draw(g);
        textPrix.draw(g);
    }	
	
	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/
}
