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
		this.nbPossede = 0;
	}
	
	/***************************************************
	METHODES
	***************************************************/

	public int getPrixNextBot() {
		return (int) (prixInitial * Math.pow(coefMultiplicite, nbPossede));
	}
	
	public float getPPS() {
		return prixInitial * rapportDEfficacite;
	}
	
	public float getPPSTotal() {
		return getPPS() * nbPossede;
	}
	
	/***************************************************
	METHODES STATIQUES
	***************************************************/
//	- p(i+1) = 10 * (1 + 1/2*sin(2*pie*i/5)) * p(i)
//			- r(i+1) = 1.6 * (1 + 1/4*cos(2*pie*i/5)) * r(i)
//			- m(i+1) = (1.02)^i * m(i)

	public int prixInitialNextBot(Bot botPrecedant, int index) {
		int pi = botPrecedant.getPrixInitial();
		return (int) (10 * (1 + 1/2 * Math.sin(2 * Math.PI * index/5)) * pi);
	}
	
	public float rapportDEfficaciteNextBot(Bot botPrecedant, int index) {
		float ri = botPrecedant.getRapportDEfficacite();
		return (float) (1.6 * (1 + 1/4 * Math.cos(2 * Math.PI * index/5)) * ri);
	}
	
	public float coefMultipliciteNextBot(Bot botPrecedant, int index) {
		float mi = botPrecedant.getCoefMultiplicite();
		return (float) (Math.pow(1.02,  index) * mi);
	}
	
	/***************************************************
	GETTEURS && SETTEURS
	***************************************************/

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public float getRapportDEfficacite() {
		return rapportDEfficacite;
	}

	public void setRapportDEfficacite(float rapportDEfficacite) {
		this.rapportDEfficacite = rapportDEfficacite;
	}

	public float getCoefMultiplicite() {
		return coefMultiplicite;
	}

	public void setCoefMultiplicite(float coefMultiplicite) {
		this.coefMultiplicite = coefMultiplicite;
	}

	public int getNbPossede() {
		return nbPossede;
	}

	public void setNbPossede(int nbPossede) {
		this.nbPossede = nbPossede;
	}
	
}
