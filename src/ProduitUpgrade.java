import org.newdawn.slick.SlickException;

public class ProduitUpgrade extends Produit {

    /* Upgrade correspondant au produit */
    public Upgrade upgrade;

    public ProduitUpgrade(Upgrade upgrade) throws SlickException {
        super(new DisponibiliteTrue(), "Je suis une string ! :D");
    }

    //@Override
    //public void afficher(){}

    @Override
    public void acheter(){}

}
