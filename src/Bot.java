import org.newdawn.slick.Image;

public class Bot {
	
	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model ludique
	private String nom; // le nom du bot
	private Image image; // l'image représentative du bot
	
	// Model mathématique
	// À partir de ce modèle, on peut calculer les valeurs qui nous intéressent vraiment
	private int prixInitial; // le prix initial du premier bot
	private float rapportDEfficacite; // le rapport (prix / PPS) d'éfficacité d'un bot
	private float coefMultiplicite; // le coefficiant de multiplicité, un bot coûte plus cher si on en a déjà du même type !
	private int nbPossede; // le nombre d'instances de ce bot déjà possédées
	
	// Valeurs numériques
	// Il n'est pas pertinent de garder ces variables en attributs, il vaudra mieux utiliser des getteurs !
	//private float PPS; // le gain par seconde d'un seul bot
	//private int prixNextBot; // le prix à payer pour acheter le prochain bot
	
	/***************************************************
	CONSTRUCTEUR
	***************************************************/

	public Bot(String nom, Image image, int prixInitial, float rapportDEfficacite, float coefMultiplicite) {
		this.nom = nom;
		this.image = image;
		this.prixInitial = prixInitial;
		this.rapportDEfficacite = rapportDEfficacite;
		this.coefMultiplicite = coefMultiplicite;
	}
	
	/***************************************************
	METHODES
	***************************************************/

	// On va générer les getteurs particuliers
	//- p(i+1) = 10 * (1 + 1/2*sin(2*pie*i/5)) * p(i)
	//- r(i+1) = 1.6 * (1 + 1/4*cos(2*pie*i/5)) * r(i)
	//- m(i+1) = (1.02)^i * m(i)

	public int getPrixNextBot() {
		int prix = 
	}
	
	public float getPPS() {
		
	}
	
	public float getPPSTotal() {
		
	}
	
	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/

}
