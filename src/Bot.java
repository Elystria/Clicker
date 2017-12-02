import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bot {
	
	/***************************************************
	ATTRIBUTS
	***************************************************/

    // Model ludique
	private String nom; // le nom du bot

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

	public Bot(String nom, int prixInitial, float rapportDEfficacite) {
		this.nom = nom;
        this.prixInitial = prixInitial;
		this.rapportDEfficacite = rapportDEfficacite;
		this.coefMultiplicite = 1.15f;
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

	public static int prixInitialNextBot(Bot botPrecedant, int index) {
		int pi = botPrecedant.getPrixInitial();
		return (int) (10f * (1f + 1f/2f * Math.cos(2f * Math.PI * index/5f)) * pi);
	}
	
	public static float rapportDEfficaciteNextBot(Bot botPrecedant, int index) {
		float ri = botPrecedant.getRapportDEfficacite();
		return (float) (1.6f * (1f + 1f/4f * Math.cos(2f * Math.PI * index/2.5f)) * ri);
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
