
public class DisponibiliteSeuilDePixels implements Disponibilite {

	private int seuil; // le nombre minimum e pixels à avoir pour débloquer cet objet
	private boolean debloque; // si on a déjà débloqué cet objet
	private Counter counter; // permet de regarder le nombre de pixels en cours
	
	public DisponibiliteSeuilDePixels(int seuil, Counter counter) {
		this.seuil = seuil;
		this.debloque = false;
		this.counter = counter;
	}
	
    @Override
    public boolean estDisponible() {
    	if(debloque) {
    		return true;
    	} else if (counter.getNbPixels() >= seuil) {
    		debloque = true;
    		return true;
    	} else {
    		return false;
    	}
    }
}
