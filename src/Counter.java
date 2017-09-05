public class Counter {


    /* Met à jour le nombre total de pixels gagnés grace au clic */
    public void activer(Partie partie){
        partie.setNbPixels(partie.getNbPixels() + partie.getPpc());
    }


    /* Met à jour le nombre total de pixels gagnés grace au pps */
    public void incrementer(Partie partie){

        partie.setNbPixels(partie.getNbPixels() + partie.getPps());
    }
}
